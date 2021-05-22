package com.kc.eh.service;

import org.springframework.stereotype.Service;

import com.kc.eh.common.vo.PageObject;
import com.kc.eh.vo.SysUserDeptVo;


public interface SysUserService {
	PageObject<SysUserDeptVo> findPageObjects(
			String username,
			Integer pageCurrent);
}
