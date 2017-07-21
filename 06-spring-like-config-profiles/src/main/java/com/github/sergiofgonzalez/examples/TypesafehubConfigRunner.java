package com.github.sergiofgonzalez.examples;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigValueType;

public class TypesafehubConfigRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypesafehubConfigRunner.class);
	
	
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {	
		Config commonProps = ConfigFactory.parseResourcesAnySyntax("reference.conf").resolve();
		Config applicationProps = ConfigFactory.parseResources("config/application.conf").resolve();
		
		Config conf;conf = applicationProps.withFallback(commonProps);
		logConfSetting(conf, "profiles.active");
		logConfSetting(conf, "profiles.available");
		logConfSetting(conf, "name");
		logConfSetting(conf, "dev.message");
		logConfSetting(conf, "test.message");
		logConfSetting(conf, "production.message");
		
		
		/* now we restrict the value for the active profile and... done! */
		Config activeConf = conf.getConfig(conf.getString("profiles.active")).withFallback(commonProps);
		logConfSetting(activeConf, "name");
		logConfSetting(activeConf, "message");
		
	}
	
	
	private static final void logConfSetting(Config config, String path) {
		Function<String, ?> fn;
		if (config.getValue(path).valueType().equals(ConfigValueType.LIST)) {
			fn = config::getList;
		} else {
			fn = config::getString;
		}
		
		LOGGER.debug("{}={}", path, fn.apply(path));
	}
}
