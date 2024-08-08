package com.geeke.common.service;

import com.geeke.common.IActionSaver;
import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.persistence.CrudDao;
import com.geeke.common.persistence.DataEntity;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.sys.entity.DictItem;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Service基类
 * @author lys
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {
	
	/**
	 * 持久层对象
	 */
	@Autowired
	protected D dao;
	
	@Autowired
	IActionSaver actionSaver;

	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}

	/**
	 * 查询分页数据
	 * @param parameters 查询参数
	 * @param offset
	 * @param limit
	 * @param orderby
	 * @return
	 */
	public Page<T> listPage(List<Parameter> parameters, int offset, int limit, String orderby) {
		PageRequest pageRequest = new PageRequest(offset, limit, parameters, orderby);
		int total = dao.count(pageRequest);
		List<T> list = null;
        if(total > 0) {
            list = dao.listPage(pageRequest);
        }
        return new Page<T>(total, list);
	}


	/**
	 * 查询列表数据
	 * @param parameters
	 * @param orderby
	 * @return
	 */
	public List<T> listAll(List<Parameter> parameters, String orderby) {
		PageRequest pageRequest = new PageRequest(parameters, orderby);
		return dao.listAll(pageRequest);
	}


	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public T save(T entity) {
		if (StringUtils.isBlank(entity.getId())){
			entity.preInsert();
			doInsert(entity);
			this.saveAction(this.createAction(ActionConstants.ACTION_CREATED, entity));
		}else{
			entity.preUpdate();
			doUpdate(entity);
			this.saveAction(this.createAction(ActionConstants.ACTION_UPDATED, entity));
		}
		
		return entity;
	}

	/**
	 * 实体表有删除字段时，采用逻辑删除；否则采用物理删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int delete(T entity) {
		entity.preUpdate();
		int rows = doDelete(entity);
		if(rows > 0) {
			this.saveAction(this.createAction(ActionConstants.ACTION_DELETED, entity));
		}
		return rows;
	}

	/**
	 * 批量添加
	 * @param entitys
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public List<String> bulkInsert(List<T> entitys) {
		List<String> ids = Lists.newArrayList();
		for(T entity: entitys) {
			entity.preInsert();
			ids.add(entity.getId());
		}
		dao.bulkInsert(entitys);
		return ids;
	}
	
	/**
	 * 批量修改
	 * @param entitys
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public List<String> bulkUpdate(List<T> entitys) {
		List<String> ids = Lists.newArrayList();
		for(T entity: entitys) {
			entity.preUpdate();
			ids.add(entity.getId());
		}
		int update = dao.bulkUpdate(entitys);
		if (update != entitys.size()) {
			int size = entitys.size() - update;
			throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50002, "存在非最新版本数据，操作失败！错误条数：" + size));
		}
		return ids;
	}
	
	/**
	 * 批量删除
	 * @param entitys
	 * @return
	 */
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public int bulkDelete(List<T> entitys) {
		for(T entity: entitys) {
			entity.preUpdate();
		}
		int delete = dao.bulkDelete(entitys);
		if (delete != entitys.size()) {
			int size = entitys.size() - delete;
			throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50002, "存在非最新版本数据，操作失败！失败条数：" + size));
		}
		return delete;
	}
	
	
	/**
	 * 检查是否有重复的数据
	 * @param curdDao   dao
	 * @param entity	业务数据
	 * @param colMaps	检查字段
	 * @return
	 */
	protected boolean exists(CrudDao curdDao, DataEntity entity, Map<String, String> colMaps) {
		if(colMaps == null || colMaps.size() <= 0) {
			return false;
		}

		List<Parameter> params = Lists.newArrayList();

		if(!StringUtils.isBlank(entity.getId())) {
			params.add(new Parameter("id", "<>", entity.getId()));
		}


		for(String column: colMaps.keySet()) {
			params.add(new Parameter(StringUtils.toUnderScoreCase(column), "=", Reflections.invokeGetter(entity, colMaps.get(column))));
		}

		PageRequest pageRequest = new PageRequest(params);
		int row = curdDao.count(pageRequest);
		return row > 0;
	}

	/**
	 * 插入操作
	 * @param entity
	 * @return
	 */
	protected int doInsert(T entity) {
		return dao.insert(entity);
	}


	/**
	 * 更新操作
	 * @param entity
	 * @return
	 */
	protected int doUpdate(T entity) {
		int update = dao.update(entity);
		if (update != 1) {
			throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50002, "该记录非最新版本，操作失败！"));
		}
		return update;
	}

	/**
	 * 删除操作
	 * @param entity
	 * @return
	 */
	protected int doDelete(T entity) {
		int delete = dao.delete(entity);
		if (delete != 1) {
			throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50002, "该记录非最新版本，操作失败！"));
		}
		return delete;
	}

	/**
	 * 生成操作日志
	 * @param actionTypeId  操作类型Id
	 * @param entity		操作的实体对象
	 * @return
	 */
	protected Action createAction(String actionTypeId, T entity) {
		// 没有del_flag字段
		if(entity == null || !entity.getBusTableHasDelFlag()) {
			return null;
		}
		Action action = new Action();

		DictItem di = new DictItem();
		di.setValue(actionTypeId);
		action.setActionType(di);
		action.setObjectType(entity.getBusTableName());
		action.setObjectId(entity.getId());
		action.setObjectName((String)Reflections.invokeGetter(entity, "name"));
		action.setObjectComments(entity.getBusTableComments());
		action.setStatus("0");


		List<ActionRecycle> recycles = Lists.newArrayList();
		// 删除时记录把保存的数据保存到回收站
		if(ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
			ActionRecycle recycle = new ActionRecycle();
			recycle.setTableName(entity.getBusTableName());
			recycle.setObjectId(entity.getId());
			recycle.setObjectName((String)Reflections.invokeGetter(entity, "name"));
			recycles.add(recycle);
		}
		action.setActionRecycleList(recycles);
		return action;
	}

	/**
	 * 批量更新之前执行方法，需要手动调用
	 * @param entities
	 * @return
	 */
	protected List<DataEntity> preUpdateList(List<DataEntity> entities) {
		for(DataEntity entity: entities) {
			entity.preUpdate();
		}
		return entities;
	}

	/**
	 * 保存操作日志
	 */
	protected void saveAction(Action action) {
		actionSaver.saveAction(action);
	}

	/**
	 * 获取条目统计
	 * @return
	 */
    public long getAllCount(){
		return getAllCount(new PageRequest(new ArrayList<>()));
	}

	/**
	 * 获取条目统计
	 * @return
	 */
	public long getAllCount(PageRequest pageRequest){
		return dao.count(pageRequest);
	}
}
