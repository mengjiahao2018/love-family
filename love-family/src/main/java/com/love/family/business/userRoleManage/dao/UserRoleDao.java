package com.love.family.business.userRoleManage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.userRoleManage.entity.SysUserRole;


public interface UserRoleDao {

	Page<SysUserRole> queryPageUserRole(Pageable pageable, Map<String, Object> conditionMap);

	void deleteUserRole(SysUserRole sysUserRole);

	List searchUserRoleDataByUserIdAndRoleId(Long roleId, Long userId);

	void saveUserRole(SysUserRole sysUserRole);

}
