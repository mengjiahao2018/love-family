package com.love.family.controller.roleManage;

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
import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.roleManage.entity.RoleInfo;
import com.love.family.business.roleManage.service.RoleService;
import com.love.family.pub.rbac.system.util.UserInfoUtils;
import com.love.family.security.model.UserInfo;
import com.love.family.utils.MessageUtil;

@Controller
@RequestMapping("/roleManage")
public class RoleController {
	Logger logger = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	public RoleService roleService;

	@RequestMapping(value = "/queryAllRole", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryAllRole(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		int page = Integer.parseInt((String)pageRequest.get("pageRequest[page]"));
		int size = Integer.parseInt((String)pageRequest.get("pageRequest[size]"));
		Sort sort = new Sort(Sort.Direction.DESC,(String)pageRequest.get("pageRequest[sort]"));
		
		String roleName = (String)pageRequest.get("roleName");
		Pageable pageable = PageRequest.of(page-1, size, sort);

		RoleInfo role = new RoleInfo();
		role.setName("%"+(StringUtils.isNotBlank(roleName)?roleName:"")+"%");
		
		try {
			Page<RoleInfo> data = roleService.queryPageRole(pageable,role);			
			resultMap.put("totalCount", data.getTotalElements());
			resultMap.put("resultList", data.getContent());
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}
	
	
	@RequestMapping(value = "/queryAllRoleHelpRoleFunction", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<RoleInfo> queryAllRoleHelpRoleFunction(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		UserInfo userInfo = UserInfoUtils.getCurrentUserInfo();
		if(userInfo!=null) {
			return roleService.findAllRole();
		}else {
			return null;
		}
	}
	
	@RequestMapping(value = "/searchRoleDataByCodeUpd", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> searchRoleDataByCodeUpd(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		String roleCode = (String)pageRequest.get("roleCode");
		Long id = Long.valueOf((String)pageRequest.get("id"));
		try {
			List<RoleInfo> roles = roleService.searchRoleDataByCodeUpd(id,roleCode);			
			resultMap.put("totalCount", roles.size());
			resultMap.put("resultList", roles);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "/searchRoleDataByCode", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> searchRoleDataByCode(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		String roleCode = (String)pageRequest.get("roleCode");
		try {
			List<RoleInfo> roles = roleService.searchRoleDataByCode(roleCode);			
			resultMap.put("totalCount", roles.size());
			resultMap.put("resultList", roles);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "/createRoleData", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> createRoleData(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		String name = (String)pageRequest.get("name_create");
		String code = (String)pageRequest.get("code_create");
		
		RoleInfo role = new RoleInfo();
		if(!StringUtils.isBlank(name)) {
			role.setName(name);
		}
		if(!StringUtils.isBlank(code)) {
			role.setCode(code);
		}
		
		try {
			roleService.saveRole(role);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAllRole(model,request,pageRequest);
	}
	
	
	@RequestMapping(value = "/updateRoleDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> updateRoleDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		Long id = Long.parseLong((String)pageRequest.get("id_update"));
		String name = (String)pageRequest.get("name_update");
		String code = (String)pageRequest.get("code_update");
		
		RoleInfo role = new RoleInfo();
		role.setId(id);
		if(!StringUtils.isBlank(name)) {
			role.setName(name);
		}
		if(!StringUtils.isBlank(code)) {
			role.setCode(code);
		}
		try {
			roleService.saveRole(role);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAllRole(model,request,pageRequest);
	}
	
	@RequestMapping(value = "/searchRoleDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public RoleInfo searchRoleDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Long id = Long.parseLong((String)pageRequest.get("id"));
		RoleInfo role = roleService.findRoleByRoleId(id);
		return role;
		
	}
	
	@RequestMapping(value = "/deleteRoleDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> deleteRoleDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		String id = (String)pageRequest.get("id");
		
		RoleInfo role = new RoleInfo() ;
		role.setId(Long.valueOf(id));
		
		try {
			roleService.deleteRolerole(role);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAllRole(model,request,pageRequest);
	}
}
