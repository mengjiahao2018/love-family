package com.love.family.business.loginManage.dao;

import java.util.List;

import com.love.family.business.loginManage.entity.SysUser;

public interface LoginDao {
	boolean login(String loginName, String password);

	boolean addLoginUser(String loginName, String password);

	List<SysUser> findLoginUserByLoginName(String loginName);
}
