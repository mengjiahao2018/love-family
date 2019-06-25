package com.love.family.business.userRoleManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.love.family.business.userRoleManage.entity.SysUserRole;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class UserRoleDaoImpl extends BaseHibernate4QueryDao<SysUserRole> implements UserRoleDao {


	@Override
	public Page<SysUserRole> queryPageUserRole(Pageable pageable, Map<String, Object> param) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from SysUserRole t where 1=1 ");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank((String)param.get("roleName"))) {
			conditionMap.put("roleName", (String)param.get("roleName"));
			buffer.append(" and t.roleId in (select t1.id from RoleInfo t1 where t1.name like :roleName ) ");
		}
		if(StringUtils.isNotBlank((String)param.get("userName"))) {
			conditionMap.put("userName", (String)param.get("userName"));
			buffer.append(" and t.userId in (select t2.id from UserModel t2 where t2.userName like :userName ) ");
		}
		return this.findEntityObjects(buffer.toString(), conditionMap, pageable);
	}

	@Override
	public void deleteUserRole(SysUserRole sysUserRole) {
		this.delete(sysUserRole);
	}

	@Override
	public List searchUserRoleDataByUserIdAndRoleId(Long roleId, Long userId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from SysUserRole r where r.userId=:userId and r.roleId = :roleId");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("userId", userId);
		conditionMap.put("roleId", roleId);
		return findEntityObjects(buffer.toString(), conditionMap);
	}

	@Override
	public void saveUserRole(SysUserRole sysUserRole) {
		this.save(sysUserRole);
	}

}
