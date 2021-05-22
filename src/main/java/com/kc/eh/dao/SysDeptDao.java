package com.kc.eh.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.kc.eh.entity.SysDept;
@Mapper
public interface SysDeptDao {

	SysDept findById(@Param("id") String id);
}
