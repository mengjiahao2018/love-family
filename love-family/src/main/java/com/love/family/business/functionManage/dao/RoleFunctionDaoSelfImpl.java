package com.love.family.business.functionManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.love.family.business.functionManage.entity.RoleFunction;
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

}
