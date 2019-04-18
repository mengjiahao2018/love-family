package com.love.family.business.loginManage.dao;

import com.love.family.business.loginManage.entity.LoginUser;

public interface LoginDao {
	boolean login(String loginName, String password);

	boolean addLoginUser(String loginName, String password);

	LoginUser findLoginUserByLoginName(String loginName);
}
