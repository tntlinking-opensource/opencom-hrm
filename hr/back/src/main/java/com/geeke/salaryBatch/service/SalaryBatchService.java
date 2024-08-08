package com.geeke.salaryBatch.service;

import com.geeke.common.service.CrudService;
import com.geeke.salaryBatch.entity.CodeID;
import com.geeke.salaryBatch.dao.SalaryBatchDao;
import com.geeke.salaryBatch.entity.SalaryBatch;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 薪资计算Service
 * @author
 * @version
 */

@Service("salaryBatchService")
@Transactional
public class SalaryBatchService extends CrudService<SalaryBatchDao, SalaryBatch> {
    public SalaryBatch insertSalarybatch(String billYearMonth,String taxYearMonth){
//        String issueName = conditionMap.getString("issueName"); //发放名称
//        String sendYear = conditionMap.getString("sendYear");  //选择应发年月
//        String billYear = conditionMap.getString("billYear");  //账单年月
//        String taxYear = conditionMap.getString("taxYear");  //报税年月
//        String isCombinedTax = conditionMap.getString("isCombinedTax"); //合并计税
        SalaryBatch salarybatchVo = new SalaryBatch() ;
//        salarybatchVo.setPayName(issueName);
//        salarybatchVo.setPay_year_month(sendYear);
//        salarybatchVo.setBill_year_month(billYear);
//        salarybatchVo.setTax_month(taxYear) ;

        //获得序列号
        salarybatchVo.setSaBatchCode(CodeID.id());
//        salarybatchVo.setTotalDecimal(Total_number);  //生成人数
//        salarybatchVo.setTotalAmount(new BigDecimal(Total_amount));  //总金额
//        salarybatchVo.setFailDecimal(new BigDecimal(Fail_number));   //生成失败人数
        salarybatchVo.setPayrollStateBd("0");
        salarybatchVo.setBillYearMonth(billYearMonth);//账单年月
        salarybatchVo.setTaxMonth(taxYearMonth);//账单年月
        salarybatchVo.setIsDel("0"); ;
        salarybatchVo.setApplyPayState("0");
        salarybatchVo.setPayState("0");
        salarybatchVo.setApplyState("0");
        salarybatchVo.setAuditState("0");
        salarybatchVo.setIsVerify("0");
        salarybatchVo.setIsCsVerify("0");
//        salarybatchVo.setMustDay(salaryclassVo.getPayDay());
//        salarybatchVo.setPayrollTypeBd(salaryclassVo.getPayrollTypeBd());

//        salarybatchVo.setCreateUserId(login_id);  //创建人
        salarybatchVo.setCreateDate((new Timestamp(new Date().getTime())).toString());
        salarybatchVo.setLastUpdateDate((new Timestamp(new Date().getTime())).toString());
//        salarybatchVo.setLastUpdateUserId(login_id);
//        if(isCombinedTax == null||isCombinedTax.equals("")) {
//            salarybatchVo.setIsUniteTax("0");
//        }else {
//            salarybatchVo.setIsUniteTax("1");
//        }
        super.save(salarybatchVo);
        return salarybatchVo;
    }

    public void updateSalaryBatch(SalaryBatch salaryBatch) {
        super.doUpdate(salaryBatch);
    }
}
