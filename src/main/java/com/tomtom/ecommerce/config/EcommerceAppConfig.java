package com.tomtom.ecommerce.config;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tomtom.ecommerce.interceptors.AuthenticateInterceptor;

@Configuration
public class EcommerceAppConfig implements WebMvcConfigurer {

	Logger logger = LogManager.getLogger(EcommerceAppConfig.class);

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AuthenticateInterceptor());
	}

	@Bean
	public SessionFactory sessionFactory() {
		StandardServiceRegistry registry = null;
		SessionFactory sessionFactory = null;

		try {
			// Create registry
			registry = new StandardServiceRegistryBuilder().configure().build();
			// Create MetadataSources
			MetadataSources sources = new MetadataSources(registry);
			// Create Metadata
			Metadata metadata = sources.getMetadataBuilder().build();
			// Create SessionFactory
			sessionFactory = metadata.getSessionFactoryBuilder().build();
		} catch (Exception e) {
			logger.error(e);
			if (registry != null) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
		}
		return sessionFactory;
	}

}