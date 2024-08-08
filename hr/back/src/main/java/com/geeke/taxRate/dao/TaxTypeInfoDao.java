package com.geeke.taxRate.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.taxRate.entity.TaxTypeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 税率类型DAO接口
 * @author
 * @version
 */
@Mapper
public interface TaxTypeInfoDao extends CrudDao<TaxTypeInfo> {
    TaxTypeInfo getTaxType5();
}
