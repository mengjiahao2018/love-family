package com.love.family.pub.rbac.system.util;

import org.springframework.stereotype.Component;

import com.love.family.security.context.SecurityContext;
import com.love.family.security.model.UserInfo;

@Component
public class UserInfoUtils {

	public static UserInfo getCurrentUserInfo() {
		UserInfo userInfo = SecurityContext.getCurrentUser();
		return userInfo;
	}
}
