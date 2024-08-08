package com.geeke.induction.contract.controller;

import com.alibaba.fastjson.JSON;
import com.geeke.induction.contract.dao.InductionBatchContractDao;
import com.geeke.induction.contract.dao.InductionContractRenewalDao;
import com.geeke.induction.contract.dao.TsmContractDao;
import com.geeke.induction.contract.entity.InductionBatchContract;
import com.geeke.induction.contract.entity.InductionContractRenewal;
import com.geeke.induction.contract.entity.TsmContract;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.StringUtils;
import com.qiyuesuo.sdk.v2.utils.CryptUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping(value = "/qiyuesuo")
public class QiyuesuoCallback {

    @Value("${qiyuesuo.callbackSecretKey}")
    private String secretKey;

    @Autowired
    private InductionBatchContractDao inductionBatchContractDao;

    @Autowired
    private InductionContractRenewalDao inductionContractRenewalDao;

    @Autowired
    private TsmContractDao tsmContractDao;

    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "callback")
    public void callback(HttpServletRequest request) throws Exception {
        String content = request.getParameter("content");
        String aesDerypt = CryptUtils.aesDerypt(content, secretKey);
        if (StringUtils.isNotBlank(aesDerypt)) {
            Map<String, Object> map = JSON.parseObject(aesDerypt, Map.class);
            String status= String.valueOf(map.get("contractStatus")); // 合同状态
            String callbackType= String.valueOf(map.get("callbackType")); // 回调类型
            TsmContract contract = tsmContractDao.getByContractId(String.valueOf(map.get("contractId")));
            if (!StringUtils.isNullOrEmpty(contract)) {
                InductionBatchContract induction = inductionBatchContractDao.get(contract.getBatchContractId());
                InductionContractRenewal renewal = inductionContractRenewalDao.get(contract.getBatchContractId());
                if (!StringUtils.isNullOrEmpty(induction)) { // 修改入职合约状态信息
                    updateInductionContract(status, callbackType, induction, contract);
                }
                if (!StringUtils.isNullOrEmpty(renewal)) { // 修改续签合约状态信息
                    updateContractRenewal(status, callbackType, renewal, contract);
                }
            }
        }
        logger.info("================契约锁回调===============");
        logger.info("aesDerypt:{}", aesDerypt);
    }

    // 修改入职合约状态信息
    public void updateInductionContract(String status, String callbackType, InductionBatchContract induction, TsmContract contract) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 修改状态
        DictItem sign = new DictItem(); // 签署状态
        DictItem ifSign = new DictItem(); // 是否签字
        if (("SIGNING").equals(status) && ("PERSONAL").equals(callbackType)) { // 已签字回调
            sign.setValue("5"); // 待盖章
            induction.setSignStatus(sign);
            ifSign.setValue("1"); // 已签字未盖章
            contract.setIfSign(ifSign);
            contract.setSignDate(format.format(new Date())); // 签字时间
            if (!StringUtils.isNullOrEmpty(contract) && StringUtils.isNotBlank(contract.getId())) {
                contract.preUpdate();
                tsmContractDao.update(contract);
            }
            if (StringUtils.isNotBlank(induction.getId())) {
                induction.preUpdate();
                inductionBatchContractDao.update(induction);
            }
        } else if (("COMPLETE").equals(status) || ("END").equals(status)) { // 盖章后 | 报告中选择强制结束签署
            sign.setValue("2"); // 已签署
            induction.setSignStatus(sign);
            ifSign.setValue("2"); // 已签字已盖章
            contract.setIfSign(ifSign);
            contract.setSealDate(format.format(new Date())); // 盖章时间
            if (!StringUtils.isNullOrEmpty(contract) && StringUtils.isNotBlank(contract.getId())) {
                contract.preUpdate();
                tsmContractDao.update(contract);
            }
            if (StringUtils.isNotBlank(induction.getId())) {
                induction.preUpdate();
                inductionBatchContractDao.update(induction);
            }
        } else if (("REJECTED").equals(status) || ("RECALLED").equals(status) || (("INVALIDED").equals(status) && ("INVALIDED").equals(callbackType))) { // 签字 退回 | 已签字 撤回 | 确认盖章后作废
            // 将原记录状态改为已作废
            sign.setValue("6"); // 已签署
            induction.setSignStatus(sign);
            ifSign.setValue("6"); // 已签字已盖章
            contract.setIfSign(ifSign);
            if (!StringUtils.isNullOrEmpty(contract) && StringUtils.isNotBlank(contract.getId())) {
                contract.preUpdate();
                tsmContractDao.update(contract);
            }
            if (StringUtils.isNotBlank(induction.getId())) {
                induction.preUpdate();
                inductionBatchContractDao.update(induction);
            }
        }
    }

    // 修改续签合约状态信息
    public void updateContractRenewal(String status, String callbackType, InductionContractRenewal renewal, TsmContract contract) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 修改状态
        DictItem sign = new DictItem(); // 签署状态
        DictItem ifSign = new DictItem(); // 是否签字
        if (("SIGNING").equals(status) && ("PERSONAL").equals(callbackType)) { // 已签字回调
            sign.setValue("5"); // 待盖章
            renewal.setSignStatus(sign);
            ifSign.setValue("1"); // 已签字未盖章
            contract.setIfSign(ifSign);
            contract.setSignDate(format.format(new Date())); // 签字时间
            if (!StringUtils.isNullOrEmpty(contract) && StringUtils.isNotBlank(contract.getId())) {
                contract.preUpdate();
                tsmContractDao.update(contract);
            }
            if (StringUtils.isNotBlank(renewal.getId())) {
                renewal.preUpdate();
                inductionContractRenewalDao.update(renewal);
            }
        } else if (("COMPLETE").equals(status) || ("END").equals(status)) { // 盖章后 | 报告中选择强制结束签署
            sign.setValue("2"); // 已签署
            renewal.setSignStatus(sign);
            ifSign.setValue("2"); // 已签字已盖章
            contract.setIfSign(ifSign);
            contract.setSealDate(format.format(new Date())); // 盖章时间
            if (!StringUtils.isNullOrEmpty(contract) && StringUtils.isNotBlank(contract.getId())) {
                contract.preUpdate();
                tsmContractDao.update(contract);
            }
            if (StringUtils.isNotBlank(renewal.getId())) {
                renewal.preUpdate();
                inductionContractRenewalDao.update(renewal);
            }
        } else if (("REJECTED").equals(status) || ("RECALLED").equals(status) || (("INVALIDED").equals(status) && ("INVALIDED").equals(callbackType))) { // 签字 退回 | 已签字 撤回 | 确认盖章后作废
            // 将原记录状态改为已作废
            sign.setValue("6"); // 已签署
            renewal.setSignStatus(sign);
            ifSign.setValue("6"); // 已签字已盖章
            contract.setIfSign(ifSign);
            if (!StringUtils.isNullOrEmpty(contract) && StringUtils.isNotBlank(contract.getId())) {
                contract.preUpdate();
                tsmContractDao.update(contract);
            }
            if (StringUtils.isNotBlank(renewal.getId())) {
                renewal.preUpdate();
                inductionContractRenewalDao.update(renewal);
            }
        }
    }

}
