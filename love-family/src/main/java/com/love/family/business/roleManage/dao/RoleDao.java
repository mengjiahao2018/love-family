package com.love.family.business.roleManage.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.roleManage.entity.RoleInfo;

public interface RoleDao {

	RoleInfo findRoleById(Long roleId);

	Page<RoleInfo> queryPageRole(Pageable pageable, RoleInfo role);

	List<RoleInfo> searchRoleDataByCodeUpd(Long id, String roleCode);

	List<RoleInfo> searchRoleDataByCode(String roleCode);

	void saveRole(RoleInfo role);

	void deleteRolerole(RoleInfo role);

	List<RoleInfo> findAllRole();
	
	public List findRolesByUserId(Long id);

}
