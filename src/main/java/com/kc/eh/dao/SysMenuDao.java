package com.kc.eh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysMenuDao {

	public List<String> findPermisssions(@Param("menuIds") Integer[] menuIds);
}
