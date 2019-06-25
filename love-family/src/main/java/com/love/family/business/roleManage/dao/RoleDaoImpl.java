package com.love.family.business.roleManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.love.family.business.roleManage.entity.RoleInfo;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class RoleDaoImpl extends BaseHibernate4QueryDao<RoleInfo>  implements RoleDao {
	
	@Override
	public List findRolesByUserId(Long id) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from RoleInfo r where r.id in (select t.roleId from SysUserRole t where t.userId=:userId) ");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("userId", id);
		return findEntityObjects(buffer.toString(), conditionMap);
	}

	@Override
	public RoleInfo findRoleById(Long roleId) {
		StringBuffer buffer = new StringBuffer("");
		buffer.append("from RoleInfo where id = :id");
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("id", roleId);
		RoleInfo role = this.findOneEntityObject(buffer.toString(), conditionMap);
		return role;
	}

	@Override
	public Page<RoleInfo> queryPageRole(Pageable pageable, RoleInfo role) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleName", role.getName());
		StringBuffer buffer = new StringBuffer();
		buffer.append("from RoleInfo t where t.name like :roleName");
		return this.findEntityObjects(buffer.toString(), conditionMap, pageable);
	}

	@Override
	public List<RoleInfo> searchRoleDataByCodeUpd(Long id, String roleCode) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleCode", roleCode);
		conditionMap.put("id", id);
		StringBuffer buffer = new StringBuffer();
		buffer.append("from RoleInfo t where t.code=:roleCode and t.id !=:id");
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}

	@Override
	public List<RoleInfo> searchRoleDataByCode(String roleCode) {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("roleCode", roleCode);
		StringBuffer buffer = new StringBuffer();
		buffer.append("from RoleInfo t where t.code=:roleCode");
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}

	@Override
	public void saveRole(RoleInfo role) {
		this.save(role);
	}

	@Override
	public void deleteRolerole(RoleInfo role) {
		this.delete(role);
	}

	@Override
	public List<RoleInfo> findAllRole() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from RoleInfo");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}

}
