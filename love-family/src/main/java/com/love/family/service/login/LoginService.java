package com.love.family.service.login;

import java.util.Map;


public interface LoginService {
	boolean login(Map<String,Object> param);

	boolean addLoginUser(Map<String, Object> pageRequest);
}
