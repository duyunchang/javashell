package com.demo.vod.config.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my")
public class MyConfig {

	private String redisFirstKey;
	private String redisSecondKey;
	private Integer redisTimeOut;



	public MyConfig() {
		super();
	}



	public Integer getRedisTimeOut() {
		return redisTimeOut;
	}

	public void setRedisTimeOut(Integer redisTimeOut) {
		this.redisTimeOut = redisTimeOut;
	}

	public String getRedisFirstKey() {
		return redisFirstKey;
	}

	public void setRedisFirstKey(String redisFirstKey) {
		this.redisFirstKey = redisFirstKey;
	}

	public String getRedisSecondKey() {
		return redisSecondKey;
	}

	public void setRedisSecondKey(String redisSecondKey) {
		this.redisSecondKey = redisSecondKey;
	}

	

}
