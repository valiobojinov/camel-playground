package com.raptor.learning.camelstarter.routes.timer;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoggingTimerRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("timer:test?period=1000").log("Hello, there!").end();
	}

}
