package com.love.family.business.loginManage.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.stereotype.Repository;

import com.love.family.business.loginManage.entity.LoginUser;
import com.love.family.dao.base.BaseHibernate4QueryDao;

@Repository
public class LoginDaoImpl extends BaseHibernate4QueryDao<LoginUser> implements
		LoginDao {

	@Override
	public boolean login(String loginName, String password){
		Map<String, Object> params = new HashedMap();
		params.put("loginName", loginName);
		params.put("password", password);
		StringBuffer buffer = new StringBuffer();
		buffer.append("from LoginUser where loginName= :loginName and password= :password");
		try {
			List<LoginUser> loginUserList = super.findEntityObjects(buffer.toString(), params);
			if(loginUserList!=null&&loginUserList.size()>0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addLoginUser(String loginName, String password) {
		LoginUser loginUser = new LoginUser();
		loginUser.setLoginName(loginName);
		loginUser.setPassword(password);
		super.insert(loginUser);
		return true;
	}

	@Override
	public LoginUser findLoginUserByLoginName(String loginName) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("from LoginUser where loginName= :loginName");
		Map<String, Object> params = new HashedMap();
		params.put("loginName", loginName);
		try {
			List<LoginUser> loginUserList = super.findEntityObjects(buffer.toString(), params);
			if(loginUserList!=null&&loginUserList.size()>0){
				return loginUserList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
