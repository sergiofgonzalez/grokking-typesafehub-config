package com.github.sergiofgonzalez.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypesafehubConfigRunner {

	@SuppressWarnings("unused")
	private static final Logger LOGGER = LoggerFactory.getLogger(TypesafehubConfigRunner.class);
	
	public static void main(String[] args) {		
		System.setProperty("common.name", "Idris Elba"); // This only works if this is called before Config has been initialized
		
		SimpleConfigWrapper simpleConfigWrapper = new SimpleConfigWrapper();
		simpleConfigWrapper.printSetting("common.value");
		simpleConfigWrapper.printSetting("application.message");
		simpleConfigWrapper.printSetting("common.name");
	}
}
