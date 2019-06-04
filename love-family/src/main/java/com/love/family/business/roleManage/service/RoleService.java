package com.love.family.business.roleManage.service;

import java.util.List;

import com.love.family.business.loginManage.entity.GenericRole;
import com.love.family.pub.rbac.privilege.model.RolePrivilege;

public interface RoleService {

	List findRolesByUserId(Long id);

	GenericRole findRoleById(Long roleId);

	/**
	 * 得到角色对应的所有相关权限信息
	 * 
	 * @param id
	 * @return
	 */
	RolePrivilege getRolePrivilege(Long roleId);

}
