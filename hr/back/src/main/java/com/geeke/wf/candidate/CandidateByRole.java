package com.geeke.wf.candidate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.VariableScope;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.geeke.admin.dao.UserRoleDao;
import com.geeke.admin.entity.UserRole;
import com.geeke.camundaex.candidate.ICandidate;
import com.geeke.camundaex.entity.ActUser;
import com.geeke.common.data.PageRequest;
import com.geeke.common.data.Parameter;
import com.google.common.collect.Lists;

/**
 * 获取角色对应用户作为审批后续人
 * @author 
 *
 */
@Service
@Transactional(readOnly = true)
public class CandidateByRole implements ICandidate, ApplicationContextAware {

	private static ApplicationContext context = null;

	@Override
	public List<ActUser> execute(VariableScope variableScope, String roleId) {
		List<ActUser> actUsers = Lists.newArrayList();
		if(StringUtils.isNotBlank(roleId)) {
			UserRoleDao userRoleDao  = (UserRoleDao) context.getBean("userRoleDao");
			
	
			List<Parameter> params = Lists.newArrayList();
			
			PageRequest pageRequest;
	    	/*获取子表列表   用户角色*/       
	        params.add(new Parameter("role_id", "=", roleId));
	        pageRequest = new PageRequest(params);
	        
	        List<UserRole> users = userRoleDao.listAll(pageRequest);
	        if(users != null) {
	        	for(UserRole u: users) {
	        		actUsers.add(new ActUser(u.getUser().getId(), u.getUser().getName()));
	        	}
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
