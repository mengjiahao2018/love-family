package com.love.family.business.userRoleManage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.love.family.business.userRoleManage.dao.UserRoleDao;
import com.love.family.business.userRoleManage.entity.UserRole;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	UserRoleDao userRoleDao;
	
	@Override
	public Page<UserRole> queryPageUserRole(Pageable pageable, Map<String, Object> conditionMap) {
		return userRoleDao.queryPageUserRole(pageable,conditionMap);
	}

	@Override
	public void deleteUserRole(UserRole sysUserRole) {
		userRoleDao.deleteUserRole(sysUserRole);
	}

	@Override
	public List searchUserRoleDataByUserIdAndRoleId(Long roleId, Long userId) {
		return userRoleDao.searchUserRoleDataByUserIdAndRoleId(roleId,userId);
	}

	@Override
	public void saveUserRole(UserRole sysUserRole) {
		userRoleDao.saveUserRole(sysUserRole);
	}

}
