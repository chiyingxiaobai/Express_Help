package com.kc.eh.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SysDept implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5994527655482450537L;
	private Integer id;
	private String name;
	private Integer parentId;
	private Integer sort;
	private String note;
	private Date createdTime;
	private Date modifiedTime;
	private String createdUser;
	private String modifiedUser;
}
