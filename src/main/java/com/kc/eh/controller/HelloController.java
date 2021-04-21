package com.kc.eh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kc.eh.cache.LocalCache;
import com.kc.eh.dao.SyslogDao;
import com.kc.eh.entity.SysUser;

@Controller
public class HelloController {
	@Autowired
	private SyslogDao syslogDao;
	
	@RequestMapping("doSayHello")
	@ResponseBody
	public String doSayHello() {
		System.out.println(syslogDao.findObject(1));
		return "hello";
	}
	
	// 本地缓存保存测试
	// 本地缓存清除时间 单位秒 一下暂定 60秒过期
	private final Long expireTime = 60L; 
	LocalCache cache = new LocalCache();
	@RequestMapping("cacheSave")
	@ResponseBody
	public String cacheSave() {
		cache.putValue("testData", "testCacheData", expireTime);
		SysUser user = new SysUser();
		user.setId(1);
		user.setUsername("测试");
		cache.putValue("testUser", user, expireTime);
		return "测试保存数据到缓存";
	}
	
	// 本地缓存获取测试
	@RequestMapping("cacheGet")
	@ResponseBody
	public String cacheGet() {
		Object testData = cache.getValue("testData");
		Object testUser = cache.getValue("testUser");
		String strData = null;
		if (testData != null) {
			strData = (String) testData;
		}
		SysUser user = null;
		String strUser = null;
		if (testUser != null) {
			user = (SysUser) testUser;
			strUser = user.toString();
		}
		return "strData:"+strData+"----strUser:"+strUser;
	}
}
