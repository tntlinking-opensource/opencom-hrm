package com.geeke.sys.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.dao.ActionDao;
import com.geeke.sys.dao.ActionRecycleDao;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日志Service
 * @author
 * @version
 */

@Service("actionService")
@Transactional(readOnly = true)
public class ActionService extends CrudService<ActionDao, Action> {

    @Autowired
    private ActionRecycleDao actionRecycleDao;

    @Override
    public Action get(String id) {
        Action action = super.get(id);

        List<Parameter> params = null;
        PageRequest pageRequest;

        /*获取子表列表   回收站*/
        params = Lists.newArrayList();

        params.add(new Parameter("action_id", "=", action.getId()));
        pageRequest = new PageRequest(params);
        action.setActionRecycleList(actionRecycleDao.listAll(pageRequest));

        return action;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public Action save(Action action) {
        Action actionTemp = super.save(action);

        /* 保存子表数据     回收站 */
        saveActionRecycleList(actionTemp);

        return actionTemp;
    }

    /**
     * 删除
     * @param entity
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int delete(Action action) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     回收站 */
        params = Lists.newArrayList();

        params.add(new Parameter("action_id", "=", action.getId()));
        pageRequest = new PageRequest(params);
        action.setActionRecycleList(actionRecycleDao.listAll(pageRequest));

        if (action.getActionRecycleList() != null && action.getActionRecycleList().size() > 0) {
            actionRecycleDao.bulkDelete(preUpdateList((List) action.getActionRecycleList()));
        }

        int rows = super.delete(action);
        return rows;
    }

    /**
     * 批量删除
     * @param entitys
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<Action> actionList) {
        for (Action action : actionList) {
            delete(action);
        }

        return actionList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, Action entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (ActionRecycle child : entity.getActionRecycleList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     回收站 */
    private void saveActionRecycleList(Action action) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("action_id", "=", action.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<ActionRecycle> list_ActionRecycle = actionRecycleDao.listAll(pageRequest);

        List<ActionRecycle> deletes = Lists.newArrayList(); // 删除列表
        List<ActionRecycle> inserts = Lists.newArrayList(); // 添加列表
        List<ActionRecycle> updates = Lists.newArrayList(); // 更新列表
        for (ActionRecycle actionRecycleSaved : list_ActionRecycle) {
            boolean found = false;
            for (ActionRecycle actionRecycle : action.getActionRecycleList()) {
                if (actionRecycleSaved.getId().equals(actionRecycle.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                actionRecycleSaved.setOldUpdateDate(actionRecycleSaved.getUpdateDate());

                deletes.add(actionRecycleSaved);
            }
        }
        if (deletes.size() > 0) {
            actionRecycleDao.bulkDelete(deletes);
        }
        for (ActionRecycle actionRecycle : action.getActionRecycleList()) {
            if (StringUtils.isBlank(actionRecycle.getId())) {
                actionRecycle.setAction(action);

                actionRecycle.preInsert();
                inserts.add(actionRecycle);
            } else {
                actionRecycle.preUpdate();
                updates.add(actionRecycle);
            }
        }
        if (updates.size() > 0) {
            actionRecycleDao.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            actionRecycleDao.bulkInsert(inserts);
        }
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public void saveMQtoDB(Action action) {
        int rows = super.dao.insert(action);
        if (rows > 0) {
            for (ActionRecycle actionRecycle : action.getActionRecycleList()) {
                actionRecycle.setAction(action);
                actionRecycleDao.insert(actionRecycle);
            }
        }
    }
}
