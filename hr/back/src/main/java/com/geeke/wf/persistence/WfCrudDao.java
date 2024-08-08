package com.geeke.wf.persistence;

import com.geeke.common.persistence.CrudDao;


/**
 * DAO支持类实现
 * @author lys
 * @version 2020-4-12
 * @param <T>
 */
public interface WfCrudDao<T extends WfDataEntity<T>> extends CrudDao<T> {

}