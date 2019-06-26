package com.love.family.controller.roleFunctionManage;

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
import com.love.family.common.UserInfoUtils;
import com.love.family.security.context.SecurityContext;
import com.love.family.security.model.User;
import com.love.family.security.model.UserInfo;
import com.love.family.utils.MessageUtil;

@Controller
@RequestMapping("/roleFunction")
public class RoleFunctionController {
	Logger logger = LoggerFactory.getLogger(RoleFunctionController.class);

	@Autowired
	public RoleFunctionService roleFunctionService;
	
	@Autowired
	FunctionService functionService;
	
	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/queryAllRoleFunction", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> queryAllRoleFunction(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Map<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		int page = Integer.parseInt((String)pageRequest.get("pageRequest[page]"));
		int size = Integer.parseInt((String)pageRequest.get("pageRequest[size]"));
		Sort sort = new Sort(Sort.Direction.DESC,(String)pageRequest.get("pageRequest[sort]"));
		
		String roleName = "%"+(StringUtils.isNotBlank((String)pageRequest.get("roleName"))?(String)pageRequest.get("roleName"):"")+"%" ;
		String functionName = "%"+(StringUtils.isNotBlank((String)pageRequest.get("functionName"))?(String)pageRequest.get("functionName"):"")+"%" ;
		Pageable pageable = PageRequest.of(page-1, size, sort);
		
