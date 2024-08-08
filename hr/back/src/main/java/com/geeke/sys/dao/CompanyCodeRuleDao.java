package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.CompanyCodeRule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 公司编码规则DAO接口
 * @author
 * @version
 */
@Mapper
public interface CompanyCodeRuleDao extends CrudDao<CompanyCodeRule> {
    /**
     * 获取编码规则
     * @param companyId
     * @param code
     * @return
     */
    public String getRuleDefByCode(@Param("companyId") String companyId, @Param("code") String code);
}
