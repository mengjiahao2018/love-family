package com.love.family.business.roleFunctionManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.love.family.business.roleFunctionManage.entity.RoleFunction;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class RoleFunctionDaoSelfImpl  extends BaseHibernate4QueryDao<RoleFunction>  implements RoleFunctionDaoSelf {

	@Override
	public List<RoleFunction> findByFunctionId(Long id) {
		StringBuffer buffer  =  new StringBuffer();
		buffer.append("from RoleFunction t where t.functionId = : id ");
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("id", id);
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}

	@Override
	public void deleteByRoleFunctionId(Long id) {
		StringBuffer buffer  =  new StringBuffer();
		buffer.append("delete from RoleFunction t where t.functionId = : id ");
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("id", id);
		this.bulkUpdate(buffer.toString(), conditionMap);
	}

	@Override
	public Page<RoleFunction> queryRoleFunctionByRoleNameAndFunctionName(Pageable pageable, String roleName,
			String functionName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from RoleFunction s where s.roleId in (select t.id from GenericRole t where t.name like :roleName ) ");
		buffer.append(" and s.functionId in (select y.id from GenericFunction y where y.name like :functionName ) ");
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleName", roleName);
		conditionMap.put("functionName", functionName);
		return this.findEntityObjects(buffer.toString(), conditionMap, pageable);
	}

	@Override
	public void deleteRoleFunction(RoleFunction roleFunction) {
		this.delete(roleFunction);
	}

	@Override
	public void saveRoleFunction(RoleFunction roleFunction) {
		this.save(roleFunction);
	}

	@Override
	public List findRoleFunctionByRoleIdAndFunction(Long roleId, Long functionId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from RoleFunction t where t.roleId = :roleId and t.functionId = :functionId");
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleId", roleId);
		conditionMap.put("functionId", functionId);
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}

}
