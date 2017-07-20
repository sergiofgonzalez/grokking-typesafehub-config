package com.github.sergiofgonzalez.examples;

import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class TypesafehubConfigRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(TypesafehubConfigRunner.class);
	
	public static void main(String[] args) {	
		/*
		 * Establish a custom priority on Config objects
		 * From top-to-bottom:
		 * 	+ application properties found in a file outside of the JAR (named application.conf)
		 *  + Environment variables
		 *  + Java System Properties
		 *  + application properties found inside the JAR
		 *  + common (reference) properties found inside the JAR
		 */
		Config propsFromOutsideJar = ConfigFactory.parseFile(Paths.get("../application.conf").toFile());
		System.out.println(propsFromOutsideJar.getString("MESSAGE"));
				
		Config environmentVariables = ConfigFactory.systemEnvironment();
		System.out.println(environmentVariables.getString("MESSAGE"));
		
		Config javaSystemProperties = ConfigFactory.systemProperties();
		System.out.println(javaSystemProperties.getString("MESSAGE"));

		Config applicationProps = ConfigFactory.parseResources("config/application.conf");
		System.out.println(applicationProps.getString("MESSAGE"));
		
		Config commonProps = ConfigFactory.parseResourcesAnySyntax("reference.conf");
		System.out.println(commonProps.getString("MESSAGE"));

		Config waterfallConfig;
		
		// Top priority is file outside the jar		
		waterfallConfig = propsFromOutsideJar
							.withFallback(environmentVariables)
							.withFallback(javaSystemProperties)
							.withFallback(applicationProps)
							.withFallback(commonProps)
							.withFallback(applicationProps);							;

		/*
		 * MESSAGE IS DEFINED EVERYWHERE
		 * MESSAGE_1 IS NOT DEFINED IN EXTERNAL APPLICATION CONF
		 * MESSAGE_2 IS NOT DEFINED AS ENVIRONMENT VARIABLE
		 * MESSAGE_3 IS NOT DEFINED AS JAVA PROPERTY
		 * MESSAGE_4 IS NOT DEFINED AS AS APPLICATION PROPERTY
		 * MESSAGE_5 IS NOT DEFINED! :P					
		 */
		printConfigProperty(waterfallConfig, "MESSAGE_1");
		printConfigProperty(waterfallConfig, "MESSAGE_2");
		printConfigProperty(waterfallConfig, "MESSAGE_3");
		printConfigProperty(waterfallConfig, "MESSAGE_4");
		printConfigProperty(waterfallConfig, "MESSAGE_5");
	}
	
	
	private static final void printConfigProperty(Config config, String property) {
		LOGGER.debug("{}=`{}`", property, config.getString(property));
	}
}
