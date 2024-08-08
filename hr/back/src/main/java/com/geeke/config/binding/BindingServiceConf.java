package com.geeke.config.binding;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.cloud.stream.binder.BinderFactory;
import org.springframework.cloud.stream.binding.BindingService;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceConfiguration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.TaskScheduler;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BindingServiceConf extends BindingServiceConfiguration {

	@Bean
	@ConditionalOnMissingBean(search = SearchStrategy.CURRENT)
	@Override
	public BindingService bindingService(
			BindingServiceProperties bindingServiceProperties,
			BinderFactory binderFactory, TaskScheduler taskScheduler, @Nullable ObjectMapper objectMapper) {
		
		// fixed: Rocketmq不支持.问题, 把配置Destination和Group中.替换成_
	    for (Entry<String, BindingProperties> entry : bindingServiceProperties.getBindings().entrySet()) {
	    	BindingProperties properties = entry.getValue();
	    	if(properties.getDestination() != null) {
	    		properties.setDestination(properties.getDestination().replace(".", "_"));
	    	
	    	}
	    	if(properties.getGroup() != null) {
	    		properties.setGroup(properties.getGroup().replace(".", "_"));
	    	
	    	}
	    }
	    
	    return super.bindingService(bindingServiceProperties, binderFactory, taskScheduler, objectMapper);
	}
}
