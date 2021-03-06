package com.love.family.controller.roleManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.love.family.business.functionManage.entity.FunctionModel;
import com.love.family.business.functionManage.service.FunctionService;
import com.love.family.business.roleManage.entity.RoleInfo;
import com.love.family.business.roleManage.service.RoleService;
import com.love.family.common.UserInfoUtils;
import com.love.family.security.context.SecurityContext;
import com.love.family.security.model.RolePrivilege;
import com.love.family.security.model.User;
import com.love.family.security.model.UserInfo;
import com.love.family.utils.MyBusinessException;

@Controller
public class RoleSelectController {
	Logger logger = LoggerFactory.getLogger(RoleSelectController.class);

	@Autowired
	public RoleService roleService;
	
	@Autowired
	public FunctionService functionService;

	@RequestMapping(value = "getRoles", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> selectRole(HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo =(UserInfo)UserInfoUtils.getCurrentUserInfo();
		if (userInfo == null) {
			return null;
		} else {
			@SuppressWarnings("rawtypes")
			List roles = roleService.findRolesByUserId(userInfo.getId());
			HashMap<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("roleList", roles);
			return resultMap;
		}

	}
	
	@RequestMapping(value = "getUserinfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getUserinfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String,String> msgMap = new HashMap<String, String>();
		UserInfo userInfo =(UserInfo)UserInfoUtils.getCurrentUserInfo();
		if(userInfo!=null) {
			User selfUser =(User) userInfo.getSimpleUser();
			RoleInfo role = selfUser.getCurrentSelectRole();
			msgMap.put("rolename", role.getName());
			msgMap.put("username", selfUser.getUserName());
			msgMap.put("usercode", selfUser.getLoginName());
		}
		return msgMap;
	}
	
	@RequestMapping(value = "selectOneRole")
	public ModelAndView selectOneRole(HttpServletRequest request, HttpServletResponse response) {
		try {
			UserInfo userInfo = (UserInfo) UserInfoUtils.getCurrentUserInfo();
			User selfUser = (User) userInfo.getSimpleUser();
			String method = request.getMethod();
			String selectedRoleId = null;
			if("get".equalsIgnoreCase(method)) {
				selectedRoleId = selfUser.getCurrentSelectRole().getId().toString();
			}else {
				selectedRoleId = request.getParameter("selectedRoleId");
			}
			Long roleId = StringUtils.isBlank(selectedRoleId) ? 0 : Long.valueOf(selectedRoleId);
			RoleInfo role = roleService.findRoleById(roleId);
			selfUser.setCurrentSelectRole(role);
			initUser(userInfo, Long.valueOf(selectedRoleId));
		} catch (MyBusinessException e) {
			ModelAndView indexView = new ModelAndView("/error/roleFunctionEmpty");
			return indexView;
		} catch (Exception e) {
			ModelAndView indexView = new ModelAndView("/error/loginerror");
			return indexView;
		}
		ModelAndView indexView = new ModelAndView("index");
		return indexView;
	}

	private void initUser(UserInfo userInfo, Long selectedRoleId) {
		//第一步查找用户所有角色
		@SuppressWarnings("rawtypes")
		List roles = roleService.findRolesByUserId(userInfo.getId());
		//第二步设置用户权限信息
		setUserPrivilege(userInfo,roles,selectedRoleId);
		//设置所有配置过的Function，用来控制没有配置过的URL是否允许访问
		initSecurityContextFunctions();
	}

	@SuppressWarnings({"unchecked"})
	private void initSecurityContextFunctions() {
		List<FunctionModel> functions = new ArrayList<FunctionModel>();
		Set<String> functionValues = new HashSet<String>();
		functions = functionService.getAllFunctions();
		for(FunctionModel function : functions) {
			if(StringUtils.isNotBlank(function.getUrl()))
				functionValues.add(function.getUrl());
		}
		SecurityContext.setAllFunctions(functionValues);
	}

	private void setUserPrivilege(UserInfo userInfo, List roles, Long selectedRoleId) {
		@SuppressWarnings("rawtypes")
		Iterator it = roles.iterator();
		RoleInfo role;
		while (it.hasNext()) {
			role = (RoleInfo) it.next();
			if(selectedRoleId.longValue()==role.getId().longValue()) {
				try {
					addRolePrivilege(userInfo,role);
				} catch (MyBusinessException e) {
					logger.error("加载[" + userInfo.getLoginName() + ":" + userInfo.getUsername() + "]的角色[id:"
							+ role.getId() + ",code:" + role.getCode() + ",name:" + role.getName() + "]");
					throw e;
				}
			}
		}
		
	}

	private void addRolePrivilege(UserInfo userInfo, RoleInfo role) {
		//第一步，得到角色的权限信息
		RolePrivilege privilege = roleService.getRolePrivilege(role.getId()); 
		
		if(privilege.getResourceList().size()==0) {
			throw new MyBusinessException("该角色权限列表为空！");
		}
		
		//第二步，将此角色对应的功能权限设置到此用户
		userInfo.getResources().clear();
		userInfo.getResources().addAll(privilege.getResourceList());
		
		//第三步，将此角色的对应功能菜单设置到此用户
		userInfo.getMenus().clear();
		userInfo.getMenus().putAll(privilege.getMenuMap());
		
	}
}
