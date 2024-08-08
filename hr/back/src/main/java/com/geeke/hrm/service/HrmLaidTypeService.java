package com.geeke.hrm.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.hrm.dao.HrmLaidTypeDao;
import com.geeke.hrm.dao.HrmLaidValueDao;
import com.geeke.hrm.entity.HrmLaidType;
import com.geeke.hrm.entity.HrmLaidValue;
import com.geeke.hrm.service.HrmLaidValueService;
import com.geeke.sys.entity.Action;
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
 * 离职类型Service
 * @author
 * @version
 */

@Service("hrmLaidTypeService")
@Transactional(readOnly = true)
public class HrmLaidTypeService extends CrudService<HrmLaidTypeDao, HrmLaidType> {

    @Autowired
    private HrmLaidValueDao hrmLaidValueDao;

    @Autowired
    private HrmLaidValueService hrmLaidValueService;

    @Override
    public HrmLaidType get(String id) {
        HrmLaidType hrmLaidType = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   离职类型项*/
        params = Lists.newArrayList();

        params.add(new Parameter("hrm_laid_type_id", "=", hrmLaidType.getId()));
        hrmLaidType.setHrmLaidValueList(hrmLaidValueService.listAll(params, ""));

        return hrmLaidType;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public HrmLaidType save(HrmLaidType hrmLaidType) {
        Map<String, String> colMaps = Maps.newHashMap();

        // 类型不能重复

        colMaps.clear();

        colMaps.put("name", "name");

        if (exists(dao, hrmLaidType, colMaps)) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "类型不能重复"));
        }

        HrmLaidType hrmLaidTypeTemp = super.save(hrmLaidType);

        /* 保存子表数据     离职类型项 */
        saveHrmLaidValueList(hrmLaidTypeTemp);

        return hrmLaidTypeTemp;
    }

    /**
     * 删除
     * @param hrmLaidType
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(HrmLaidType hrmLaidType) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     离职类型项 */
        params = Lists.newArrayList();

        params.add(new Parameter("hrm_laid_type_id", "=", hrmLaidType.getId()));
        pageRequest = new PageRequest(params);
        hrmLaidType.setHrmLaidValueList(hrmLaidValueDao.listAll(pageRequest));

        if (hrmLaidType.getHrmLaidValueList() != null && hrmLaidType.getHrmLaidValueList().size() > 0) {
            hrmLaidValueService.bulkDelete(hrmLaidType.getHrmLaidValueList());
        }

        int rows = super.delete(hrmLaidType);
        return rows;
    }

    /**
     * 批量删除
     * @param hrmLaidTypeList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<HrmLaidType> hrmLaidTypeList) {
        for (HrmLaidType hrmLaidType : hrmLaidTypeList) {
            delete(hrmLaidType);
        }

        return hrmLaidTypeList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, HrmLaidType entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (HrmLaidValue child : entity.getHrmLaidValueList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     离职类型项 */
    private void saveHrmLaidValueList(HrmLaidType hrmLaidType) {
        List<Parameter> params = Lists.newArrayList();

        params.add(new Parameter("hrm_laid_type_id", "=", hrmLaidType.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<HrmLaidValue> list_HrmLaidValue = hrmLaidValueDao.listAll(pageRequest);

        List<HrmLaidValue> deletes = Lists.newArrayList(); // 删除列表
        List<HrmLaidValue> inserts = Lists.newArrayList(); // 添加列表
        List<HrmLaidValue> updates = Lists.newArrayList(); // 更新列表
        for (HrmLaidValue hrmLaidValueSaved : list_HrmLaidValue) {
            boolean found = false;
            for (HrmLaidValue hrmLaidValue : hrmLaidType.getHrmLaidValueList()) {
                if (hrmLaidValueSaved.getId().equals(hrmLaidValue.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(hrmLaidValueSaved);
            }
        }
        if (deletes.size() > 0) {
            hrmLaidValueService.bulkDelete(deletes);
        }
        for (HrmLaidValue hrmLaidValue : hrmLaidType.getHrmLaidValueList()) {
            Map<String, String> colhrmLaidValueMaps = Maps.newHashMap();

            // 离职类型项不能重复

            colhrmLaidValueMaps.clear();

            colhrmLaidValueMaps.put("hrm_laid_type_id", "hrmLaidType");

            colhrmLaidValueMaps.put("value", "value");

            if (exists(hrmLaidValueDao, hrmLaidValue, colhrmLaidValueMaps)) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "离职类型项不能重复"));
            }

            if (StringUtils.isBlank(hrmLaidValue.getId())) {
                hrmLaidValue.setHrmLaidType(hrmLaidType);

                inserts.add(hrmLaidValue);
            } else {
                updates.add(hrmLaidValue);
            }
        }
        if (updates.size() > 0) {
            hrmLaidValueService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            hrmLaidValueService.bulkInsert(inserts);
        }
    }
}
