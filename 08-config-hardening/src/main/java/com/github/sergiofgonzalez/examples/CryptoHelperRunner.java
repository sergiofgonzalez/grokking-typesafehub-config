package com.github.sergiofgonzalez.examples;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.cipher.CryptoCipherFactory;
import org.apache.commons.crypto.cipher.CryptoCipherFactory.CipherProvider;
import org.apache.commons.crypto.utils.Utils;


/*
 * Apache Commons Crypto Example: as Spark includes Commons Crypto it is not that far-fetched
 */
public class CryptoHelperRunner {
	public static void main(String[] args) throws Exception {
		final SecretKeySpec key = new SecretKeySpec(getUTF8Bytes("12345678901234561234567890123456"), "AES");
		final IvParameterSpec iv = new IvParameterSpec(getUTF8Bytes("1234567890123456"));
		
		Properties properties = new Properties();
		properties.setProperty(CryptoCipherFactory.CLASSES_KEY, CipherProvider.JCE.getClassName());
		
		final String transform = "AES/CBC/PKCS5Padding";
		CryptoCipher encipher = Utils.getCipherInstance(transform, properties);
		System.out.println("Cipher: " + encipher.getClass().getCanonicalName());
		
		final String sampleInput = "hello world!";
		System.out.println("input: " + sampleInput);
		
		byte[] input = getUTF8Bytes(sampleInput);
		byte[] output = new byte[32];
		
		encipher.init(Cipher.ENCRYPT_MODE, key, iv);
		
		int updateBytes = encipher.update(input,  0, input.length, output, 0);
		System.out.println(updateBytes);
		
		int finalBytes = encipher.doFinal(input, 0, 0, output, updateBytes);
		System.out.println(finalBytes);
		
		encipher.close();
		
		System.out.println(Arrays.toString(Arrays.copyOf(output, updateBytes + finalBytes)));
		
		InputStream keystoreStream = new FileInputStream(Paths.get("src", "main", "resources", "config-keystore.jck").toFile());
		KeyStore keystore = KeyStore.getInstance("JCEKS");
		keystore.load(keystoreStream, "mystorepass".toCharArray());
		if (!keystore.containsAlias("config-key")) {
			System.out.println("Couldn't found the expected AES key in the provided keystore");
			throw new IllegalStateException("Couldn't found the expected AES key in the provided keystore");
		}
		
		Key aesKey = keystore.getKey("config-key", "mykeypass".toCharArray());
		System.out.println("alg: " + aesKey.getAlgorithm() + ":=>" + Base64.getEncoder().encodeToString(aesKey.getEncoded()));
	}
	
	private static byte[] getUTF8Bytes(String input) {
		return input.getBytes(StandardCharsets.UTF_8);
	}
}
