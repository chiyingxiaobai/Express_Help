package com.kc.test;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component //标注将类交给spring管理
@Scope("singleton") //标注创建类的模式是单列
public class DefaultCache {
	
	public DefaultCache() {
		System.out.println("cache()");
	}
	
	@PostConstruct //标注在类实例初始化执行的方法
	public void init() {
		System.out.println("init()");
	}
	
	@PreDestroy	//标注在类实例销毁前执行的方法
	public void destroy() {
		System.out.println("destroy()");
	}
	
	
	
}
