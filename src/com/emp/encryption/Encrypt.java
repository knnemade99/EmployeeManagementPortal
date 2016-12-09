package com.emp.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

	private static final String SALT = "E.M.P.";
	
	/* Encrypts the String */
	public static String encrypt(String str){
		String message=SALT+str;
		return generateHash(str);
	}
	
	/* generate Hash String */
	public static String generateHash(String input) {
		StringBuilder hash = new StringBuilder();

		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			byte[] hashedBytes = sha.digest(input.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
					'a', 'b', 'c', 'd', 'e', 'f','g' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			// handle error here.
		}

		return hash.toString();
	}
}
