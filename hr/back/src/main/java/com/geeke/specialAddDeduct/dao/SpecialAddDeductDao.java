package com.geeke.specialAddDeduct.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.specialAddDeduct.entity.SpecialAddDeduct;
import com.geeke.specialAddDeduct.entity.TaxDeclareConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专项附加扣除DAO接口
 * @author
 * @version
 */
@Mapper
public interface SpecialAddDeductDao extends CrudDao<SpecialAddDeduct> {

    List<TaxDeclareConfig> getTaxDeclareConfig();

    int insertTaxDeclareConfig(@Param("object") TaxDeclareConfig taxDeclareConfig);

    int updateTaxDeclareConfig(@Param("object") TaxDeclareConfig taxDeclareConfig);

    List<SpecialAddDeduct> getSpecialAddDeductByTaxMonth(String idCard,String taxMonth);
}
