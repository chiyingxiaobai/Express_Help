package com.kc.eh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kc.eh.dao.SyslogDao;

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
}
