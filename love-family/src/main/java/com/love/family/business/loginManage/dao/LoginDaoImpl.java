package com.love.family.business.loginManage.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.love.family.business.userManage.entity.UserModel;
import com.love.family.system.dao.BaseHibernate4QueryDao;

@Repository
public class LoginDaoImpl extends BaseHibernate4QueryDao<UserModel> implements LoginDao {

	@Override
	public boolean login(String loginName, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		params.put("password", password);
		StringBuffer buffer = new StringBuffer();
		buffer.append("from LoginUser where loginName= :loginName and password= :password");
		try {
			List<UserModel> loginUserList = super.findEntityObjects(buffer.toString(), params);
			if (loginUserList != null && loginUserList.size() > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addLoginUser(String loginName, String password) {
		UserModel sysUser = new UserModel();
		sysUser.setLoginName(loginName);
		sysUser.setPassword(password);
		sysUser.setStatus("0");
		super.insert(sysUser);
		return true;
	}

	@Override
	public List<UserModel> findLoginUserByLoginName(String loginName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from UserModel where loginName= :loginName and status=1");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("loginName", loginName);
		try {
			List<UserModel> sysUserList = super.findEntityObjects(buffer.toString(), params);
			return sysUserList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
