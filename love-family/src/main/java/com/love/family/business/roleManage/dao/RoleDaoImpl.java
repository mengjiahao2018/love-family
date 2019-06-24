package com.love.family.business.roleManage.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.love.family.business.roleManage.entity.GenericRole;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class RoleDaoImpl extends BaseHibernate4QueryDao<GenericRole>  implements RoleDao {

	@Override
	public GenericRole findRoleById(Long roleId) {
		StringBuffer buffer = new StringBuffer("");
		buffer.append("from GenericRole where id = :id");
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("id", roleId);
		GenericRole role = this.findOneEntityObject(buffer.toString(), conditionMap);
		return role;
	}

}
