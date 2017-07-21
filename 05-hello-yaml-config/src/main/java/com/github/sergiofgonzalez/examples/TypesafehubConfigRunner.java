package com.github.sergiofgonzalez.examples;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigResolveOptions;
import com.typesafe.config.ConfigValueFactory;

public class TypesafehubConfigRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypesafehubConfigRunner.class);
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {	
		/* Loading from YAML */
		Map<String, ?> props;
		try (InputStream in = TypesafehubConfigRunner.class.getClassLoader().getResourceAsStream("config/application.yml")) {
			Yaml yaml = new Yaml();
			props = (Map<String,?>) yaml.load(in);
			LOGGER.debug("Properties from YAML loaded! -> {}", props);
		} catch (IOException e) {
			LOGGER.error("Could not read configuration as resource", e);
			throw new IllegalStateException(e);
		}
		
		
		/* Now creating a configuration out of it */
		Config config = ConfigFactory.parseMap(props);
		System.out.println(config.getString("dev.message"));
		
		/* Note that reference.conf is not read */
		try {
			System.out.println(config.getString("message_1"));			
		} catch (Exception e) {
			LOGGER.error("reference.conf has not been read in the process!");
		}

		
		/* referencing previous values is super ugly in YAML */
		LOGGER.debug("dev.val={}", config.getValue("dev.val"));
		
		/* default placeholder syntax simply does not work */
		LOGGER.debug("dev.name={}", config.getValue("dev.name"));
		
		
		/* However, when using HOCON it works right out of the box */
		Config hoconConfig = ConfigFactory.load();
		System.out.println(hoconConfig);
		LOGGER.debug("dev.name={}", hoconConfig.getValue("dev.name"));
		LOGGER.debug("dev.path={}", hoconConfig.getValue("dev.path"));
		
		System.out.println("===========================");
		Config customConfig = ConfigFactory.load(config, ConfigResolveOptions.defaults());
		customConfig = customConfig.resolveWith(config);
		LOGGER.debug("custom|dev.val={}", customConfig.getValue("dev.val"));
		LOGGER.debug("custom|dev.name={}", customConfig.getValue("dev.name"));

		LOGGER.debug("custom|age_alias={}", customConfig.getValue("age_alias"));
	}
}
