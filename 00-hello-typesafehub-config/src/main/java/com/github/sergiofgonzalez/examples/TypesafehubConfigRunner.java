package com.github.sergiofgonzalez.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class TypesafehubConfigRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypesafehubConfigRunner.class);
	
	public static void main(String[] args) {
		/*
		 * Use the native Config class to load from the default configuration sources:
		 * + reference.conf
		 * + application.conf
		 *  
		 * In `application.conf` we've overridden some common values as well 
		 *  
		 */
		
		// Reading by specifying complete path
		Config config = ConfigFactory.load();
		int value1 = config.getInt("common.value");
		
		// Reading by specifying node, then using getInt to read value
		Config common = config.getConfig("common");
		int value2 = common.getInt("value");
		
		LOGGER.debug("value1={}", value1);
		LOGGER.debug("value2={}", value2);
		
		
		// Verifying that overridding has worked
		LOGGER.debug("message={}", config.getString("application.message"));
		LOGGER.debug("name={}", config.getString("common.name"));
	
	}	
}
