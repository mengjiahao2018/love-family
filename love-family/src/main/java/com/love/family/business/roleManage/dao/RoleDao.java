package com.love.family.business.roleManage.dao;

import com.love.family.business.roleManage.entity.GenericRole;

public interface RoleDao {

	GenericRole findRoleById(Long roleId);

}
