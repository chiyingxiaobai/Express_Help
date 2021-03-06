package com.kc.eh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface SysUserRoleDao {
	
	public List<Integer> findRoleIdsByUserId(Integer id);
}
