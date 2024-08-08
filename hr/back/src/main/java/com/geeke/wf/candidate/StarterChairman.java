package com.geeke.wf.candidate;

import com.geeke.admin.dao.UserDao;
import com.geeke.admin.entity.User;
import com.geeke.camundaex.candidate.ICandidate;
import com.geeke.camundaex.constant.WfConstant;
import com.geeke.camundaex.entity.ActUser;
import com.geeke.org.dao.CompanyDao;
import com.geeke.org.dao.DepartmentDao;
import com.geeke.org.entity.Company;
import com.geeke.org.entity.Department;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 获取发起人的部门经理作为审批后续人
 * @author 
 *
 */
@Service
@Transactional(readOnly = true)
public class StarterChairman implements ICandidate, ApplicationContextAware {

	private static ApplicationContext context = null;

	@Override
	public List<ActUser> execute(VariableScope variableScope, String param) {
		List<ActUser> actUsers = Lists.newArrayList();
		
		String starterId = (String)variableScope.getVariable(WfConstant.START_UP_USER_ID);
		UserDao userDao = (UserDao)context.getBean("userDao");
        User starter = userDao.get(starterId);
        if(starter != null && starter.getDepartment() != null && StringUtils.isNotBlank(starter.getDepartment().getId())) {
        	CompanyDao companyDao = (CompanyDao) context.getBean("companyDao");
        	Company company = companyDao.get(starter.getCompany().getId());
        	if(company != null & company.getChairman() != null && StringUtils.isNotBlank(company.getChairman().getId()) ) {
        		actUsers.add(new ActUser(company.getChairman().getId(), company.getChairman().getName()));
        	}
        }
		
		return actUsers;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;

	}

}
