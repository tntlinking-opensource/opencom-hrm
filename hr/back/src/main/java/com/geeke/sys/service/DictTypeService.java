package com.geeke.sys.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.dao.DictItemDao;
import com.geeke.sys.dao.DictTypeDao;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.sys.entity.DictItem;
import com.geeke.sys.entity.DictType;
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
 * 字典管理Service
 * @author
 * @version
 */

@Service("dictTypeService")
@Transactional(readOnly = true)
public class DictTypeService extends CrudService<DictTypeDao, DictType> {

    @Autowired
    private DictItemDao dictItemDao;

    @Override
    public DictType get(String id) {
        DictType dictType = super.get(id);

        List<Parameter> params = null;
        PageRequest pageRequest;

        /*获取子表列表   字典项*/
        params = Lists.newArrayList();

        params.add(new Parameter("dict_type_id", "=", dictType.getId()));
        pageRequest = new PageRequest(params);
        dictType.setDictItemList(dictItemDao.listAll(pageRequest));

        return dictType;
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    @Override
    public DictType save(DictType dictType) {
        DictType dictTypeTemp = super.save(dictType);

        /* 保存子表数据     字典项 */
        saveDictItemList(dictTypeTemp);

        return dictTypeTemp;
    }

    /**
     * 删除
     * @param entity
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int delete(DictType dictType) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     字典项 */
        params = Lists.newArrayList();

        params.add(new Parameter("dict_type_id", "=", dictType.getId()));
        pageRequest = new PageRequest(params);
        dictType.setDictItemList(dictItemDao.listAll(pageRequest));

        if (dictType.getDictItemList() != null && dictType.getDictItemList().size() > 0) {
            dictItemDao.bulkDelete(preUpdateList((List) dictType.getDictItemList()));
        }

        int rows = super.delete(dictType);
        return rows;
    }

    /**
     * 批量删除
     * @param entitys
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<DictType> dictTypeList) {
        for (DictType dictType : dictTypeList) {
            delete(dictType);
        }

        return dictTypeList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, DictType entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (DictItem child : entity.getDictItemList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     字典项 */
    private void saveDictItemList(DictType dictType) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("dict_type_id", "=", dictType.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<DictItem> list_DictItem = dictItemDao.listAll(pageRequest);

        List<DictItem> deletes = Lists.newArrayList(); // 删除列表
        List<DictItem> inserts = Lists.newArrayList(); // 添加列表
        List<DictItem> updates = Lists.newArrayList(); // 更新列表
        for (DictItem dictItemSaved : list_DictItem) {
            boolean found = false;
            for (DictItem dictItem : dictType.getDictItemList()) {
                if (dictItemSaved.getId().equals(dictItem.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                dictItemSaved.setOldUpdateDate(dictItemSaved.getUpdateDate());

                deletes.add(dictItemSaved);
            }
        }
        if (deletes.size() > 0) {
            dictItemDao.bulkDelete(deletes);
        }
        for (DictItem dictItem : dictType.getDictItemList()) {
            if (StringUtils.isBlank(dictItem.getId())) {
                dictItem.setDictType(dictType);

                dictItem.preInsert();
                inserts.add(dictItem);
            } else {
                dictItem.preUpdate();
                updates.add(dictItem);
            }
        }
        if (updates.size() > 0) {
            dictItemDao.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            dictItemDao.bulkInsert(inserts);
        }
    }

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public DictType importJson(DictType dictType) {
        if (this.get(dictType.getId()) == null) {
            dictType.preInsert();
            this.doInsert(dictType);
            this.saveAction(this.createAction("created", dictType));
        }

        DictType dictTypeTemp = this.save(dictType);
        return dictTypeTemp;
    }
}
