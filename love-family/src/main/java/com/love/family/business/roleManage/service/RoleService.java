package com.love.family.business.roleManage.service;

import java.util.List;

import com.love.family.business.loginManage.entity.SysRole;

public interface RoleService {

	List findRolesByUserId(Long id);

	SysRole findRoleById(Long roleId);

}
