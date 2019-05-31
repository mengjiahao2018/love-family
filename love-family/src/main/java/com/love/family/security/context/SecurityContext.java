package com.love.family.security.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.love.family.security.model.UserInfo;

public class SecurityContext {

	public static UserInfo getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication == null ? null : authentication.getPrincipal();
		if ((principal == null) || (!(principal instanceof UserInfo))) {
			return null;
		}
		return (UserInfo) principal;
	}
}
