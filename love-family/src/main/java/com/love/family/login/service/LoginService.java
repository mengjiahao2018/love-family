package com.love.family.login.service;

import java.util.Map;


public interface LoginService {
	boolean login(Map<String,Object> param);

	boolean addLoginUser(Map<String, Object> pageRequest);
}
