package com.example.serverdemo.redis.dao.impl;


import com.example.serverdemo.redis.dao.ICaCheRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存 通用的db操作
 * 
 */
@Service("redisCaCheRepository")
public class RedisCaCheRepository implements ICaCheRepository {

	// 实例化 RedisTemplate<String, Object>
	@Resource(name = "objRedisTemplate")
	private RedisTemplate<String, Object> objRedisTemplate;

	@Resource(name = "stringRedisTemplate")
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private RedisTemplate<String, ?> redisTemplate;

	/**
	 * @description   : 从队列的左侧插入一条数据
	 * @method_name   : leftPush
	 * @param         : [key, obj]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:33
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public void leftPush(String key, Object obj) {
		objRedisTemplate.boundListOps(key).leftPush(obj);
	}

	/**
	 * @description   : 从队列的右侧弹出一条数据
	 * @method_name   : rightPop
	 * @param         : [key]
	 * @return        : java.lang.Object
	 * @throws        :
	 * @date          : 2019/12/10 17:34
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public Object rightPop(String key) {
		return objRedisTemplate.boundListOps(key).rightPop();
	}

	/**
	 * @description   : 根据key获取value
	 * @method_name   : getValue
	 * @param         : [key]
	 * @return        : java.lang.Object
	 * @throws        :
	 * @date          : 2019/12/10 17:34
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public Object getValue(String key) {
		return objRedisTemplate.boundValueOps(key).get();
	}
	
	/**
	 * @description   : 根据key前缀， 获取当前key前缀下所有符合要求的key列表
	 * @method_name   : getKeys
	 * @param         : [key]
	 * @return        : java.util.Set<java.lang.String>
	 * @throws        :
	 * @date          : 2019/12/10 17:34
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public Set<String> getKeys(String key){
		return objRedisTemplate.keys(key);
	}
	
	/**
	 * @description   : 将values以set集合方式，对应key，入redis 可以追加
	 * @method_name   : sAdd
	 * @param         : [key, values]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:34
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public void sAdd(String key, Object values){
		objRedisTemplate.opsForSet().add(key, values);
	}
	
	/**
	 * @description   : 根据key，将values以set集合方式全部取出
	 * @method_name   : sMembers
	 * @param         : [key]
	 * @return        : java.util.Set<java.lang.Object>
	 * @throws        :
	 * @date          : 2019/12/10 17:34
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public Set<Object> sMembers(String key){
		return objRedisTemplate.boundSetOps(key).members();
	}
	
	/**
	 * @description   : 根据key、values。指定删除set集合中的元素
	 * @method_name   : sRemove
	 * @param         : [key, values]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:35
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public void sRemove(String key, Object values){
		objRedisTemplate.opsForSet().remove(key, values);
	}
	

	/**
	 * @description   : 向redis插入一条key value数据
	 * @method_name   : setValue
	 * @param         : [key, obj]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:35
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public void setValue(String key, Object obj) {
		objRedisTemplate.opsForValue().set(key, obj);
	}
	
	/**
	 * @description   : 向redis插入一条key value数据 并设置有效期
	 * @method_name   : setValue
	 * @param         : [key, obj, timeout, timeunit]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:35
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public void setValue(String key, Object obj,long timeout,TimeUnit timeunit) {
		objRedisTemplate.opsForValue().set(key, obj,timeout,timeunit);
	}
	
	/**
	 * @description   : 设置字符串值，key value均为字符串
	 * @method_name   : setStrValue
	 * @param         : [key, value]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:35
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public void setStrValue(String key, String value){
		stringRedisTemplate.opsForValue().set(key, value);
	}
	
	/**
	 * @description   : 新增向redis中插入value为String类型的字符串
	 * @method_name   : setStrValue
	 * @param         : [key, value, timeout, timeunit]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:35
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public void setStrValue(String key, String value ,long timeout, TimeUnit timeunit) {
		stringRedisTemplate.opsForValue().set(key,value,timeout,timeunit);
	}
	
	/**
	 * @description   : 获取字符串类型值
	 * @method_name   : getStrValue
	 * @param         : [key]
	 * @return        : java.lang.String
	 * @throws        :
	 * @date          : 2019/12/10 17:35
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public String getStrValue(String key){
		return stringRedisTemplate.boundValueOps(key).get();
	}
	
	/**
	 * @description   : 删除字符串类型值
	 * @method_name   : removeStrValue
	 * @param         : [key]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:35
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	@Override
	public void removeStrValue(String key) {
		// TODO Auto-generated method stub
		stringRedisTemplate.delete(key);
	}

	/**
	 * @description   : 使用setbit方式，向redis插入一条数据
	 * @method_name   : setBit
	 * @param         : [key, offset, value]
	 * @return        : void
	 * @throws        :
	 * @date          : 2019/12/10 17:36
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public void setBit(String key, Long offset, boolean value) {
		redisTemplate.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				connection.setBit(key.getBytes(), offset, value);
				return true;
			}
		});
	}

	/**
	 * @description   : 从根据key，从redis中获取setbit数据
	 * @method_name   : getBit
	 * @param         : [key]
	 * @return        : byte[]
	 * @throws        :
	 * @date          : 2019/12/10 17:36
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public byte[] getBit(String key) {

		byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
			@Override
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
		return result;
	}

	/**
	 * @description   : key对应的value为数字字符时 根据key，将其value值自增+1 redis中有此key，自增+1，没有key，新建key再自增+1
	 * @method_name   : incr
	 * @param         : [key]
	 * @return        : java.lang.Long
	 * @throws        :
	 * @date          : 2019/12/10 17:36
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public Long incr(String key) {

		// redis原子性 自增 自减实现
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());

		// 当前key的value自增一次，先自增，再获取自增后的值，返回值为自增之后的值
		Long increment = entityIdCounter.incrementAndGet();

		return increment;
	}
	
	/**
	 * @description   : key对应的value为数字字符时 根据key，将其value值自减-1
	 * @method_name   : decr
	 * @param         : [key]
	 * @return        : java.lang.Long
	 * @throws        :
	 * @date          : 2019/12/10 17:36
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
	public Long decr(String key) {
		// redis原子性 自增 自减实现
		RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());

		// 当前key的value自增一次，先自减，再获取自减后的值，返回值为自减之后的值
		Long decrement = entityIdCounter.decrementAndGet();

		return decrement;
	}

	/**
	 * @description   :  key对应的value为数字字符时 根据key，将其value值自增+1 redis中有此key，
     *                     自增+1，没有key，新建key再自增+1添加一个过期时间
	 * @method_name   : incr
	 * @param         : [key, timeout, timeUnit]
	 * @return        : java.lang.Long  
	 * @throws        : 
	 * @date          : 2019/7/30 11:08
	 * @version       : v1.00
	 * @author        : Lujw
	 * @update date   :
	 * @update author :
	 */
    public Long incr(String key,long timeout,TimeUnit timeUnit) {

        // redis原子性 自增 自减实现
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        // 设置key的过期时间
        entityIdCounter.expire(timeout,timeUnit);
        // 当前key的value自增一次，先自增，再获取自增后的值，返回值为自增之后的值
        Long increment = entityIdCounter.incrementAndGet();

        return increment;
    }

    /**
     * @description   : 获取 key对应的value为数字字符
     * @method_name   : getLong
     * @param         : [key]
     * @return        : java.lang.Long  
     * @throws        : 
     * @date          : 2019/7/30 11:08
     * @version       : v1.00
     * @author        : Lujw
     * @update date   :
     * @update author :
     */
    public Long getLong(String key) {
        // redis原子性 自增 自减实现
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        return entityIdCounter.get();
    }

}
