package com.github.sergiofgonzalez.examples;

import static com.github.sergiofgonzalez.examples.WaterfallConfigurationSingleton.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

public class TypesafehubConfigRunner {

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IOException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {	
		System.out.println(wconf().get("name"));

		System.out.println(wconf().get("friends", true));		
		System.out.println(wconf().get("encryption.enabled"));
		System.out.println(wconf().get("encryption.algorithm"));
		System.out.println(wconf().get("encryption.keystore.name"));
		System.out.println(wconf().get("encryption.keystore.password"));
		System.out.println(wconf().get("encryption.keystore.key.alias"));
		System.out.println(wconf().get("encryption.keystore.key.password"));
		System.out.println(wconf().get("encryption.iv"));

		System.out.println(wconf().get("message"));
		
		/*
		try (InputStream in = TypesafehubConfigRunner.class.getClassLoader().getResourceAsStream("development.pem")) {
			Yaml yaml = new Yaml();
			props = (Map<String,?>) yaml.load(in);
			LOGGER.debug("Properties from YAML loaded! -> {}", props);
		} catch (IOException e) {
			LOGGER.error("Could not read configuration as resource", e);
			throw new IllegalStateException(e);
		}
		*/
		System.out.println("-- done!!!");
	}	
}
