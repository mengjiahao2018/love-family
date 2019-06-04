package com.love.family.business.roleManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.love.family.business.loginManage.entity.GenericRole;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class UserRoleDaoImpl extends BaseHibernate4QueryDao<GenericRole> implements UserRoleDao {

	@Override
	public List findRolesByUserId(Long id) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from GenericRole r where r.id in (select t.roleId from SysUserRole t where t.userId=:userId) ");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("userId", id);
		return findEntityObjects(buffer.toString(), conditionMap);
	}

}
