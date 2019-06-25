package com.love.family.business.userManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.love.family.business.userManage.entity.UserModel;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class UserDaoImpl extends BaseHibernate4QueryDao<UserModel>  implements UserDao {

	@Override
	public Page<UserModel> findPageUserInfoByCondition(Map<String, Object> conditionMap, Pageable pageable) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserModel t where t.userName like :userName  and t.loginName like :loginName");
		return this.findEntityObjects(buffer.toString(), conditionMap, pageable);
	}

	@Override
	public UserModel findUserById(Long id) {
		return this.load(id);
	}

	@Override
	public void saveUser(UserModel sysUser) {
		this.save(sysUser);
	}

	@Override
	public List findAllUsers() {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" from UserModel ");
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		return this.findEntityObjects(buffer.toString(), conditionMap);
	}
}
