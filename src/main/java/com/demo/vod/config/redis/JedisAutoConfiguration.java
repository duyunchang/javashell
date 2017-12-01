package com.demo.vod.config.redis;


import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


@Configuration
@EnableCaching
public class JedisAutoConfiguration {  
 
	@Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.max-wait}")
    private long maxWaitMillis;

 
    
    @Bean
    public JedisPool redisPoolFactory() {
       
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);

        JedisPool jedisPool = new JedisPool( jedisPoolConfig,   host,   port,
        	       timeout);
    
        return jedisPool;
    } 
    
    public static void main(String[] args) {
    	JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(100);
        jedisPoolConfig.setMaxWaitMillis(1000*20);

        JedisPool jedisPool = new JedisPool( jedisPoolConfig,   "192.168.23.216",   6379,
        	       1000*20);
       
        Jedis jedis = jedisPool.getResource();
//           while(true){
	       HashSet<String> values =new HashSet<String>();
	       values.add("11");
	       values.add("12");
	       values.add("13");
	       values.add("14");
	       values.add("15");
	       for(String s:values){
				jedis.sadd("1", s);
			}
			jedis.expire("1", 86400);
//       }
	}
          
}
