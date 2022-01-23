package com.example.springjob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringJobApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplicationBuilder()
				.sources(SpringJobApplication.class)
				.web(WebApplicationType.NONE).build();
		ConfigurableApplicationContext applicationContext = application.run(args);
		SpringApplication.exit(applicationContext);
		System.exit(0);
	}

}
