package com.kc.eh.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 此Controller主要负责响应一些页面
 */
@Controller
@RequestMapping("/")
public class PageController {
	@RequestMapping("doIndexUI")
	public String doIndexUI() {
		return "starter";
	}
	/**
	 * 返回日志列表页面
	 * @return
	 */
	@RequestMapping("log/log_list")
	public String doLogUI() {
		return "sys/log_list";
	}
	
	@RequestMapping("doPageUI")
	public String doPageUI() {
		return "common/page";
	}
	
	@RequestMapping("/doLoginUI")
	public String doLoginUI() {
		return "login";
	}
}






