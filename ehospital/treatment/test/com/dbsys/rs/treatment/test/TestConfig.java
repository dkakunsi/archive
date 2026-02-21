package com.dbsys.rs.treatment.test;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import com.dbsys.rs.treatment.ApplicationConfig;

@Configuration
@ComponentScan("com.dbsys.rs.treatment.controller")
@EnableWebMvc
@Import(ApplicationConfig.class)
public class TestConfig {

	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
}
