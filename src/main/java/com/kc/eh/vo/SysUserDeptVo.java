package com.kc.eh.vo;

import java.io.Serializable;
import java.util.Date;

import com.kc.eh.entity.SysDept;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class SysUserDeptVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5691030209266025463L;
	
	private Integer id;
	private String username;
	private String password;
	private String salt;
	private String email;
	private String mobile;
	private Integer valid=1;
	private SysDept sysDept; //private Integer deptId;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser; 
	

}
