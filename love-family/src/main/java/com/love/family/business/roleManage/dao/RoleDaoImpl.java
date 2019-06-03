package com.love.family.business.roleManage.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.love.family.business.loginManage.entity.SysRole;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class RoleDaoImpl extends BaseHibernate4QueryDao<SysRole>  implements RoleDao {

	@Override
	public SysRole findRoleById(Long roleId) {
		StringBuffer buffer = new StringBuffer("");
		buffer.append("from SysRole where id = :id");
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("id", roleId);
		SysRole sysRole = this.findOneEntityObject(buffer.toString(), conditionMap);
		return sysRole;
	}

}
