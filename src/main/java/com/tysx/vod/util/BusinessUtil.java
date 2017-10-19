package com.tysx.vod.util;

import java.util.UUID;

public class BusinessUtil {
	
	public static String getUUID() {
		return UUID.randomUUID().toString().toLowerCase();
	}
	
}
