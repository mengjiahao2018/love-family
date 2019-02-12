package com.love.family.dao.user;

import com.love.family.entity.user.LoginUser;

public interface LoginDao {
	boolean login(String loginName, String password);

	boolean addLoginUser(String loginName, String password);

	LoginUser findLoginUserByLoginName(String loginName);
}
