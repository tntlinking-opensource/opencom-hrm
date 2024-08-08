package com.newtouch.cloud.common.dependency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class DependencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DependencyApplication.class, args);
	}

}
