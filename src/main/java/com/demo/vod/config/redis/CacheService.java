package com.demo.vod.config.redis;

import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 记得每次拿jedis后 一定要close()
 * 
 * @author dyc
 *
 */
@Component
public class CacheService {

	private static final Logger LOGGER = Logger.getLogger(CacheService.class);
	
	@Autowired
	private JedisPool jedisPool;

	@Autowired
	private MyConfig config;

	/**
	 * <保存key-value>
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean saveKeyValue(final String key, final String value) {
		boolean result = false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.sadd(key, value);
			
			jedis.expire(key, config.getRedisTimeOut());
			result = true;
		} catch (Exception e) {
			LOGGER.error("Fun[saveKeyValue] redis except: {}",e);
		} finally {
			jedis.close(); //返还到连接池
		}
		LOGGER.info("Fun[saveKeyValue] redis 保存key=" + key + " value=" + value);
		return result;
	}

	/**
	 * <查询key-value>
	 * 
	 * @param key
	 * @return
	 */
	public Set<String> getValue(final String key) {
		Set<String> result = new HashSet<String>();
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			result = (Set<String>) jedis.smembers(key);
		} catch (Exception e) {
			LOGGER.error("Fun[getValue] redis except: {}",e);
			
		} finally {
			jedis.close(); //返还到连接池
			
		}
		LOGGER.info("Fun[getValue] redis 根据key=" + key + " 查询value");
		return result;
	}

	/**
	 * <把key-value插入到缓存中>
	 * 
	 * @param key
	 * @param values
	 */
	public void saveValues(final String key, final HashSet<String> values) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			for (String s : values) {
				jedis.sadd(key, s);
			}
			jedis.expire(key, config.getRedisTimeOut());// config.getRedisTimeOut()
		} catch (Exception e) {
			LOGGER.error("Fun[saveValues] redis except: {}",e);
		} finally {
			jedis.close(); //返还到连接池
		}
		LOGGER.info("Fun[saveValues] redis 存储values=" + values + " key+" + key);
	}

	public void deleteKeyValue(final String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			LOGGER.error("Fun[deleteKeyValue] redis except: {}",e);
		} finally {
			jedis.close(); //返还到连接池
		}

		LOGGER.info("Fun[deleteKeyValue] redis 删除= key+" + key);
	}

}
