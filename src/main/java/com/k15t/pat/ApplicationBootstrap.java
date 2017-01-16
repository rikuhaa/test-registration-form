package com.k15t.pat;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * The main entry class for the registration form application.
 * </p>
 * <p>
 * Contains the setup to use for Spring Boot, as well as the application
 * main-method.
 * </p>
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class ApplicationBootstrap {

    public static void main(String[] args) {
	SpringApplication.run(ApplicationBootstrap.class, args);
    }

    @Bean
    public ServletRegistrationBean initJerseyServlet() {
	ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/rest/*");
	registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
	return registration;
    }

}
