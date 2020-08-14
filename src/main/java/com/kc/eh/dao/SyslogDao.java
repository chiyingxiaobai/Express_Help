package com.kc.eh.dao;


import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SyslogDao {
	@Delete("delete from student where id = #{id}")
	int deleteObject(Integer id);
	
	
	Map<String, Object> findObject(Integer id);
}
