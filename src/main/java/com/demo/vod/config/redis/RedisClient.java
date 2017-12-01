package com.demo.vod.config.redis;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisClient {

	@Autowired
	private JedisPool jedisPool;

	public void set(String key, String value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}

	public String getstr(String key) throws Exception {

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return jedis.get(key);
		} finally {
			// 返还到连接池
			jedis.close();
		}
	}
	
	public byte[] get(String key) {
		byte[] bys = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			bys = jedis.get(key.getBytes());
		} catch (Exception e) {
			System.out.printf("获取缓存，原因:%s" + e.getMessage(), e);
		} finally {
			jedis.close();
		}

		return bys;
	}

	public void set(String key, Serializable obj, Integer timeOut) {
		
		Jedis jedis = null;

		try {
			jedis = jedisPool.getResource();
			byte[] bts = ObjectHelper.getBytesByObject(obj);	
			
			if (timeOut > 0) {
				jedis.setex(key.getBytes(), timeOut, bts);
			} else {
				jedis.set(key.getBytes(), bts);
			}
		} catch (Exception e) {
			System.out.printf("保存缓存，原因:%s" + e.getMessage(), e);
		} finally {
			jedis.close();
		}

	}

	public void remove(String cacheKey) {
		String key = cacheKey.replaceAll("#cacheKey#", cacheKey);
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			System.out.printf("删除缓存，原因:%s" + e.getMessage(), e);
		} finally {
			jedis.close();
		}

	}

	public void setHash(String key, String field, String value, Integer timeOut) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			/*
			 * if (timeOut > 0) { jedis.hsetnx(keyArr, fieldArr, bts);
			 * jedis.expire(keyArr, timeOut); } else { jedis.hsetnx(keyArr,
			 * fieldArr, bts); }
			 */
			if (timeOut > 0) {
				jedis.hset(key, field, value);
				jedis.expire(key, timeOut);
			} else {
				jedis.hset(key, field, value);
			}
		} catch (Exception e) {
			System.out.printf("保存缓存，原因:%s" + e.getMessage(), e);
		} finally {
			jedis.close();
		}

	}

	public String getInHashObjArr(String key, String field) {
		String bys = null;

		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();

			if (jedis.hexists(key, field)) {
				bys = jedis.hget(key, field);
			}
		} catch (Exception e) {
			System.out.printf("获取缓存，原因:%s" + e.getMessage(), e);
		} finally {
			jedis.close();
		}

		return bys;
	}

	public void removeHash(String key) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key.getBytes());
		} catch (Exception e) {
			System.out.printf("删除缓存，原因:%s" + e.getMessage(), e);
		} finally {
			jedis.close();
		}

	}

	///////////////////////////// Redis Zset/////////////////////////////////
	public void setZset(String key, Serializable obj, double score, int timeOut) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			byte[] keyArr = key.getBytes();
			byte[] objArr;

			String objstr = JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue);
			objArr = objstr.getBytes();
			jedis.zadd(keyArr, score, objArr);
			if (timeOut > 0) {
				jedis.expire(keyArr, timeOut);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.printf("保存缓存，序列化object失败，原因:%s" + e.getMessage(), e);
			return;
		} finally {
			jedis.close();
		}

	}
	

}
