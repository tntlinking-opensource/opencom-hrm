package com.geeke.notice.service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.baidubce.services.sms.model.SendMessageV3Response;
import com.geeke.admin.dao.UserDao;
import com.geeke.admin.entity.User;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.config.rocketmq.MqProduce;
import com.geeke.config.websocketServer.WebSocketServer;
import com.geeke.notice.entity.CompanyTree;
import com.geeke.noticesend.service.NoticeReceiveSiteService;
import com.geeke.noticesend.utils.SendMail;
import com.geeke.noticesend.entity.NoticeReceiveSite;
import com.geeke.noticesend.utils.SendSms;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.geeke.common.service.CrudService;
import com.geeke.notice.dao.NoticeSendDao;
import com.geeke.notice.entity.NoticeSend;
import com.geeke.utils.StringUtils;


import javax.annotation.PostConstruct;

/**
 * 消息发送公告Service
 * @author ycy
 * @version 2021-11-22
 */

@Service("noticeSendService")
@Transactional(readOnly = true)
public class NoticeSendService extends CrudService<NoticeSendDao, NoticeSend>{

    @Value("${spring.cloud.stream.enabled: false}")
    private boolean enableMq;		// 是否启用mq

    @Autowired
    private NoticeReceiveSiteService siteService; //消息接收

    @Autowired
    private SendMail sendMail;                    //发送邮件

    @Autowired
    private SendSms sendSms;//发送短信

    @Autowired
    private WebSocketServer webSocketServer;
    private static  WebSocketServer socketServer; //发送站内消息

    @PostConstruct
    public void init() {
        socketServer = this.webSocketServer;
    }

    @Autowired
    private MqProduce mqProduce;

    @Autowired
    private UserDao userDao;                    //人员查询

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public NoticeSend save(NoticeSend entity){
        if (StringUtils.isBlank(entity.getId())) {
            this.sendMessage(entity,false);
            this.saveAction(this.createAction("created", entity));
        } else {
            entity.preUpdate();
            this.doUpdate(entity);
            this.saveAction(this.createAction("updated", entity));
        }
        return entity;
    }


    /**
     * 判断消息发送类型  是直接保存还是 通过消息队列
     * @param noticeSend
     * @param bo false 直接保存 ， true 通过消息队列
     */
    public void sendMessage(NoticeSend noticeSend,Boolean bo){
        noticeSend.preInsert();
        this.doInsert(noticeSend);
        if(bo && enableMq){
            mqProduce.produceNotice(noticeSend);
        }else{
            this.sendInfo(noticeSend);
        }
      }


    /**
     * 发送消息
     * @param noticeSend
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void  sendInfo(NoticeSend noticeSend){
        String[] arr = noticeSend.getReceivedBy().split(",");   //接收消息人员id
        //TODO 通过id查出用户的 邮箱，手机号，微信绑定信息 判断有无选中 发送信息
        if("1".equals(noticeSend.getIsSite())){     //站内消息
            List<NoticeReceiveSite> list = new ArrayList<>();
            for (String id : arr) {
                NoticeReceiveSite site = new NoticeReceiveSite();
                site.setReadStatus("0");
                site.setSendId(noticeSend.getId());   //发送记录id
                site.setReadTime(new Date());
                site.setReceiveBy(id);
                site.setSiteContent(noticeSend.getSiteContent());
                site.setTitle(noticeSend.getTitle());
                site.setCreateBy(noticeSend.getCreateBy());
                site.setUpdateBy(noticeSend.getUpdateBy());
                site.setCreateDate(simpleDateFormat.format(new Date()));
                site.setUpdateDate(simpleDateFormat.format(new Date()));
                list.add(site);
            }
            List<String> ids = this.siteService.bulkInsert(list);
            if(ids.size() > 0){
                socketServer.sendMessageList(Arrays.asList(arr)); //todo 需要修改
            }
        }
        if("1".equals(noticeSend.getIsEmail())){    //邮件

        }
        if("1".equals(noticeSend.getIsSms())){      //短信
            //先获取需要接收短信的人员的电话号码
            List<String> userPhones = getUserPhone(arr);
            if(CollectionUtils.isNotEmpty(userPhones)){
                String phone = userPhones.stream().map(String::valueOf).collect(Collectors.joining(","));
                //将电话号码用英文逗号分隔符分割连接成字符串(支持多发)
                SendMessageV3Response response = sendSms.sendMsg(phone, noticeSend.getSmsContent());
                if(Objects.isNull(response) || !response.isSuccess()){
                    throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_400, "短信发送失败!"));
                }
            }
        }
        if("1".equals(noticeSend.getIsWechat())){      //微信

        }
    }


    /**
     * 获取公司树结构
     * @param code
     * @return
     */
    public List<CompanyTree> getCompanyTree(String code){
        List<CompanyTree> list = dao.getCompanyTree(code);
        List<CompanyTree> trees = new ArrayList<>();
        for (CompanyTree companyTree : list) {
            if ("0".equals(companyTree.getParentId())) {
                CompanyTree newNode =  findChildren(companyTree, list);
                trees.add(newNode);
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param
     * @return
     */
    private CompanyTree findChildren(CompanyTree companyTree, List<CompanyTree> list) {
        for (CompanyTree it : list) {
            if (companyTree.getId().equals(it.getParentId())) {
                companyTree.getChildren().add(findChildren(it, list));
            }
        }
        if (companyTree.getChildren().size() == 0) {
            companyTree.setChildren(null);
        }
        return companyTree;
    }


    /**
     *
     * @param ids 用户的id 多个
     * @return 所有不为空的电话号码
     */
    private List<String> getUserPhone(String[] ids){
        List<Parameter> parameters = Lists.newArrayList();
        parameters.add(new Parameter("id", "in", Arrays.asList(ids)));
        PageRequest request = new PageRequest(parameters);
        List<User> users = userDao.listAll(request);
        List<String> phones = users.stream().map(User::getPhone).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        return phones;
    }

}