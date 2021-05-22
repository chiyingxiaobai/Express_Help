package com.kc.eh.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kc.eh.common.vo.JsonResult;
import com.kc.eh.common.vo.PageObject;
import com.kc.eh.service.SysUserService;
import com.kc.eh.vo.SysUserDeptVo;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;

	@RequestMapping("/doLogin")
	@ResponseBody
	public JsonResult doLogin(String username, String password) {
		// 1.获取Subject对象
		Subject subject = SecurityUtils.getSubject();
		// 2.通过Subject提交用户信息,交给shiro框架进行认证操作
		// 2.1对用户进行封装
		UsernamePasswordToken token = new UsernamePasswordToken(username, // 身份信息
				password);// 凭证信息
		// 2.2对用户信息进行身份认证
		subject.login(token);
		// 分析:
		// 1)token会传给shiro的SecurityManager
		// 2)SecurityManager将token传递给认证管理器
		// 3)认证管理器会将token传递给realm
		return new JsonResult("login ok");
	}

	@RequestMapping("/doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}

	// @RequestMapping("/doFindPageObjects")
	@ApiOperation(value = "获取用户列表", notes = "对于null username 改为nuulStr传参，否则null username 会报404错误")
	@RequestMapping(value = "/{pageCurrent}/{username}", method = RequestMethod.GET)
	@ResponseBody
	public JsonResult doFindPageObjects(@PathVariable String pageCurrent, @PathVariable String username) {
		int parseInt = Integer.parseInt(pageCurrent);
		if ("nullStr".equals(username)) {
			username = "";
		}
		PageObject<SysUserDeptVo> pageObject = sysUserService.findPageObjects(username, parseInt);
		return new JsonResult(pageObject);
	}

	@RequestMapping("/doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjectsT(String username, Integer pageCurrent) {
		PageObject<SysUserDeptVo> pageObject = sysUserService.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}
}
