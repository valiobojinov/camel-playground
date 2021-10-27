package com.raptor.learning.camelstarter.routes.timer;

import javax.annotation.PostConstruct;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoggingTimerRouteBuilder extends RouteBuilder {
	private static Log LOGGER = LogFactory.getLog(LoggingTimerRouteBuilder.class);

	@Value("${period}")
	private Integer period;
	@Override
	public void configure() throws Exception {
		from("timer:test?period=" + period).log("Hello, there! I run every " + period + "ms").end();
	}
	
	@PostConstruct
	public void initialised() {
		LOGGER.info("Initialising timer with " + period + "ms");
	}

}
