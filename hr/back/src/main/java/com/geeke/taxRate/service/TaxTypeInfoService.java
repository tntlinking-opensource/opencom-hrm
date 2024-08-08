package com.geeke.taxRate.service;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.Page;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.common.service.CrudService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.taxRate.dao.TaxDetailsInfoDao;
import com.geeke.taxRate.dao.TaxTypeInfoDao;
import com.geeke.taxRate.entity.TaxDetailsInfo;
import com.geeke.taxRate.entity.TaxTypeInfo;
import com.geeke.taxRate.service.TaxDetailsInfoService;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.BadRequestException;

/**
 * 税率类型Service
 * @author
 * @version
 */

@Service("taxTypeInfoService")
@Transactional(readOnly = true)
public class TaxTypeInfoService extends CrudService<TaxTypeInfoDao, TaxTypeInfo> {

    @Autowired
    private TaxDetailsInfoDao taxDetailsInfoDao;

    @Autowired
    private TaxDetailsInfoService taxDetailsInfoService;

    @Override
    public TaxTypeInfo get(String id) {
        TaxTypeInfo taxTypeInfo = super.get(id);

        List<Parameter> params = null;

        /*获取子表列表   税率详细信息*/
        params = Lists.newArrayList();

        params.add(new Parameter("tax_type_info_id", "=", taxTypeInfo.getId()));
        taxTypeInfo.setTaxDetailsInfoList(taxDetailsInfoService.listAll(params, "rate_level"));

        return taxTypeInfo;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    @Override
    public TaxTypeInfo save(TaxTypeInfo taxTypeInfo) {
        Map<String, String> colMaps = Maps.newHashMap();

        // 类型不能重复

        colMaps.clear();

        colMaps.put("tax_name", "taxName");
        colMaps.put("company_id", "companyId");

        if (exists(dao, taxTypeInfo, colMaps) && !"离职补偿金免税上限".equals(taxTypeInfo.getTaxName())) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "税率表不能重复"));
        }

        TaxTypeInfo taxTypeInfoTemp = super.save(taxTypeInfo);

        /* 保存子表数据     税率详细信息 */
        if (CollectionUtils.isNotEmpty(taxTypeInfo.getTaxDetailsInfoList())) {
            saveTaxDetailsInfoList(taxTypeInfoTemp);
        }

        return taxTypeInfoTemp;
    }

    /**
     * 删除
     * @param taxTypeInfo
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int delete(TaxTypeInfo taxTypeInfo) {
        List<Parameter> params = null;
        PageRequest pageRequest;

        /* 处理子表     税率详细信息 */
        params = Lists.newArrayList();

        params.add(new Parameter("tax_type_info_id", "=", taxTypeInfo.getId()));
        pageRequest = new PageRequest(params);
        taxTypeInfo.setTaxDetailsInfoList(taxDetailsInfoDao.listAll(pageRequest));

        if (taxTypeInfo.getTaxDetailsInfoList() != null && taxTypeInfo.getTaxDetailsInfoList().size() > 0) {
            taxDetailsInfoService.bulkDelete(taxTypeInfo.getTaxDetailsInfoList());
        }

        int rows = super.delete(taxTypeInfo);
        return rows;
    }

    /**
     * 批量删除
     * @param taxTypeInfoList
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int bulkDelete(List<TaxTypeInfo> taxTypeInfoList) {
        for (TaxTypeInfo taxTypeInfo : taxTypeInfoList) {
            delete(taxTypeInfo);
        }

        return taxTypeInfoList.size();
    }

    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, TaxTypeInfo entity) {
        Action action = super.createAction(actionTypeId, entity);
        if (action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if (ActionConstants.ACTION_DELETED.equals(actionTypeId)) {
            for (TaxDetailsInfo child : entity.getTaxDetailsInfoList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String) Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);
            }
        }
        return action;
    }

    /* 保存子表数据     税率详细信息 */
    private void saveTaxDetailsInfoList(TaxTypeInfo taxTypeInfo) {
        List<Parameter> params = Lists.newArrayList();

        // 速率级次去重校验
        if(CollectionUtils.isNotEmpty(taxTypeInfo.getTaxDetailsInfoList())) {
            List<TaxDetailsInfo> taxDetailsInfoList = taxTypeInfo.getTaxDetailsInfoList().stream().distinct().collect(
                    Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TaxDetailsInfo :: getRateLevel))), ArrayList::new)

            );
            if(taxDetailsInfoList.size() < taxTypeInfo.getTaxDetailsInfoList().size()) {
                throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_50001, "【速率级次】重复"));
            }
        }

        params.add(new Parameter("tax_type_info_id", "=", taxTypeInfo.getId()));
        PageRequest pageRequest = new PageRequest(params);
        List<TaxDetailsInfo> list_TaxDetailsInfo = taxDetailsInfoDao.listAll(pageRequest);

        List<TaxDetailsInfo> deletes = Lists.newArrayList(); // 删除列表
        List<TaxDetailsInfo> inserts = Lists.newArrayList(); // 添加列表
        List<TaxDetailsInfo> updates = Lists.newArrayList(); // 更新列表
        for (TaxDetailsInfo taxDetailsInfoSaved : list_TaxDetailsInfo) {
            boolean found = false;
            for (TaxDetailsInfo taxDetailsInfo : taxTypeInfo.getTaxDetailsInfoList()) {
                if (taxDetailsInfoSaved.getId().equals(taxDetailsInfo.getId())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                deletes.add(taxDetailsInfoSaved);
            }
        }
        if (deletes.size() > 0) {
            taxDetailsInfoService.bulkDelete(deletes);
        }
        for (TaxDetailsInfo taxDetailsInfo : taxTypeInfo.getTaxDetailsInfoList()) {
            // 校验税率详细信息
            this.validateTaxDetailsInfo(taxDetailsInfo);
            if (StringUtils.isBlank(taxDetailsInfo.getId())) {
                taxDetailsInfo.setTaxTypeInfoId(taxTypeInfo.getId());

                inserts.add(taxDetailsInfo);
            } else {
                updates.add(taxDetailsInfo);
            }
        }
        if (updates.size() > 0) {
            taxDetailsInfoService.bulkUpdate(updates);
        }
        if (inserts.size() > 0) {
            taxDetailsInfoService.bulkInsert(inserts);
        }
    }

    public void validateTaxDetailsInfo(TaxDetailsInfo taxDetailsInfo) {
        if (null == taxDetailsInfo.getRateLevel()) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90003, "类型不能重复"));
        }
        if(null == taxDetailsInfo.getSpeedDeductAmount()){
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90003, "速算扣除数不能为空"));
        }
        if (null == taxDetailsInfo.getRate()) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90003, "税率不能为空"));
        }
        if (null == taxDetailsInfo.getAmountUp()) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90003, "纳税级距上限或者年终奖月平均金额上限不能为空"));
        }
        if (null == taxDetailsInfo.getAmountDown()) {
            throw new CommonJsonException(ResultUtil.warningJson(ErrorEnum.E_90003, "纳税级距下限或者年终奖月平均金额下限不能为空"));
        }
    }
}
