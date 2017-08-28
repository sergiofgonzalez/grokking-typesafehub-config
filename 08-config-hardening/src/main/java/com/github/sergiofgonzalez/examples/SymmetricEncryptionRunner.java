package com.github.sergiofgonzalez.examples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/*
 * native JCE example
 */
public class SymmetricEncryptionRunner {
	
	private SecretKeySpec secretKeySpec;
	private Cipher cipher;
	
	public SymmetricEncryptionRunner(String secret, int length, String algorithm) throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException {
		byte[] key = new byte[length];
		key = fixSecret(secret, length);
		this.secretKeySpec = new SecretKeySpec(key, algorithm);
		this.cipher = Cipher.getInstance(algorithm);
	}
	
	private byte[] fixSecret(String s, int length) throws UnsupportedEncodingException {
		if (s.length() < length) {
			int missingLength = length - s.length();
			for (int i = 0; i < missingLength; i++) {
				s += " ";
			}
		}
		return s.substring(0, length).getBytes("UTF-8");
	}
	
	public void encryptFile(File f) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		System.out.println("Encrypting file: " + f.getName());
		this.cipher.init(Cipher.ENCRYPT_MODE, this.secretKeySpec);
		this.writeToFile(f);
	}
	
	public void decryptFile(File f) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		System.out.println("Decrypting file: " + f.getName());
		this.cipher.init(Cipher.DECRYPT_MODE, this.secretKeySpec);
		this.writeToFile(f);
	}
	
	
	public void writeToFile(File f) throws IOException, IllegalBlockSizeException, BadPaddingException {
		FileInputStream in = new FileInputStream(f);
		byte[] input = new byte[(int) f.length()];
		in.read(input);
		
		FileOutputStream out = new FileOutputStream(f);
		byte[] output = this.cipher.doFinal(input);
		out.write(output);
		
		out.flush();
		out.close();
		in.close();
	}
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		File f = Paths.get("src", "main", "resources", "config", "application2.conf").toFile();
//		SymmetricEncryptionRunner crypto = new SymmetricEncryptionRunner("!@#$MySecr3tPassw0rd", 16, "AES");
		SymmetricEncryptionRunner crypto = new SymmetricEncryptionRunner("123567890123456123567890123456", 32, "AES");
		crypto.encryptFile(f);
	}
}
