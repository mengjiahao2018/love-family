package com.love.family.business.userManage.dao;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.love.family.business.userManage.entity.SysUser;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class UserDaoImpl extends BaseHibernate4QueryDao<SysUser>  implements UserDao {

	@Override
	public Page<SysUser> findPageUserInfoByCondition(Map<String, Object> conditionMap, Pageable pageable) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from SysUser t where t.userName like :userName  and t.loginName like :loginName");
		return this.findEntityObjects(buffer.toString(), conditionMap, pageable);
	}

	@Override
	public SysUser findUserById(Long id) {
		return this.load(id);
	}

	@Override
	public void saveUser(SysUser sysUser) {
		this.save(sysUser);
	}

}
