package com.godai.trade.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;

import sun.misc.Signal;
import sun.misc.SignalHandler;

@SuppressWarnings("restriction")
public class ShutDown implements SignalHandler {

	private static final Logger logger = LoggerFactory.getLogger(ShutDown.class);
	private AbstractApplicationContext application;
	public ShutDown(AbstractApplicationContext application) {
		super();
		this.application = application;
	}

	@Override
	public void handle(Signal arg0) {
		logger.info(arg0.getName() + "is recevied.");
		if ("USR2".equals(arg0.getName())) {
			application.destroy();
			logger.info("AbstractApplicationContext closed!");
			logger.info("#######################system.exit-0#########################");
			logger.info("#######################system.exit-0#########################");
			logger.info("#######################system.exit-0#########################");
			System.exit(0);
		}
	}
}
