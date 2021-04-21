package com.kc.eh.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 简易本地缓存的实现类 
 * 参考代码：https://www.iteye.com/blog/wujiu-2179087
 * @author zcc
 *
 */
public class LocalCache {
	// 默认的缓存容量
	private static int DEFAULT_CAPACITY = 512;
	// 最大容量
	private static int MAX_CAPACITY = 100000;
	// 刷新缓存的频率
	private static int MONITOR_DURATION = 30;
	// 启动监控线程
	static {
		new Thread(new TimeoutTimerThread()).start();
	}
	// 使用默认容量创建一个Map
	private static ConcurrentHashMap<String, CacheEntity> cache = new ConcurrentHashMap<String, CacheEntity>(
			DEFAULT_CAPACITY);

	/**
	 * 将key-value 保存到本地缓存并制定该缓存的过期时间
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 *            过期时间，如果是-1 则表示永不过期
	 * @return
	 */
	public boolean putValue(String key, Object value, Long expireTime) {
		return putCloneValue(key, value, expireTime);
	}

	/**
	 * 将值通过序列化clone 处理后保存到缓存中，可以解决值引用的问题
	 * 
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	private boolean putCloneValue(String key, Object value, Long expireTime) {
		try {
			if (cache.size() >= MAX_CAPACITY) {
				return false;
			}
			// 序列化赋值
			CacheEntity entityClone = clone(new CacheEntity(value,
					System.nanoTime(), expireTime));
			cache.put(key, entityClone);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * 序列化 克隆处理
	 * 
	 * @param object
	 * @return
	 */
	private <T extends Serializable> T clone(T object) {
		T cloneObject = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.close();
			ByteArrayInputStream bais = new ByteArrayInputStream(
					baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			cloneObject = (T) ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cloneObject;
	}

	/**
	 * 从本地缓存中获取key对应的值，如果该值不存则则返回null
	 * 
	 * @param key
	 * @return
	 */
	public Object getValue(String key) {
		if (cache.get(key) != null) {
			return cache.get(key).getValue();
		}
		return null;
	}

	/**
	 * 清空所有
	 */
	public void clear() {
		cache.clear();
	}

	/**
	 * 过期处理线程
	 * 
	 * @author Lenovo
	 * @version $Id: LocalCache.java, v 0.1 2014年9月6日 下午1:34:23 Lenovo Exp $
	 */
	static class TimeoutTimerThread implements Runnable {
		public void run() {
			while (true) {
				try {
					//System.out.println("Cache monitor");
					TimeUnit.SECONDS.sleep(MONITOR_DURATION); // 单位秒
					checkTime();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 过期缓存的具体处理方法
		 * 
		 * @throws Exception
		 */
		private void checkTime() throws Exception {
			// "开始处理过期 ";

			for (String key : cache.keySet()) {
				CacheEntity tce = cache.get(key);
				/*
				 * System.nanoTime()
				 * 该方法返回最准确的可用系统计时器的当前值，以毫微秒为单位。
				 */				
				/*
				 * TimeUnit.NANOSECONDS.toSeconds(); 
				 * 该方法将括号中的 毫微秒 数 转化为秒
				 * TimeUnit 类作用
				 * 1. 时间颗粒度转换 如上述 毫微秒 转 秒
				 * 2. 延时
				 * 延时实例
				 * TimeUnit.SECONDS.sleep(5);  // 延时5秒
				 * 本类 125 行有使用延时用法
				 */
				// timoutTime 单位秒
				long timoutTime = TimeUnit.NANOSECONDS.toSeconds(System
						.nanoTime() - tce.getGmtModify());
				// " 过期时间 : "+timoutTime);
				// 此处比较时间单位为 秒 所以在设定缓存数据过期时间时单位也应是 秒
				if (tce.getExpire() > timoutTime) {   // 时间比较 单位秒
					continue;
				}
				System.out.println(" 清除过期缓存 ： " + key);
				// 清除过期缓存和删除对应的缓存队列
				cache.remove(key);
			}
		}
	}
}
