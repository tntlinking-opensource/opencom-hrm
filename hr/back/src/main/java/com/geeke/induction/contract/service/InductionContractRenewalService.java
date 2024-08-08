package com.geeke.induction.contract.service;

import com.geeke.camundaex.entity.ActResult;
import com.geeke.common.sequence.service.SequenceService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.induction.contract.dao.InductionContractRenewalDao;
import com.geeke.induction.contract.dao.TsmContractDao;
import com.geeke.induction.contract.entity.InductionBatchContract;
import com.geeke.induction.contract.entity.InductionContractRenewal;
import com.geeke.induction.contract.entity.TsmContract;
import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.org.entity.Company;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.SessionUtils;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.geeke.wf.service.BizInProcService;
import com.geeke.wf.service.NoneBizInProcService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qiyuesuo.sdk.v2.bean.Contract;
import com.qiyuesuo.sdk.v2.bean.TemplateParam;
import com.qiyuesuo.sdk.v2.response.DocumentAddResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 合约续签Service
 * @author
 * @version
 */

@Service("inductionContractRenewalService")
@Transactional(readOnly = true)
public class InductionContractRenewalService extends BizInProcService<InductionContractRenewalDao, InductionContractRenewal> {

    @Value("${qiyuesuo.renewalTemplateId}")
    private String templateId;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private QiyuesuoService qiyuesuoService;

    @Autowired
    private TsmContractDao tsmContractDao;

