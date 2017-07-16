package com.github.sergiofgonzalez.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class TypesafehubConfigRunner {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(TypesafehubConfigRunner.class);
	
	public static void main(String[] args) {	
		System.setProperty("common.name", "this values has been set from Java properties");
		
		Config config = ConfigFactory.load(ConfigFactory
							.parseResources("config/app.conf"));
		
							
		System.out.println("common.name=" + config.getString("common.name"));
		System.out.println("common.message=" + config.getString("common.message"));
		System.out.println("common.greeting=" + config.getString("common.greeting"));
		
		System.out.println("application.computedValue=" + config.getInt("application.computed-value"));
	}
}
