package com.love.family.business.menuManage.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.menuManage.entity.MenuVO;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class MenuRepoImpl extends BaseHibernate4QueryDao<MenuEO>  implements MenuRepo {

	@Override
	public List<MenuVO> findAllBottomMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select e from MenuEO e where e.parentId is not null ");
		buffer.append(" and e.id not in (select t.parentId from MenuEO t where");
		buffer.append(" t.parentId is not null) order by code");
	    Map<String,Object> conditionMap = new HashMap<String, Object>();		
		List<MenuEO> menuEOlist = this.findEntityObjects(buffer.toString(), conditionMap);
		List<MenuVO> menuVOList = new ArrayList<MenuVO>();
		for(MenuEO menuEO:menuEOlist) {
			MenuVO menuVO = new MenuVO();
			BeanUtils.copyProperties(menuEO,menuVO);
			menuVOList.add(menuVO);   
		}
		return menuVOList;
	}

	@Override
	public List<MenuVO> findAllParentMenu() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" select e from MenuEO e where e.id in ");
		buffer.append(" (select t.parentId from MenuEO t where");
		buffer.append(" t.parentId is not null) order by code");
	    Map<String,Object> conditionMap = new HashMap<String, Object>();		
		List<MenuEO> menuEOlist = this.findEntityObjects(buffer.toString(), conditionMap);
		List<MenuVO> menuVOList = new ArrayList<MenuVO>();
		for(MenuEO menuEO:menuEOlist) {
			MenuVO menuVO = new MenuVO();
			BeanUtils.copyProperties(menuEO,menuVO);
			menuVOList.add(menuVO);   
		}
		return menuVOList;
	}

	@Override
	public List<Object[]> findMenuByLabel(String label) {
		return null;
	}

}
