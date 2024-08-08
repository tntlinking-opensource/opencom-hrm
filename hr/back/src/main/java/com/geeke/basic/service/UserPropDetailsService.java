package com.geeke.basic.service;

import com.geeke.basic.dao.UserPropDetailsDao;
import com.geeke.basic.entity.UserPropDetails;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.org.entity.Company;
import com.geeke.sys.service.SysFileService;
import com.geeke.utils.IdGen;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * 基础配置项Service
 * @author
 * @version
 */

@Service("userPropDetailsService")
@Transactional(readOnly = true)
public class UserPropDetailsService extends CrudService<UserPropDetailsDao, UserPropDetails> {

    @Autowired
    private SysFileService sysFileService;


    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public UserPropDetails save(UserPropDetails entity) {
        Map<String, String> colMaps = Maps.newHashMap();
        colMaps.clear();
        colMaps.put("user_prop_list_id", "userPropList.id");
        colMaps.put("attribute2", "attribute2");
        if (exists(dao, entity, colMaps)) {
            if(Objects.equals(entity.getUserPropList().getId(),"1")){
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "邮箱序号不可重复!"));
            }
            if(Objects.equals(entity.getUserPropList().getId(),"2")){
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "附件序号不可重复!"));
            }
        }
        if(Objects.equals(entity.getUserPropList().getId(),"2")){
            Map<String, String> nameMaps = Maps.newHashMap();
            nameMaps.clear();
            nameMaps.put("user_prop_list_id", "userPropList.id");
            nameMaps.put("attribute0", "attribute0");
            if(exists(dao, entity, nameMaps)){
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "附件名称不可重复!"));
            }
        }
        UserPropDetails userPropDetailsTemp = super.save(entity);
        return userPropDetailsTemp;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public UserPropDetails fileSave(UserPropDetails userPropDetails, MultipartFile[] attribute0Uploads, String[] deleteIds)
            throws java.io.IOException {
        if (StringUtils.isEmpty(userPropDetails.getAttribute1())) {
            userPropDetails.setAttribute1(IdGen.uuid());
        }
        // 保存附件
        sysFileService.changeAndSaveSysFileList(attribute0Uploads, userPropDetails.getAttribute1());

        // 根据附件ID删除附件信息
        if (null != deleteIds && deleteIds.length > 0) {
            sysFileService.delete(deleteIds);
        }
        //保存文件名称
        if(Objects.nonNull(attribute0Uploads)){
            if(attribute0Uploads.length > 0 && attribute0Uploads[0] != null && StringUtils.isBlank(userPropDetails.getAttribute0())){
                userPropDetails.setAttribute0(attribute0Uploads[0].getOriginalFilename());
            }
        }
        Map<String, String> colMaps = Maps.newHashMap();
        colMaps.clear();
        colMaps.put("user_prop_list_id", "userPropList.id");
        colMaps.put("attribute2", "attribute2");
        if (exists(dao, userPropDetails, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "序号不可重复!"));
        }
        UserPropDetails userPropDetailsTemp = super.save(userPropDetails);

        return userPropDetailsTemp;
    }
}
