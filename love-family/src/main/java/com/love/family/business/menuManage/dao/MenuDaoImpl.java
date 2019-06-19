package com.love.family.business.menuManage.dao;

import java.util.HashMap;
import java.util.Map;

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

}
