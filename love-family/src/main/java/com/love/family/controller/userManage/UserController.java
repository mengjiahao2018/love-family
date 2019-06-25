package com.love.family.controller.userManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.love.family.business.functionManage.entity.GenericFunction;
import com.love.family.business.functionManage.service.FunctionService;
import com.love.family.business.menuManage.entity.MenuEO;
import com.love.family.business.menuManage.service.MenuService;
import com.love.family.business.userManage.entity.SysUser;
import com.love.family.business.userManage.entity.SysUserVO;
import com.love.family.business.userManage.service.UserService;
import com.love.family.utils.MessageUtil;
import com.love.family.utils.MyBusinessException;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	FunctionService functionService;
	
	@Autowired
	MenuService menuService;
	
	@Autowired
	UserService userService;
	

	@RequestMapping(value = "/queryAll", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryAll(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		int page = Integer.parseInt((String)pageRequest.get("pageRequest[page]"));
		int size = Integer.parseInt((String)pageRequest.get("pageRequest[size]"));
		Sort sort = new Sort(Sort.Direction.DESC,(String)pageRequest.get("pageRequest[sort]"));
		
		String userName = "%"+(StringUtils.isNotBlank((String)pageRequest.get("userName"))?(String)pageRequest.get("userName"):"")+"%" ;
		String loginName = "%"+(StringUtils.isNotBlank((String)pageRequest.get("loginName"))?(String)pageRequest.get("loginName"):"")+"%" ;
		
		Pageable pageable = PageRequest.of(page-1, size, sort);
		Map<String,Object> conditionMap = new HashMap<String, Object>();
		conditionMap.put("userName", userName);
		conditionMap.put("loginName", loginName);
		Page<SysUser> data = userService.findPageUserInfoByCondition(conditionMap,pageable);
		
		List<SysUserVO> sysUserVOs = new ArrayList<SysUserVO>();
		
		for(SysUser sysUser : data.getContent()) {
			SysUserVO sysUserVO = new SysUserVO();
			BeanUtils.copyProperties(sysUser, sysUserVO);
			if(sysUserVO.getStatus().equals("1")) {
				sysUserVO.setStatus("有效");
			}
			if(sysUserVO.getStatus().equals("0")) {
				sysUserVO.setStatus("无效");
			}
			if(StringUtils.isNotBlank(sysUserVO.getPassword())){
				sysUserVO.setPassword("******");
			}
			sysUserVOs.add(sysUserVO);
		}
		resultMap.put("totalCount", data.getTotalElements());
		resultMap.put("resultList", sysUserVOs);
		return resultMap;
	}
	
	
	
	@RequestMapping(value = "/createUserData", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> createUserData(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		try {
			String userName = (String)pageRequest.get("userName_create");
			String loginName = (String)pageRequest.get("loginName_create");
			String password = (String)pageRequest.get("password_create");
			String status = (String)pageRequest.get("status_create");
			
			SysUser sysUser = new SysUser();
			
			if(!StringUtils.isBlank(userName)) {
				sysUser.setUserName(userName);
			}
			if(!StringUtils.isBlank(loginName)) {
				sysUser.setLoginName(loginName);
			}
			if(!StringUtils.isBlank(status)) {
				sysUser.setStatus(status);
			}
			
			if(!StringUtils.isBlank(password)&&!"******".equals(password)) {
				sysUser.setLastModifyPassword(password);
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				sysUser.setPassword(passwordEncoder.encode(password));
			}
			
			userService.saveUser(sysUser);
			
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAll(model,request,pageRequest);
	}
	
	@RequestMapping(value = "/updateUserDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> updateUserDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		try {
			
			Long id = Long.parseLong((String)pageRequest.get("id_update"));
			String userName = (String)pageRequest.get("userName_update");
			String loginName = (String)pageRequest.get("loginName_update");
			String password = (String)pageRequest.get("password_update");
			String status = (String)pageRequest.get("status_update");
			
			SysUser sysUser = new SysUser();
			SysUser oldSysUser = userService.findUserById(id);
			BeanUtils.copyProperties(oldSysUser, sysUser);
			sysUser.setId(id);
			if(!StringUtils.isBlank(userName)) {
				sysUser.setUserName(userName);
			}
			if(!StringUtils.isBlank(loginName)) {
				sysUser.setLoginName(loginName);
			}
			if(!StringUtils.isBlank(status)) {
				sysUser.setStatus(status);
			}
			if(!StringUtils.isBlank(password)&&!"******".equals(password)) {
				sysUser.setLastModifyPassword(password);
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				sysUser.setPassword(passwordEncoder.encode(password));
			}
			
			userService.saveUser(sysUser);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAll(model,request,pageRequest);
	}
	
	@RequestMapping(value = "/deleteUserDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> deleteUserDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		String id = (String)pageRequest.get("id");
		
		try {
			SysUser sysUser = new SysUser() ;
			if(StringUtils.isNotBlank(id)) {
				sysUser.setId(Long.valueOf(id));
				sysUser.setStatus("0");
				userService.saveUser(sysUser);
			}else {
				throw new MyBusinessException("用户ID信息不存在！");
			}
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAll(model,request,pageRequest);
	}
	
	@RequestMapping(value = "/searchUserDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public SysUser searchUserDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Long id = Long.parseLong((String)pageRequest.get("id"));
		SysUser sysUser = userService.findUserById(id);
		if(StringUtils.isNotBlank(sysUser.getPassword())){
			sysUser.setPassword("******");
		}
		return sysUser;
		
	}
	
	@RequestMapping(value = "/queryAllFatherMenu", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryAllFatherMenu(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		try {
			String param = request.getParameter("label");
			param = StringUtils.isBlank(param)?"%%":"%"+param+"%";
			List<Object[]> list = menuService.findMenuByLabel(param);
			List<Map<String,Object>> menuList = new ArrayList<Map<String,Object>>();
			Map<String,Object> map = null;
			for(Iterator<Object[]> iter = list.iterator();iter.hasNext();){
				Object [] data = iter.next();
				map = new HashMap<String, Object>();
				map.put("id", data[0]);
				map.put("label", data[1]);
				map.put("fid", data[2]);
				map.put("hasSub", data[3]);
				menuList.add(map);
			}
			resultMap.put("menuList",menuList);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "/queryAllMenuFunction", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryAllMenuFunction(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		try {
			String param = request.getParameter("label");
			param = StringUtils.isBlank(param)?"%%":"%"+param+"%";
			List<GenericFunction> functions = functionService.findFunctionByLikeName(param);
			List<Map<String,Object>> eos = new ArrayList<Map<String,Object>>();
			for(GenericFunction function : functions){
				Map<String,Object> eo = new HashMap<String, Object>();
				eo.put("code", function.getId());
				eo.put("id", function.getCode());
				eo.put("name", function.getName());
				eo.put("status", function.getStatus());
				eo.put("type", function.getType());
				eo.put("url", function.getUrl());
				eos.add(eo);
			}
			resultMap.put("eos",eos);
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryAllUserHelpUserRole", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List queryAllUserHelpUserRole(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		return userService.findAllUsers();
	}
	
}
