package com.love.family.business.loginManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.love.family.business.loginManage.entity.SysUser;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class LoginDaoImpl extends BaseHibernate4QueryDao<SysUser> implements LoginDao {

	@Override
	public boolean login(String loginName, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		params.put("password", password);
		StringBuffer buffer = new StringBuffer();
		buffer.append("from LoginUser where loginName= :loginName and password= :password");
		try {
			List<SysUser> loginUserList = super.findEntityObjects(buffer.toString(), params);
			if (loginUserList != null && loginUserList.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addLoginUser(String username, String password) {
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		sysUser.setPassword(password);
		super.insert(sysUser);
		return true;
	}

	@Override
	public List<SysUser> findLoginUserByLoginName(String username) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from SysUser where username= :username");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", username);
		try {
			List<SysUser> sysUserList = super.findEntityObjects(buffer.toString(), params);
			return sysUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
