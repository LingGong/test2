package com.base.util;

import java.security.MessageDigest;

/**
 * @ClassName: MD5
 * @Description: md5加密
 * @author: gl
 * @date: 2018年1月16日 下午1:38:36
 */
public class MD5 {

	public MD5() {
	}

	public static String encode(String sourceString) {
		String resultString = null;
		try {
			resultString = new String(sourceString);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byte2hexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return resultString.toUpperCase();
	}


	public static final String byte2hexString(byte bytes[]) {
		StringBuffer buf = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; i++) {
			if ((bytes[i] & 0xff) < 16)
				buf.append("0");
			buf.append(Long.toString(bytes[i] & 0xff, 16));
		}

		return buf.toString();
	}

}
