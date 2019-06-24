package com.love.family.business.roleManage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.roleManage.entity.GenericRole;
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

	/**
	 * 查询所有角色
	 * @param pageable
	 * @param role
	 * @return
	 */
	Page<GenericRole> queryPageRole(Pageable pageable, GenericRole role);

}
