package com.love.family.business.roleManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<GenericRole> queryPageRole(Pageable pageable, GenericRole role) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleName", role.getName());
		StringBuffer buffer = new StringBuffer();
		buffer.append("from GenericRole t where t.name like :roleName");
		return this.findEntityObjects(buffer.toString(), conditionMap, pageable);
	}

	@Override
	public List<GenericRole> searchRoleDataByCodeUpd(Long id, String roleCode) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleCode", roleCode);
		conditionMap.put("id", id);
		StringBuffer buffer = new StringBuffer();
		buffer.append("from GenericRole t where t.code=:roleCode and t.id !=:id");
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}

	@Override
	public List<GenericRole> searchRoleDataByCode(String roleCode) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleCode", roleCode);
		StringBuffer buffer = new StringBuffer();
		buffer.append("from GenericRole t where t.code=:roleCode");
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}

	@Override
	public void saveRole(GenericRole role) {
		this.save(role);
	}

	@Override
	public void deleteRolerole(GenericRole role) {
		this.delete(role);
	}

	@Override
	public List<GenericRole> findAllRole() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from GenericRole");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}

}
