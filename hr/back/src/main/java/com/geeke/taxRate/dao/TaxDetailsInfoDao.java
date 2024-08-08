package com.geeke.taxRate.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.taxRate.entity.TaxDetailsInfo;
import com.geeke.taxRate.entity.TaxTypeInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 税率类型DAO接口
 * @author
 * @version
 */
@Mapper
public interface TaxDetailsInfoDao extends CrudDao<TaxDetailsInfo> {
    List<TaxDetailsInfo> getTaxTableList(String taxId);
}
