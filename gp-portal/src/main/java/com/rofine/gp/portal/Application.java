package com.rofine.gp.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.rofine.gp.platform.bean.ApplicationContextUtil;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = { "com.rofine.gp.*" })
@EntityScan(basePackages = { "com.rofine.gp.*" })
@ComponentScan(basePackages = { "com.rofine.gp.*" })
public class Application {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

		ApplicationContextUtil.setApplicationContext(applicationContext);
	}
}
