package com.raptor.learning.camelstarter.routes.file;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class CopyFileRouteBuilder extends RouteBuilder {
	private static Log LOGGER = LogFactory.getLog(CopyFileRouteBuilder.class);

	private String baseLocation;
	private String sourceDirectory;
	private String destinationDirectory;
	
	public void init(String baseLocation, String sourceDirectory, String destinationDirectory) {
		this.baseLocation = baseLocation;
		this.sourceDirectory = sourceDirectory;
		this.destinationDirectory = destinationDirectory;
	}
	
	public void init(String baseLocation) {
		init(baseLocation, "input", "output");
	}
	@Override
	public void configure() throws Exception {
		from("file:"+baseLocation + "/" +sourceDirectory+"?noop=true&delete=false")
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
		.to("file:"+baseLocation + "/" + destinationDirectory);
	}

}
