package com.temp;

import org.apache.commons.lang3.RandomStringUtils;

public class Main2 {

	public static void main(String[] args) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
		String pwd = RandomStringUtils.random( 15, characters );
		System.out.println( pwd );
	}
}
