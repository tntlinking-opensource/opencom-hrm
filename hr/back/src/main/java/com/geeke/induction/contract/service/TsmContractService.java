package com.geeke.induction.contract.service;

import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.induction.contract.dao.TsmContractDao;
import com.geeke.induction.contract.entity.InductionBatchContract;
import com.geeke.induction.contract.entity.TsmContract;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;

import com.qiyuesuo.sdk.v2.bean.Contract;
import com.qiyuesuo.sdk.v2.response.DocumentAddResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合同Service
 * @author
 * @version
 */

@Service("tsmContractService")
@Transactional(readOnly = true)
public class TsmContractService extends CrudService<TsmContractDao, TsmContract> {

    /**
     * 获取契约锁返回的合同ID、模板ID，保存数据到合同表
     * @param inductionId
     * @param contract 契约锁返回合同
     * @param template 契约锁返回合同文档
     */
    public void saveTsmContract(String inductionId, Contract contract, DocumentAddResult template) {
        TsmContract byBatchContract = dao.getByBatchContractId(inductionId);
        if (StringUtils.isNullOrEmpty(byBatchContract)) {
            byBatchContract = new TsmContract();
        }
        byBatchContract.setContractId(String.valueOf(contract.getId()));
        byBatchContract.setContractFileId(String.valueOf(template.getDocumentId()));
        byBatchContract.setBatchContractId(inductionId);
        if (StringUtils.isBlank(byBatchContract.getId())){
            DictItem renew = new DictItem();
            renew.setValue("0");
            byBatchContract.setIsRenew(renew); // 合约分类，默认入职合同
            DictItem sign = new DictItem();
            sign.setValue("0");
            byBatchContract.setIfSign(sign); // 是否签字，默认未操作
        }
        super.save(byBatchContract);
    }
}
