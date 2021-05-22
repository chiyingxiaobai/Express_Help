package com.kc.eh.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kc.eh.common.exception.ServiceException;
import com.kc.eh.common.vo.PageObject;
import com.kc.eh.dao.SysUserDao;
import com.kc.eh.service.SysUserService;
import com.kc.eh.vo.SysUserDeptVo;

@Service
public class SysUserServiceImpl implements SysUserService {

	@Resource
	private SysUserDao sysUserDao;
	
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		// 1.数据合法性验证
		if (pageCurrent == null || pageCurrent <= 0)
			throw new ServiceException("参数不合法");
		// 2.依据条件获取总记录数
		int rowCount = sysUserDao.getRowCount(username);
		if (rowCount == 0)
			throw new ServiceException("记录不存在");
		// 3.计算startIndex的值
		int pageSize = 3;
		int startIndex = (pageCurrent - 1) * pageSize;
		// 4.依据条件获取当前页数据
		List<SysUserDeptVo> records = sysUserDao.findPageObjects(username, startIndex, pageSize);
		// 5.封装数据
		PageObject<SysUserDeptVo> pageObject = new PageObject<>();
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setRowCount(rowCount);
		pageObject.setPageSize(pageSize);
		pageObject.setRecords(records);
		pageObject.setPageCount((rowCount - 1) / pageSize + 1);
		return pageObject;
	}

}
