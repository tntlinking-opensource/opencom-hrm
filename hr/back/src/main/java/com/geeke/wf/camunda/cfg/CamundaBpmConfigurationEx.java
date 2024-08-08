package com.geeke.wf.camunda.cfg;

import java.util.List;

import org.camunda.bpm.engine.impl.cfg.CompositeProcessEnginePlugin;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.spring.SpringProcessEngineConfiguration;
import org.camunda.bpm.spring.boot.starter.util.CamundaSpringBootUtil;
import org.camunda.connect.plugin.impl.ConnectProcessEnginePlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.geeke.camundaex.cfg.SpringProcessEngineConfigurationEx;
import com.geeke.camundaex.cfg.TaskServiceExImpl;

/**
 * 注入自定义的流程引擎配置bean
 * 修改自定义任务服务
 * @author lys
 *
 */

@Component
public class CamundaBpmConfigurationEx  {
	private static Logger log = LoggerFactory.getLogger(CamundaBpmConfigurationEx.class);

	@Autowired
	CamundUidGenerator camundUidGenerator;
	
	@Bean
	@ConditionalOnMissingBean(ProcessEngineConfigurationImpl.class)
	public ProcessEngineConfigurationImpl processEngineConfigurationImpl(List<ProcessEnginePlugin> processEnginePlugins) {
		log.info("------- inject SpringProcessEngineConfigurationEx bean as processEngineConfigurationImpl");
		SpringProcessEngineConfigurationEx springProcessEngineConfiguration = new SpringProcessEngineConfigurationEx();
		
		// 设置Snowflake ID生成器
		springProcessEngineConfiguration.setIdGenerator(camundUidGenerator);
		
		// 修改 taskService为拓展service
		springProcessEngineConfiguration.setTaskService(new TaskServiceExImpl());
		
		final SpringProcessEngineConfiguration configuration = CamundaSpringBootUtil.initCustomFields(springProcessEngineConfiguration);

		// 连接插件配置，否则无法解析配置了连接器activity
		configuration.getProcessEnginePlugins().add(new ConnectProcessEnginePlugin());
		
		configuration.getProcessEnginePlugins().add(new CompositeProcessEnginePlugin(processEnginePlugins));
		
		
		return configuration;
	}
}
