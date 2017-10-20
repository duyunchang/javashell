package com.demo.vod.util;

import java.util.UUID;

public class BusinessUtil {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().toLowerCase();
	}
	
}
