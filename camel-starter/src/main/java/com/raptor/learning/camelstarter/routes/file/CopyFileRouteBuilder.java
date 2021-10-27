package com.raptor.learning.camelstarter.routes.file;

import javax.annotation.PostConstruct;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CopyFileRouteBuilder extends RouteBuilder {
	private static Log LOGGER = LogFactory.getLog(CopyFileRouteBuilder.class);

	@Value("${path.source}")
	private String sourceDirectory;
	
	@Value("${path.destination}")
	private String destinationDirectory;
	
	@Override
	public void configure() throws Exception {
		
		from("file:"+ sourceDirectory+"?noop=true&delete=false")
		.to("log:?level=INFO&showBody=true&showHeaders=true")
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
				LOGGER.info(new StringBuilder("Processing filename: ")
						.append(exchange.getIn().getHeader(Exchange.FILE_NAME))
						.append(" from routeId:")
						.append(exchange.getFromRouteId()).toString());
			}
		})
		.to("file:" + destinationDirectory);
	}
	@PostConstruct
	public void initialised() {
		LOGGER.info("Initialising CopyFileRoute with sourceDirectory: " + sourceDirectory + " and destinationDirectory:" + destinationDirectory);
	}
}