		try {
			Page<RoleFunction> data = roleFunctionService.queryRoleFunctionByRoleNameAndFunctionName(pageable,roleName,functionName);			
			if(data!=null) {
				List<RoleFunction> roleFunctions = data.getContent();
				List<RoleFunctionVO> roleFunctionVOs = new ArrayList<RoleFunctionVO>();
				for(RoleFunction roleFunction : roleFunctions) {
					RoleFunctionVO roleFunctionVO = new RoleFunctionVO();
					roleFunctionVO.setId(roleFunction.getId());
					roleFunctionVO.setFunctionId(roleFunction.getFunctionId());
					roleFunctionVO.setRoleId(roleFunction.getRoleId());
					
					if(roleFunction.getFunctionId()!=null) {
						FunctionModel functionDefault = functionService.findFunctionByFunctionId(roleFunction.getFunctionId());
						if(functionDefault!=null) {
							roleFunctionVO.setFunctionName(functionDefault.getName());
							roleFunctionVO.setFunctionCode(functionDefault.getCode());
							roleFunctionVO.setFunctionType(functionDefault.getType());
							roleFunctionVO.setFunctionUrl(functionDefault.getUrl());
						}
					}
					
					if(roleFunction.getRoleId()!=null) {
						RoleInfo RoleInfo = roleService.findRoleById(roleFunction.getRoleId());
						if(RoleInfo!=null) {
							roleFunctionVO.setRoleName(RoleInfo.getName());
							roleFunctionVO.setRoleCode(RoleInfo.getCode());
						}
					}
					
					roleFunctionVOs.add(roleFunctionVO);
				}
				resultMap.put("totalCount", data.getTotalElements());
				resultMap.put("resultList", roleFunctionVOs);
			}
			
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return resultMap;
	}
	
	
	@RequestMapping(value = "/createRoleFunctionData", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> createRoleData(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		try {
			Long roleId = Long.valueOf((String)pageRequest.get("roleId_create"));
			String functionIds  = (String)pageRequest.get("functionId_create");
			
			if(StringUtils.isNotBlank(functionIds)) {
				for(String id : functionIds.split(",")) {
					RoleFunction roleFunction = new RoleFunction();
					roleFunction.setRoleId(roleId);
					roleFunction.setFunctionId(Long.valueOf(id));
					roleFunctionService.saveRoleFunction(roleFunction);
				}
			}else {
				List<FunctionModel> functions = functionService.findAllFunction();
				for(FunctionModel function : functions) {
					RoleFunction rf = new RoleFunction();
					rf.setRoleId(roleId);
					rf.setFunctionId(function.getId());
					roleFunctionService.saveRoleFunction(rf);
				}
			}
			
			UserInfo userInfo = UserInfoUtils.getCurrentUserInfo();
			RoleInfo role = null;
			if(userInfo!=null) {
				role = ((User) userInfo.getSimpleUser()).getCurrentSelectRole();
			}
			
			if(role!=null&&role.getId().equals(roleId)) {
				Map<String,String> menus = null;
				List<String> resources = null;
				if(userInfo!=null) {
					menus = userInfo.getMenus();
					resources = userInfo.getResources();
				}
				
				for(String id : functionIds.split(",")) {
					FunctionModel function = functionService.findFunctionByFunctionId(Long.valueOf(id));
					if(menus!=null&&function!=null) {
						menus.remove(function.getCode());
					}
					if(resources!=null&&function!=null) {
						resources.remove(function.getUrl());
					}
					if(SecurityContext.getAllFunctions()!=null&&function!=null) {
						SecurityContext.getAllFunctions().remove(function.getUrl());
					}
				}
				
			}
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAllRoleFunction(model,request,pageRequest);
	}
	
	
	@RequestMapping(value = "/deleteRoleFunctionDataById", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> deleteRoleFunctionDataById(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_SUCCESS_CODE);
		resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, MessageUtil.RETURN_SUCCESS_MESSAGE);
		
		try {
			Long id = Long.valueOf((String)pageRequest.get("id"));
			String roleIdStr = (String)pageRequest.get("roleId");
			String functionIdStr = (String)pageRequest.get("functionId");
			Long roleId = Long.valueOf((roleIdStr==null||"".equals(roleIdStr)?"0":roleIdStr));
			Long functionId = Long.valueOf((functionIdStr==null||"".equals(functionIdStr)?"0":functionIdStr));
			
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setId(id);
			roleFunctionService.deleteRoleFunction(roleFunction);
			
			UserInfo userInfo = UserInfoUtils.getCurrentUserInfo();
			User selfUser =(User) userInfo.getSimpleUser();
			RoleInfo role = null;
			if(userInfo!=null) {
				role = selfUser.getCurrentSelectRole();
			}
			
			if(role!=null&&role.getId().equals(roleId)) {
				Map<String,String> menus = null;
				List<String> resources = null;
				if(userInfo!=null) {
					menus = userInfo.getMenus();
					resources = userInfo.getResources();
				}
				
				FunctionModel function = functionService.findFunctionByFunctionId(functionId);
				if(menus!=null&&function!=null) {
					menus.remove(function.getCode());
				}
				if(resources!=null&&function!=null) {
					resources.remove(function.getUrl());
				}
				if(SecurityContext.getAllFunctions()!=null&&function!=null) {
					SecurityContext.getAllFunctions().remove(function.getUrl());
				}
			}
		} catch (Exception e) {
			resultMap.put(MessageUtil.RETURN_RESULT_SIGN, MessageUtil.RETURN_FAILED_CODE);
			resultMap.put(MessageUtil.RETURN_MESSAGE_SIGN, e.getMessage());
			return resultMap;
		}
		
		return queryAllRoleFunction(model,request,pageRequest);
	}
	
	
	
	@RequestMapping(value = "/searchRoleFunctionDataByRoleIdAndFunctionId", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Map<String,Object> searchRoleFunctionDataByRoleIdAndFunctionId(Model model, HttpServletRequest request, @RequestParam Map<String, Object> pageRequest) {
		Long roleId =Long.valueOf((String)pageRequest.get("roleId"));
		Long functionId =Long.valueOf((String)pageRequest.get("functionId"));
		List data = roleFunctionService.findRoleFunctionByRoleIdAndFunction(roleId,functionId);
		HashMap<String,Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultList", data);
		resultMap.put("totalCount", data.size());
		return resultMap;
	}
}
