package com.love.family.business.roleManage.dao;

import com.love.family.business.loginManage.entity.GenericRole;

public interface RoleDao {

	GenericRole findRoleById(Long roleId);

}
