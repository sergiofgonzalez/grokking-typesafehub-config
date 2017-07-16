package com.github.sergiofgonzalez.examples;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/*
 * A simple wrapper on top of the Config library
 */
public class SimpleConfigWrapper {
	private Config config;
	
	public SimpleConfigWrapper(Config config) {
		this.config = config;
		config.checkValid(ConfigFactory.defaultReference(), "common");
	}
	
	public SimpleConfigWrapper() {
		this(ConfigFactory.load());
	}
	
	public void printSetting(String path) {
		System.out.println(path + ": " + config.getValue(path));
	}
}
