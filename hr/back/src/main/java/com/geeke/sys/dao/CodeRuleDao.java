package com.geeke.sys.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.sys.entity.CodeRule;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统编码规则DAO接口
 * @author
 * @version
 */
@Mapper
public interface CodeRuleDao extends CrudDao<CodeRule> {
    /**
     * 不在公司的规则
     * @param companyId
     * @return
     */
    public List<CodeRule> listNotInCompany(String companyId);

    /**
     * 获取编码规则
     * @param code
     * @return
     */
    public String getRuleDefByCode(@Param("code") String code);
}
