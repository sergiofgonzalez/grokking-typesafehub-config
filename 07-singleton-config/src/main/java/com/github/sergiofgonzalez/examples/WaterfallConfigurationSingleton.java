package com.github.sergiofgonzalez.examples;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Eagerly loaded Singleton 
 *
 */
public class WaterfallConfigurationSingleton {
	
	private static Logger LOGGER = LoggerFactory.getLogger(WaterfallConfigurationSingleton.class);
	
	private static WaterfallConfigurationSingleton uniqueInstance = new WaterfallConfigurationSingleton();
	
	private UUID instanceUUID;
	private Config config;
	
	private WaterfallConfigurationSingleton() {
		Instant start = Instant.now();
		instanceUUID = UUID.randomUUID();
		LOGGER.debug("Initializing a new Waterfall config: {}", instanceUUID = UUID.randomUUID());

		Config commonProps = ConfigFactory.parseResourcesAnySyntax("reference.conf").resolve();
		Config applicationProps = ConfigFactory.parseResources("config/application.conf").resolve();
		
		Config conf = applicationProps.withFallback(commonProps);
		config = conf.getConfig(conf.getString("profiles.active")).withFallback(commonProps);
		Duration duration = Duration.between(start, Instant.now());
		LOGGER.debug("Config initialization took {}", duration);
	}
	
	public static WaterfallConfigurationSingleton wconf() {
		return uniqueInstance;
	}
	
	public String get(String key) {
		Instant start = Instant.now();
		String value = uniqueInstance.config.getString(key);
		LOGGER.debug("Access to config {} took {}", uniqueInstance.instanceUUID, Duration.between(start, Instant.now()));
		return value;
	}
	
	public List<String> get(String key, boolean isMultivalued) {
		Instant start = Instant.now();
		List<String> values = uniqueInstance.config.getStringList(key);
		LOGGER.debug("Access to config {} took {}", uniqueInstance.instanceUUID, Duration.between(start, Instant.now()));
		return values;
	}
}
