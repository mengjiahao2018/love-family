package com.love.family.business.roleManage.dao;

import java.util.List;

public interface UserRoleDao {

	List findRolesByUserId(Long id);

}
