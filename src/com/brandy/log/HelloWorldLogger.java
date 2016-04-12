package com.brandy.log;

import java.time.LocalTime;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

public class HelloWorldLogger {

	public static void main(String args[]) throws InterruptedException, ExecutionException {
		// start the listener up
		LogQueueConsumer listener = new LogQueueConsumer();
		Thread runner = new Thread(listener);
		runner.start();

		Logger log = Logger.getLogger(HelloWorldLogger.class);
		log.debug("Hello World!  It's about: " + LocalTime.now());
		log.info("Hello World, A Few Moments Later..." + LocalTime.now());
		log.warn("Hello World, Don't Panic " + LocalTime.now());
		log.error("Hello World, it's time to worry " + LocalTime.now());
		
		Thread.sleep(1000);
		log.fatal("Goodbye World " + LocalTime.now());
		
		// clean up
		listener.shutDown();
		System.exit(1);
		
	}

}
