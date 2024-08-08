package com.geeke.notice.dao;

import com.geeke.common.persistence.CrudDao;
import com.geeke.notice.entity.CompanyTree;
import com.geeke.notice.entity.NoticeSend;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息记录DAO接口
 * @author
 * @version
 */
@Mapper
public interface NoticeSendDao extends CrudDao<NoticeSend> {

    /**
     * 查询公司-部门-人员信息转换成树
     * @param code
     * @return
     */
    List<CompanyTree> getCompanyTree(@Param("code")String code);

    /**
     * 根据公司id 查询人员
     */
    List<String> getCompanyIdUserList(@Param("arr")String[] arr, @Param("noticeTemplateId")String id);

    /**
     * 根据部门id 查询人员
     */
    List<String> getDepartmentIdUserList(@Param("arr")String[] arr,@Param("noticeTemplateId")String id);

}