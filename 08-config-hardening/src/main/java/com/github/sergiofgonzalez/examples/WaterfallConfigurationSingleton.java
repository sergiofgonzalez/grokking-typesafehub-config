package com.github.sergiofgonzalez.examples;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

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
	private Cipher cipher;
	
	private WaterfallConfigurationSingleton() {
		Instant start = Instant.now();
		instanceUUID = UUID.randomUUID();
		LOGGER.debug("Initializing a new Waterfall config: {}", instanceUUID = UUID.randomUUID());

		Config commonProps = ConfigFactory.parseResourcesAnySyntax("reference.conf").resolve();
		Config applicationProps = ConfigFactory.parseResources("config/application.conf").resolve();
		
		Config conf = applicationProps.withFallback(commonProps);
		config = conf.getConfig(conf.getString("profiles.active")).withFallback(commonProps);
		
		String encryptionAlgorithm = config.getString("encryption.algorithm");
		String keyType = config.getString("encryption.keyType");
		String keystoreName = config.getString("encryption.keystore.name");
		String keyStorePassword = config.getString("encryption.keystore.password");
		String configSecretKeyAlias = config.getString("encryption.keystore.key.alias");
		String configSecretKeyPassword = config.getString("encryption.keystore.key.password");
		String encodedInitializationVector = config.getString("encryption.iv");
	
		try (InputStream keystoreStream = WaterfallConfigurationSingleton.class.getClassLoader().getResourceAsStream(keystoreName)) {
			KeyStore keyStore = KeyStore.getInstance("JCEKS");
			keyStore.load(keystoreStream, keyStorePassword.toCharArray());
			if (!keyStore.containsAlias(configSecretKeyAlias)) {
				LOGGER.error("The key {} was not found in the key store {}", configSecretKeyAlias, keyStore);
				throw new IllegalStateException("Could not found the expected key in the provided keystore");
			}
			
			Key aesKey = keyStore.getKey(configSecretKeyAlias, configSecretKeyPassword.toCharArray());	
			
			SecretKeySpec secretKeySpec = new SecretKeySpec(aesKey.getEncoded(), keyType);
			cipher = Cipher.getInstance(encryptionAlgorithm);
			IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.getDecoder().decode(encodedInitializationVector));
			
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
			
		} catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			LOGGER.error("Could not initialize the encryption scheme from the provided keystore and config data", e);
			throw new IllegalStateException("Could not initialize the encryption scheme", e);
		}		
		
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
	
		if (value.startsWith("cipher(") && value.endsWith(")")) {
			String cipherText = value.substring(7, value.length() - 1);
			byte[] clearBytes;
			try {
				clearBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
			} catch (IllegalBlockSizeException | BadPaddingException e) {
				LOGGER.error("Error trying to decrypt key {}", key);
				throw new IllegalArgumentException("Could not decrypt config value", e);
			}
			value = new String(clearBytes, StandardCharsets.UTF_8);
		}
		return value;
	}
	
	public List<String> get(String key, boolean isMultivalued) {
		Instant start = Instant.now();
		List<String> values = uniqueInstance.config.getStringList(key);
		LOGGER.debug("Access to config {} took {}", uniqueInstance.instanceUUID, Duration.between(start, Instant.now()));
		return values;
	}
}
