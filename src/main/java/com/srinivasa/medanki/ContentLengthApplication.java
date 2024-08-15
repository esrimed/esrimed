package com.srinivasa.medanki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.boot.web.SpringBootServletInitializer;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class ContentLengthApplication extends SpringBootServletInitializer {

	@Autowired
	MainController mainController;
	
	public static void main(String[] args) {
		SpringApplication.run(ContentLengthApplication.class, args);
	}
	
	  @Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		  SpringApplicationBuilder result= application.sources(ContentLengthApplication.class);
		  return result;
	  }

}
