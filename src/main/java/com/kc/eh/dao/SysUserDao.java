package com.kc.eh.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kc.eh.entity.SysUser;
@Mapper
public interface SysUserDao {
	public SysUser findUserByUserName(String username);
}
