package com.love.family.controller.userRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.love.family.business.functionManage.entity.FunctionModel;
import com.love.family.business.functionManage.service.FunctionService;
import com.love.family.business.roleFunctionManage.entity.RoleFunction;
import com.love.family.business.roleFunctionManage.entity.RoleFunctionVO;
import com.love.family.business.roleFunctionManage.service.RoleFunctionService;
import com.love.family.business.roleManage.entity.RoleInfo;
import com.love.family.business.roleManage.service.RoleService;
import com.love.family.business.userManage.entity.UserModel;
import com.love.family.business.userManage.service.UserService;
import com.love.family.business.userRoleManage.entity.UserRole;
import com.love.family.business.userRoleManage.entity.UserRoleVO;
import com.love.family.business.userRoleManage.service.UserRoleService;
import com.love.family.common.UserInfoUtils;
import com.love.family.security.context.SecurityContext;
import com.love.family.security.model.User;
import com.love.family.security.model.UserInfo;
import com.love.family.utils.MessageUtil;

@Controller
@RequestMapping("/userRole")
public class UserRoleController {
	Logger logger = LoggerFactory.getLogger(UserRoleController.class);

	@Autowired
	public RoleFunctionService roleFunctionService;
	
	@Autowired
	FunctionService functionService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserRoleService userRoleService;
	
	@Autowired
	UserService userService;
	

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryAllUserRole(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		int page = Integer.parseInt((String)pageRequest.get("pageRequest[page]"));
		int size = Integer.parseInt((String)pageRequest.get("pageRequest[size]"));
		Sort sort = new Sort(Sort.Direction.DESC,(String)pageRequest.get("pageRequest[sort]"));
		String roleName = "%"+(StringUtils.isNotBlank((String)pageRequest.get("roleName"))?(String)pageRequest.get("roleName"):"")+"%" ;
		String userName = "%"+(StringUtils.isNotBlank((String)pageRequest.get("userName"))?(String)pageRequest.get("userName"):"")+"%" ;
		Pageable pageable = PageRequest.of(page-1, size, sort);
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		try {
			conditionMap.put("roleName", roleName);
			conditionMap.put("userName", userName);
			Page<UserRole> data = userRoleService.queryPageUserRole(pageable,conditionMap);			
			if(data!=null) {
				List<UserRole> sysUserRoles = data.getContent();
				List<UserRoleVO> sysUserRoleVOs = new ArrayList<UserRoleVO>();
				for(UserRole sysUserRole : sysUserRoles) {
					UserRoleVO sysUserRoleVO = new UserRoleVO();
					sysUserRoleVO.setId(sysUserRole.getId());
					sysUserRoleVO.setRoleId(sysUserRole.getRoleId());
					sysUserRoleVO.setUserId(sysUserRole.getUserId());
					
					UserModel sysUser = userService.findUserById(sysUserRole.getUserId());
					sysUserRoleVO.setUserName(sysUser.getUserName());
					sysUserRoleVO.setLoginName(sysUser.getLoginName());
					
					RoleInfo role = roleService.findRoleById(sysUserRole.getRoleId());
					sysUserRoleVO.setRoleName(role.getName());
					sysUserRoleVO.setRoleCode(role.getCode());
					
					sysUserRoleVOs.add(sysUserRoleVO);
				}
				resultMap.put("totalCount", data.getTotalElements());
				resultMap.put("resultList", sysUserRoleVOs);
			}
			
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "/deleteUserRoleDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> deleteUserRoleDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		try {
			Long id = Long.valueOf((String)pageRequest.get("id"));
			UserRole sysUserRole = new UserRole();
			sysUserRole.setId(id);
			userRoleService.deleteUserRole(sysUserRole);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAllUserRole(model,request,pageRequest);
	}
	
	@RequestMapping(value = "/searchUserRoleDataByUserIdAndRoleId", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> searchUserRoleDataByUserIdAndRoleId(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Long roleId =Long.valueOf((String)pageRequest.get("roleId"));
		Long userId =Long.valueOf((String)pageRequest.get("userId"));
		List data = userRoleService.searchUserRoleDataByUserIdAndRoleId(roleId,userId);
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultList", data);
		resultMap.put("totalCount", data.size());
		return resultMap;
	}
	
	
	@RequestMapping(value = "/createUserRoleData", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> createRoleData(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		try {
			Long roleId = Long.valueOf((String)pageRequest.get("roleId_create"));
			Long userId = Long.valueOf((String)pageRequest.get("userId_create"));
			UserRole sysUserRole = new UserRole();
			sysUserRole.setRoleId(roleId);
			sysUserRole.setUserId(userId);
			userRoleService.saveUserRole(sysUserRole);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		return queryAllUserRole(model,request,pageRequest);
	}

}
