package com.github.sergiofgonzalez.examples;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.Key;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/*
 * JCE based implm
 */
public class EncryptTool {
	public static void main(String[] args) throws Exception {
		// Loading key from key store
		InputStream keystoreStream = new FileInputStream(Paths.get("src", "main", "resources", "config-keystore.jck").toFile());
		KeyStore keystore = KeyStore.getInstance("JCEKS");
		keystore.load(keystoreStream, "mystorepass".toCharArray());
		if (!keystore.containsAlias("config-key")) {
			System.out.println("Couldn't found the expected AES key in the provided keystore");
			throw new IllegalStateException("Couldn't found the expected AES key in the provided keystore");
		}
		
		Key key = keystore.getKey("config-key", "mykeypass".toCharArray());
		System.out.println("key: " + key.getAlgorithm() + ":=>" + Base64.getEncoder().encodeToString(key.getEncoded()));	
		
		SecureRandom random = new SecureRandom();
		byte[] ivBytes = new byte[Cipher.getInstance("AES/CBC/PKCS5Padding").getBlockSize()];
		random.nextBytes(ivBytes);
		IvParameterSpec ivParameterSpec = new IvParameterSpec(ivBytes);
		System.out.println("IV: " + Base64.getEncoder().encodeToString(ivBytes));
		
		// Encrypt
		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getEncoded(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		byte[] inputBytes = "hello, symmetric encription!".getBytes(StandardCharsets.UTF_8);
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] output = cipher.doFinal(inputBytes);
		
		String cipherText = Base64.getEncoder().encodeToString(output);
		System.out.println("Encrypted: " + cipherText);
		
		
		// Decrypt
		cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
		byte[] clearBytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		System.out.println("Decrypted: " + new String(clearBytes, StandardCharsets.UTF_8));
	}
}
