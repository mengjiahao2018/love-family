package com.love.family.business.roleManage.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.roleManage.entity.RoleInfo;
import com.love.family.pub.rbac.privilege.model.RolePrivilege;

public interface RoleService {

	List findRolesByUserId(Long id);

	RoleInfo findRoleById(Long roleId);

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
	Page<RoleInfo> queryPageRole(Pageable pageable, RoleInfo role);
	/**
	 * 根据角色ID和角色Code查询角色是否已经存在
	 * @param id
	 * @param roleCode
	 * @return
	 */
	List<RoleInfo> searchRoleDataByCodeUpd(Long id, String roleCode);
	/**
	 * 根据角色Code查询角色是否已经存在
	 * @param roleCode
	 * @return
	 */
	List<RoleInfo> searchRoleDataByCode(String roleCode);
	
	/**
	 * 更新或者新增角色
	 * @param role
	 */
	void saveRole(RoleInfo role);
	/**
	 * 根据角色ID查询角色
	 * @param id
	 * @return
	 */
	RoleInfo findRoleByRoleId(Long id);

	void deleteRolerole(RoleInfo role);

	List<RoleInfo> findAllRole();

}
