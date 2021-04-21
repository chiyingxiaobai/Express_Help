package com.kc.eh.cache;

import java.io.Serializable;
/**
 * *本地缓存保存的实体 
 * @author zcc
 *
 */
public class CacheEntity implements Serializable {
	
	private static final long serialVersionUID = 7172649826282703560L;

	/**
	 * 值
	 */
	private Object value;

	/**
	 * 保存的时间戳
	 */
	private long gmtModify;

	/**
	 * 过期时间 
	 */
	private Long expire; 

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public long getGmtModify() {
		return gmtModify;
	}

	public void setGmtModify(long gmtModify) {
		this.gmtModify = gmtModify;
	}

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
	}

	public CacheEntity(Object value, long gmtModify, Long expire) {
		super();
		this.value = value;
		this.gmtModify = gmtModify;
		this.expire = expire;
	}
}
