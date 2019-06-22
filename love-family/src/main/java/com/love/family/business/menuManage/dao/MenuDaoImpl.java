package com.love.family.business.menuManage.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class MenuDaoImpl extends BaseHibernate4QueryDao<MenuEO> implements MenuDao {

	@Override
	public MenuEO findMenuByCode(String code) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from MenuEO m where m.code = :code");
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("code", code);
		return this.findOneEntityObject(buffer.toString(), conditionMap);
	}

	@Override
	public Page<MenuEO> queryPageMenu(Pageable pageRequest, MenuEO menuEO) {
		Map<String,Object> conditionMap =  new HashMap<String, Object>();
		conditionMap.put("label", menuEO.getLabel());
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from MenuEO e where 1=1 ");
		buffer.append(" and e.label like :label ");
		buffer.append(" order by e.code asc ");
		return this.findEntityObjects(buffer.toString(), conditionMap, pageRequest);
	}

	@Override
	public MenuEO findMenuById(Long menuId) {
		return this.load(menuId);
	}

	@Override
	public void deleteMenu(MenuEO eo) {
		this.delete(eo);
	}

	@Override
	public void saveMenu(MenuEO eo) {
		this.save(eo);
	}

}
