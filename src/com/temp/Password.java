package com.temp;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class Password {

	Map<String, String> DB = new HashMap<String, String>();
	public static final String SALT = "";

	public static void main(String args[]) {
//		Password demo = new Password();
//		demo.signup("john", "dummy123");
//
//		// login should succeed.
//		if (demo.login("john", "dummy123"))
//			System.out.println("user login successfull.");
//
//		// login should fail because of wrong password.
//		if (demo.login("john", "blahblah"))
//			System.out.println("User login successfull.");
//		else
//			System.out.println("user login failed.");
		
		String str=SALT+"kunal123";
		
		String pass=generateHash(str);
		System.out.println("pass=" + pass);
		
		String str2=SALT+"kunal123";
		String enteredpass=generateHash(str2);
		
		System.out.println("entered pass="+enteredpass);
		if(enteredpass.equals(pass)){
			System.out.println("success");
		}
		else{
			System.out.println("fail");
		}
			
	}



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