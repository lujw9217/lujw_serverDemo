package com.example.serverdemo.redis.dao;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存 通用的db操作
 */
public interface ICaCheRepository {
	 void leftPush(String key, Object obj);

	 Object rightPop(String key);

	 Object getValue(String key);
	
	 Set<String> getKeys(String key);
	
	 void sAdd(String key, Object values);
	
	 Set<Object> sMembers(String key);
	
	 void sRemove(String key, Object values);
	
	 void setValue(String key, Object obj);
	
	 void setValue(String key, Object obj, long timeout, TimeUnit timeunit);

	 void setStrValue(String key, String value);

	 void setStrValue(String key, String value, long timeout, TimeUnit timeunit);
	
	 String getStrValue(String key);
	
	 void removeStrValue(String key);

	 void setBit(String key, Long offset, boolean value);

	 byte[] getBit(String key);

	 Long incr(String key);

	 Long decr(String key);
     Long incr(String key, long timeout, TimeUnit timeUnit);

     Long getLong(String key);
}