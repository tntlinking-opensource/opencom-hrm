package com.geeke.test.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.geeke.common.constants.ActionConstants;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.geeke.wf.service.NoneBizInProcService;
import com.geeke.config.exception.CommonJsonException;
import com.geeke.sys.entity.Action;
import com.geeke.sys.entity.ActionRecycle;
import com.geeke.test.entity.Expense;
import com.geeke.test.dao.ExpenseDao;
import com.geeke.test.entity.ExpenseDetail;
import com.geeke.test.dao.ExpenseDetailDao;
import com.geeke.utils.Reflections;
import com.geeke.utils.ResultUtil;
import com.geeke.utils.StringUtils;
import com.geeke.utils.constants.ErrorEnum;
import com.google.common.collect.Lists;

/**
 * 请假管理Service
 * @author lys
 * @version 2020-06-30
 */
 
@Service("expenseService")
@Transactional(readOnly = true)
public class ExpenseService extends NoneBizInProcService<ExpenseDao, Expense> {

	@Autowired
	private ExpenseDetailDao expenseDetailDao;
	
	@Override
	public Expense get(String id) {
		Expense expense = super.get(id);

		List<Parameter> params = null;
		PageRequest pageRequest;
    	/*获取子表列表   费用明细*/
		params = Lists.newArrayList();
        params.add(new Parameter("expense_id", "=", expense.getId()));
        pageRequest = new PageRequest(params);
        expense.setExpenseDetailList(expenseDetailDao.listAll(pageRequest));        
		return expense;
	}
	
	@Override
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	public Expense save(Expense expense) {

        Expense expenseTemp = super.save(expense);
		if (StringUtils.isNoneBlank(expenseTemp.getId())) {

            List<Parameter> params = null;
            PageRequest pageRequest;
            /* 处理子表     费用明细 */
            params = Lists.newArrayList();
            params.add(new Parameter("expense_id", "=", expense.getId()));
            pageRequest = new PageRequest(params);
            List<ExpenseDetail> list_ExpenseDetail = expenseDetailDao.listAll(pageRequest);            
            for(ExpenseDetail expenseDetailSaved: list_ExpenseDetail) {
                boolean found = false;   
                for (ExpenseDetail expenseDetail : expense.getExpenseDetailList()){
                   if(expenseDetailSaved.getId().equals(expenseDetail.getId())){
                       found = true;
                       break;
                   }
                }
                if(!found) {
                   expenseDetailDao.delete(expenseDetailSaved);
                }
            }
			for (ExpenseDetail expenseDetail : expense.getExpenseDetailList()){
				int childRow = 0;
                if (StringUtils.isBlank(expenseDetail.getId())) {
                    expenseDetail.setExpense(expense);
                    expenseDetail.preInsert();
                    childRow = expenseDetailDao.insert(expenseDetail);
                } else {
                    expenseDetail.preUpdate();
                    childRow = expenseDetailDao.update(expenseDetail);
                }

            }

	    }
        return expenseTemp;
	}


    /**
     * 删除
     * @param entity
     */
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
    public int delete(Expense expense) {
        List<Parameter> params = null;
        PageRequest pageRequest;
        /* 处理子表     费用明细 */
        params = Lists.newArrayList();
        params.add(new Parameter("expense_id", "=", expense.getId()));
        pageRequest = new PageRequest(params);
        expense.setExpenseDetailList(expenseDetailDao.listAll(pageRequest));        

        for(ExpenseDetail expenseDetailSaved: expense.getExpenseDetailList()) {
            expenseDetailDao.delete(expenseDetailSaved);
        }

        int rows = super.delete(expense);
        return rows;
    }



    /**
     * 生成操作日志
     * @param actionTypeId  操作类型Id
     * @param entity        操作的实体对象
     * @return
     */
    @Override
    protected Action createAction(String actionTypeId, Expense entity) {
        Action action = super.createAction(actionTypeId, entity);
        if(action == null) {
            return null;
        }
        // 删除时记录把保存的数据保存到回收站
        if(ActionConstants.ACTION_DELETED.equals(actionTypeId)) {   
            for(ExpenseDetail child: entity.getExpenseDetailList()) {
                ActionRecycle recycle = new ActionRecycle();
                recycle.setTableName(child.getBusTableName());
                recycle.setObjectId(child.getId());
                recycle.setObjectName((String)Reflections.invokeGetter(child, "name"));
                action.getActionRecycleList().add(recycle);            
            }
        }
        return action;
    }

	/**
	 * 获取表单对象的class
	 * @return
	 */
	@Override
	protected Class<Expense> getBizClass() {
		return Expense.class;
	}
	
	
    @Override
    protected int doInsert(Expense expense) {
    
        int rows = dao.insert(expense);
        if(rows > 0) {
            modifyChilds(expense);
        }
        return rows;
    }
    
    @Override
    protected int doUpdate(Expense expense) {
    
        int rows = dao.update(expense);
        if(rows > 0) {
            modifyChilds(expense);
        }
        return rows;
    }    
    
    
	
    private void modifyChilds(Expense expense) {

        List<Parameter> params = null;
        PageRequest pageRequest;
        /* 处理子表     费用明细 */
        params = Lists.newArrayList();


        params.add(new Parameter("expense_id", "=", expense.getId()));
        pageRequest = new PageRequest(params);
        List<ExpenseDetail> list_ExpenseDetail = expenseDetailDao.listAll(pageRequest);           
        for(ExpenseDetail expenseDetailSaved: list_ExpenseDetail) {
            boolean found = false;   
            for (ExpenseDetail expenseDetail : expense.getExpenseDetailList()){
               if(expenseDetailSaved.getId().equals(expenseDetail.getId())){
                   found = true;
                   break;
               }
            }
            if(!found) {
               expenseDetailDao.delete(expenseDetailSaved);
            }
        }
        for (ExpenseDetail expenseDetail : expense.getExpenseDetailList()){
            int childRow = 0;
            expenseDetail.setProcInst(expense.getProcInst());
            expenseDetail.setProcStatus(expense.getProcStatus());
            if (StringUtils.isBlank(expenseDetail.getId())) {
                expenseDetail.setExpense(expense);
                expenseDetail.preInsert();
                childRow = expenseDetailDao.insert(expenseDetail);
            } else {
                expenseDetail.preUpdate();
                childRow = expenseDetailDao.update(expenseDetail);
            }

        }

    }
	
}