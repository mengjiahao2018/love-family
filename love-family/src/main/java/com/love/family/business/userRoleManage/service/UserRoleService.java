package com.love.family.business.userRoleManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.userRoleManage.entity.UserRole;

public interface UserRoleService {

	Page<UserRole> queryPageUserRole(Pageable pageable, Map<String, Object> conditionMap);

	void deleteUserRole(UserRole sysUserRole);

	List searchUserRoleDataByUserIdAndRoleId(Long roleId, Long userId);

	void saveUserRole(UserRole sysUserRole);

}
