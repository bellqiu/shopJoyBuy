package com.spshop.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * YourSpay支付MD5加密和Base64加密
 * @author www.yourspay.com
 *
 */
public class Encrypt {

    /**
     * md5加密
     * @param String input
     * @return String output
     */	
	public  static String MD5(String input){
		String output = "";
	    char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
	    try {
	         byte[] strTemp = input.getBytes();
	         java.security.MessageDigest mdTemp = java.security.MessageDigest.getInstance("MD5");
	         mdTemp.update(strTemp);
	         byte[] md = mdTemp.digest();
	         int j = md.length;
	         char str[] = new char[j * 2];
	         int k = 0;
	         for (int i = 0; i < 16; i++) {
		         byte byte0 = md[i];
		         str[k++] = hexDigits[byte0 >>> 4 & 0xf];
		         str[k++] = hexDigits[byte0 & 0xf];
	         }
	         output =  new String(str).toString();
	    } catch (Exception e){
	         return null;
	    }
	    return output;
	}
	
	/**
	 * 先对字符串进行URL的UTF-8编码,然后再进行base64位编码
	 * @param String input
	 * @return String output
	 */
	public  static String URLEncode_BASE64(String input) {
		String output = "";
		String urlEncode = "";
		if (input == null){
			return null;
		}
		try {
			urlEncode = URLEncoder.encode(input.toString(),"UTF-8");
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			output =  encoder.encode(urlEncode.getBytes());
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return output;
	}}
