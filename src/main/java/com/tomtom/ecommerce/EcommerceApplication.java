package com.tomtom.ecommerce;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.tomtom.ecommerce.config.EcommerceAppConfig;
import com.tomtom.ecommerce.utils.EcomUtil;

@SpringBootApplication
public class EcommerceApplication implements CommandLineRunner {

	Logger logger = LogManager.getLogger(EcommerceAppConfig.class);

	@Autowired
	private ApplicationContext context;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("\n ------------ EcommerceApplication started  ------------");
		performStartupJob();

	}

	public void performStartupJob() {
		logger.info("performing Startup Jobs");

		EcomUtil.setContextAndInit(context);
	}

}
