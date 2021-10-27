package com.raptor.learning.camelstarter;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;

import com.raptor.learning.camelstarter.routes.file.CopyFileRouteBuilder;
import com.raptor.learning.camelstarter.routes.timer.LoggingTimerRouteBuilder;

@SpringBootApplication
public class CamelStarterApplication {
	private static CamelContext CONTEXT;
	@Autowired
	CopyFileRouteBuilder fileRouteBuilder;
	@Autowired
	LoggingTimerRouteBuilder timerRouteBuilder;
	public static void main(String[] args) throws Exception {
		CONTEXT= new DefaultCamelContext();
		SpringApplication.run(CamelStarterApplication.class, args);
		Thread.sleep(3000L);
		CONTEXT.close();
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void startCamelContext() throws Exception {
		CONTEXT.addRoutes(fileRouteBuilder);
		CONTEXT.addRoutes(timerRouteBuilder);
		CONTEXT.start();	
	}
	
	

}
