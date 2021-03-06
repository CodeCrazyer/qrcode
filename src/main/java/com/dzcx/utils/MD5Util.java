/**
 * Copyright 2014 the original author or authors. All rights reserved.
 */
package com.dzcx.utils;

import java.security.MessageDigest;

/**
 * @author Administrator
 * @since 0.0.1
 *
 */
public class MD5Util {

	public static String getMD5Code(String source){
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = source.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
    public static void main(String[] argc){
    	System.out.println(MD5Util.getMD5Code("123456"));
    }
}
