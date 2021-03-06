package com.kc.eh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMenuDao {

	public List<Integer> findMenuIdsByRoleIds(@Param("roleIds") Integer[] roleIds);
}
