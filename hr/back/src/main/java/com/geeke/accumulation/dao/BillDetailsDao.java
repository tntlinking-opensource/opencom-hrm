package com.geeke.accumulation.dao;

import com.geeke.accumulation.entity.BillDetails;
import com.geeke.accumulation.entity.vo.BillDetailsVo;
import com.geeke.accumulation.entity.vo.BillListVo;
import com.geeke.common.data.PageRequest;
import com.geeke.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 生成账单DAO接口
 * @author
 * @version
 */
@Mapper
public interface BillDetailsDao extends CrudDao<BillDetails> {

    /**
     * 获取去重员工工号集合 *
     * @return
     */
    List<String> getEmployeeNo();

    /**
     * 根据员工编号查询产品信息 *
     * @param employeeNo
     * @return
     */
    List<Map<String, Object>> getProductPayByEmployeeNo(@Param("employeeNo") String employeeNo);

    /**
     * 获取生成账单列表 *
     * @param pageRequest
     * @return
     */
    List<BillListVo> getBillListVos(PageRequest pageRequest);

    /**
     * 根据批次号获取汇总账单 *
     * @param billDetails
     * @return
     */
    List<BillDetailsVo> getBillSummaryList(@Param("object") BillDetails billDetails);

    /**
     * 根据批次号获取明细账单 *
     * @param billDetails
     * @return
     */
    List<BillDetailsVo> getBillDetailsList(@Param("object") BillDetails billDetails);

    /**
     * 获取最大的批次号 *
     * @return
     */
    String getMaxBatchNo();

    /**
     * 根据批次号删除账单 *
     * @param batchNo
     * @return
     */
    int deleteByBatchNo(@Param("batchNo") String batchNo);
}
