package com.love.family.business.userManage.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.love.family.business.userManage.entity.UserInfo;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class UserManageDaoImpl extends BaseHibernate4QueryDao<UserInfo> implements UserManageDao {

	@Override
	public List<UserInfo> findUserInfoList(Map<String, Object> param) throws Exception {
		StringBuffer hql = new StringBuffer();
		hql.append(" from UserInfo where 1=1 ");
		if(!StringUtils.isEmpty((String)param.get("name"))) {
			hql.append(" and name = :name");
		}
		if(!StringUtils.isEmpty((String)param.get("sex"))) {
			hql.append(" and sex = :sex");
		}
		List<UserInfo> UserInfoList = super.findEntityObjects(hql.toString(), param);
		return UserInfoList;
	}

}
