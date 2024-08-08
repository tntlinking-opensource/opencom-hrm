package com.geeke.induction.dao;

import com.geeke.induction.entity.TsmInductionBasic;
import com.geeke.induction.request.InductionHomeSearchRequest;
import com.geeke.wf.persistence.WfCrudDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * inductionDAO接口
 * @author
 * @version
 */
@Mapper
public interface TsmInductionBasicDao extends WfCrudDao<TsmInductionBasic> {
    int homeSearchCount(InductionHomeSearchRequest searchParams);

    List<TsmInductionBasic> homeSearchListPage(InductionHomeSearchRequest searchParams);
}
