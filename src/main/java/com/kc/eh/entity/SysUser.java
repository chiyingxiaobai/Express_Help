package com.kc.eh.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SysUser implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3061203001791142537L;
	
	private Integer id;
	private String username;
	private String password;
	private String salt;
	private String email;
	private String mobile;
	private Integer valid=1;
	private Integer deptId;
}