    @Autowired
    private TsmContractService tsmContractService;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public InductionContractRenewal save(InductionContractRenewal inductionContractRenewal) {
        // 新增时, 处理自动编号字段
        DictItem contractStatus = new DictItem();
        contractStatus.setValue("0");
        DictItem signStatus = new DictItem();
        signStatus.setValue("0");
        if (StringUtils.isBlank(inductionContractRenewal.getId())) {
            String sn = sequenceService.generate(SessionUtils.getUser().getCompanyId(), "HYXQ", inductionContractRenewal);
            inductionContractRenewal.setCode(sn);
            inductionContractRenewal.setContractDate(""); // 合约生成时间
            inductionContractRenewal.setContractStartDate(""); // 合约发起时间
            inductionContractRenewal.setContractStatus(contractStatus); // 合约状态
            inductionContractRenewal.setSignStatus(signStatus); // 签署状态
        }
        return super.save(inductionContractRenewal);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public ActResult create(String procDefId, InductionContractRenewal inductionContractRenewal, MultipartFile[] attachments) {
        // 新增时, 处理自动编号字段
        if (StringUtils.isBlank(inductionContractRenewal.getId())) {
            String sn = sequenceService.generate(SessionUtils.getUser().getCompanyId(), "HYXQ", inductionContractRenewal);
            inductionContractRenewal.setCode(sn);
        }

        return super.create(procDefId, inductionContractRenewal, attachments);
    }

    /**
     * 批量生成合约
     * @param entitys
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<String> bulkGenerate(List<InductionContractRenewal> entitys) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < entitys.size(); i++) {
            InductionContractRenewal generate = generate(entitys.get(i).getId());
            ids.add(generate.getId());
        }
        return ids;
    }

    /**
     * 生成合约
     * @param id
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public InductionContractRenewal generate(String id) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        InductionContractRenewal renewal = super.get(id);
        // 创建合同草稿（设置基本信息）
        Contract contract = qiyuesuoService.createContract(renewal.getTheme(), renewal.getName(), renewal.getPhone());
        // 用模板添加合同文档
        DocumentAddResult template = createTemplate(renewal, contract.getId());
        // 更新记录当前记录中合同生成时间、合约生成状态
        DictItem status = new DictItem();
        status.setValue("1");
        renewal.setContractStatus(status);
        renewal.setContractDate(format.format(new Date()));
        InductionContractRenewal save = super.save(renewal);
        // 获取契约锁返回的合同ID、模板ID，保存数据到合同表
        tsmContractService.saveTsmContract(save.getId(), contract, template);
        return save;
    }

    /**
     * 用模板添加合同文档
     * @param renewal
     * @param contractId
     * @return
     */
    public DocumentAddResult createTemplate(InductionContractRenewal renewal, Long contractId) {
        String title = renewal.getName() + "-入职合同";
        // 添加合同文档
        List<TemplateParam> template = setTemplate(renewal);
        DocumentAddResult result = qiyuesuoService.createTemplate(contractId, title, template, Long.valueOf(templateId));
        return result;
    }

    public List<TemplateParam> setTemplate(InductionContractRenewal renewal) {
        List<TemplateParam> params = new ArrayList<>();
        params.add(new TemplateParam("partb", renewal.getName())); // 姓名
        params.add(new TemplateParam("maturityTime", renewal.getExpirationDate())); // 上一份合同到期时间
        params.add(new TemplateParam("sendTalents", renewal.getArriveName())); // 送达人
        params.add(new TemplateParam("sendTalentsContract", renewal.getArrivePhone())); // 送达人联系方式
        params.add(new TemplateParam("workParta", renewal.getContractSignUnit())); // 甲方
        params.add(new TemplateParam("sendTalents", renewal.getArriveName())); // 送达人
        params.add(new TemplateParam("signDate", renewal.getSignDate())); // 签字日期
        params.add(new TemplateParam("workAddressa", renewal.getReportAddress())); // 甲方-住所地
        params.add(new TemplateParam("credentiano", renewal.getIdNumber())); // 身份证号
        params.add(new TemplateParam("address", renewal.getAddress())); // 居住地址
        params.add(new TemplateParam("mobile", renewal.getPhone())); // 联系方式
        params.add(new TemplateParam("email", renewal.getEmail())); // 邮箱
        params.add(new TemplateParam("startTime", renewal.getContractStartTime())); // 合同开始时间
        params.add(new TemplateParam("endTime", renewal.getContractEndTime())); // 合同结束时间
        params.add(new TemplateParam("testStartTime", renewal.getContractStartTime())); // 试用期开始时间
        params.add(new TemplateParam("testEndTime", renewal.getProbationEndTime())); // 试用期结束时间
        params.add(new TemplateParam("post", renewal.getTechPosition().getName())); // 员工职位
        params.add(new TemplateParam("rank", renewal.getEmployeeLevel())); // 员工职级
        params.add(new TemplateParam("workLegalmana", renewal.getLaborLegalmana())); // 法定代表人
        params.add(new TemplateParam("bWages", renewal.getBasicWages())); //基本工资
        params.add(new TemplateParam("tryOutBasic", renewal.getProbationBasicWages())); // 试用期基本工资
        params.add(new TemplateParam("instBonus", renewal.getSystemReward())); // 制度奖
        params.add(new TemplateParam("secretBonus", renewal.getSecrecyReward())); // 保密金
        params.add(new TemplateParam("basicPerf", renewal.getBasicPerformance())); // 基本绩效
        params.add(new TemplateParam("testBasicPerf", renewal.getProbationBasicPerformance())); // 试用期基本绩效
        params.add(new TemplateParam("floatPerf", renewal.getFloatingPerformance())); // 浮动绩效
        params.add(new TemplateParam("testFloatPerf", renewal.getProbationFloatingPerformance())); // 试用期浮动绩效
        return params;
    }

    /**
     * 批量发起合约
     * @param entitys
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<String> bulkStart(List<InductionContractRenewal> entitys) {
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < entitys.size(); i++) {
            InductionContractRenewal start = start(entitys.get(i).getId());
            ids.add(start.getId());
        }
        return ids;
    }

    /**
     * 发起合约
     * @param id
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public InductionContractRenewal start(String id) {
        InductionContractRenewal renewal = super.get(id);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        TsmContract byBatchContract = tsmContractDao.getByBatchContractId(id);
        if (StringUtils.isNullOrEmpty(byBatchContract)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "发起合约错误，请先执行生成合约操作！"));
        }
        // 契约锁发起合约
        qiyuesuoService.start(Long.valueOf(byBatchContract.getContractId()));
        // 更改合约表信息
        DictItem status = new DictItem();
        status.setValue("9");
        renewal.setContractStatus(status);
        // 更改签署状态
        DictItem sign = new DictItem();
        sign.setValue("4"); // 待签字
        renewal.setSignStatus(sign);
        renewal.setContractStartDate(format.format(new Date())); // 合约发起时间
        return super.save(renewal);
    }

    /**
     * 批量发起签署--创建流程
     * @param procDefId
     * @param entitys
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<ActResult> bulkSignCreate(String procDefId, List<InductionContractRenewal> entitys) {
        List<ActResult> list = new ArrayList<>();
        for (int i = 0; i < entitys.size(); i++) {
            entitys.get(i).preUpdate();
            ActResult actResult = wfUtils.create(procDefId, entitys.get(i), null, this);
            if(actResult.getCode() == ActResult.EnumCodes.AutoSubmit || actResult.getCode() == ActResult.EnumCodes.Done) {
                this.doUpdate(entitys.get(i));
            }
            list.add(actResult);
        }
        return list;
    }

    /**
     * 发起签署--创建流程
     * @param procDefId
     * @param entity
     * @return
     */
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public ActResult firstCreate(String procDefId, InductionContractRenewal entity) {
        entity.preUpdate();
        ActResult actResult = wfUtils.create(procDefId, entity, null, this);
        if(actResult.getCode() == ActResult.EnumCodes.AutoSubmit || actResult.getCode() == ActResult.EnumCodes.Done) {
            this.doUpdate(entity);
        }
        return actResult;
    }

    /**
     * 预览合同文档
     * @param id
     * @return
     */
    public String viewContract(String id) {
        TsmContract tsmContract = tsmContractDao.getByBatchContractId(id);
        if (StringUtils.isNullOrEmpty(tsmContract)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "预览文档错误，请重新生成合约！"));
        }
        String pageUrl = qiyuesuoService.viewContract(Long.valueOf(tsmContract.getContractId()));
        return pageUrl;
    }

    /**
     * 下载合同文档
     * @param id
     * @return
     */
    public void uploadContract(String id, HttpServletResponse response) throws Exception {
        TsmContract tsmContract = tsmContractDao.getByBatchContractId(id);
        if (StringUtils.isNullOrEmpty(tsmContract)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "下载文档错误，请重新生成合约！"));
        }
        qiyuesuoService.uploadContract(Long.valueOf(tsmContract.getContractFileId()), response);
    }

    /**
     * 查看报告
     * @param id
     * @return
     */
    public String report(String id) {
        TsmContract byBatchContract = tsmContractDao.getByBatchContractId(id);
        if (StringUtils.isNullOrEmpty(byBatchContract)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "查看报告错误，请重新生成合约！"));
        }
        String pageUrl = qiyuesuoService.report(Long.valueOf(byBatchContract.getContractId()));
        return pageUrl;
    }

    /**
     * 批量添加
     * @param entitys
     * @return
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public List<String> bulkInsert(List<InductionContractRenewal> entitys) {
        List<String> ids = Lists.newArrayList();
        DictItem contractStatus = new DictItem();
        contractStatus.setValue("0");
        DictItem signStatus = new DictItem();
        signStatus.setValue("0");
        for(InductionContractRenewal entity: entitys) {
            // 设置自动编号
            String sn = sequenceService.generate(SessionUtils.getUser().getCompanyId(), "HYXQ", entity);
            entity.setCode(sn);
            entity.setContractDate(""); // 合约生成时间
            entity.setContractStartDate(""); // 合约发起时间
            entity.setContractStatus(contractStatus); // 合约状态
            entity.setSignStatus(signStatus); // 签署状态
            entity.preInsert();
            ids.add(entity.getId());
        }
        dao.bulkInsert(entitys);
        return ids;
    }

    /**
     * 获取表单对象的class
     * @return
     */
    @Override
    protected Class<InductionContractRenewal> getBizClass() {
        return InductionContractRenewal.class;
    }
}
