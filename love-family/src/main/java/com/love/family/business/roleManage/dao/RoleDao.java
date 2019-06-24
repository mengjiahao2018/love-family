package com.love.family.business.roleManage.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.love.family.business.roleManage.entity.GenericRole;

public interface RoleDao {

	GenericRole findRoleById(Long roleId);

	Page<GenericRole> queryPageRole(Pageable pageable, GenericRole role);

	List<GenericRole> searchRoleDataByCodeUpd(Long id, String roleCode);

	List<GenericRole> searchRoleDataByCode(String roleCode);

	void saveRole(GenericRole role);

	void deleteRolerole(GenericRole role);

}
