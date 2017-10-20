package com.demo.vod.util;

import java.security.SecureRandom;
import java.util.Date;

public class UUIDUtil {

	/**
	 * 每位允许的字符
	 */
	private static final String POSSIBLE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";

	/**
	 * *
	 * 
	 * @return String 生成32位的随机数作为id
	 */
	public static String getUUID() {
		String datestamp = DateHelper.getDateTimeByyyyyMMddHHmmss(new Date());
		StringBuilder sb = new StringBuilder(18);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < 18; i++) {
			sb.append(POSSIBLE_CHARS.charAt(random.nextInt(POSSIBLE_CHARS.length())));
		}
		return datestamp + sb.toString();
	}

	/**
	 * 16位UUID+2位任务code
	 * 
	 * @return
	 */
	public static String getUUIDAddCode() {
		String datestamp = DateHelper.getDateTimeByyyyyMMddHHmmss(new Date());
		StringBuilder sb = new StringBuilder(16);
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < 16; i++) {
			sb.append(POSSIBLE_CHARS.charAt(random.nextInt(POSSIBLE_CHARS.length())));
		}
		return datestamp + sb.toString();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
