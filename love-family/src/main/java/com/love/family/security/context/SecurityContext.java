package com.love.family.security.context;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.love.family.security.model.UserInfo;

public class SecurityContext {
	
	private static Set<String> allFunctions = new HashSet<String>();
	
	public static UserInfo getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication == null ? null : authentication.getPrincipal();
		if ((principal == null) || (!(principal instanceof UserInfo))) {
			return null;
		}
		return (UserInfo) principal;
	}

	public static Set<String> getAllFunctions() {
		return allFunctions;
	}

	public static void setAllFunctions(Set<String> allFunctions) {
		SecurityContext.allFunctions = allFunctions;
	}


	
	
}
