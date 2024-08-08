package com.geeke.dc.service;

import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.dc.dao.DcConditionDao;
import com.geeke.dc.entity.DcCondition;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 查询条件Service
 * @author
 * @version
 */

@Service("dcConditionService")
@Transactional(readOnly = true)
public class DcConditionService extends CrudService<DcConditionDao, DcCondition> {

    @Autowired
    private DcConditionDao dcConditionDao;

    /**
     * 移动排序批量修改序号，返回最新信息
     *
     * @param conditionList
     * @param userId
     * @param routerId
     * @return
     */
    @Transactional
    public List<DcCondition> bulkUpdateSort(List<DcCondition> conditionList, String userId, String routerId) {
        if (CollectionUtils.isNotEmpty(conditionList)) {
            for (int i = 0; i < conditionList.size(); i++) {
                DcCondition dcCondition = conditionList.get(i);
                dcCondition.preUpdate();
                dcCondition.setSort(i + 1);
            }
            //批量更新
            dcConditionDao.bulkUpdate(conditionList);
        }
        List<Parameter> params = null;
        PageRequest pageRequest;
        /* 处理子表     设备维保单位 */
        params = Lists.newArrayList();
        params.add(new Parameter("user_id", "=", userId));
        params.add(new Parameter("router_id", "=", routerId));
        pageRequest = new PageRequest(params);
        List<DcCondition> conditions = dcConditionDao.listAll(pageRequest);
        //返回最新的收藏数据
        return conditions != null ? conditions : new ArrayList<>();
    }
}
