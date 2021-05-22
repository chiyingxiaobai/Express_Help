package com.kc.eh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kc.eh.entity.SysUser;
import com.kc.eh.vo.SysUserDeptVo;
@Mapper
public interface SysUserDao {
	public SysUser findUserByUserName(String username);
	
	public List<SysUserDeptVo> findPageObjects(
			@Param("username") String username,
			@Param("startIndex") Integer startIndex,
			@Param("pageSize") Integer pageSize);
	
	public int getRowCount(@Param("username") String username);
}
