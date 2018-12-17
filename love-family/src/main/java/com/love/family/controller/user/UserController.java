package com.love.family.controller.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.love.family.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping("/findall")
	@ResponseBody
	public Map<String, Object> getUser() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("data", userService.findAll());
		return map;
	}

	@RequestMapping("/findbyid")
	@ResponseBody
	public Map<String, Object> findById(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("data", userService.findById(id));
		return map;
	}

	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> save(String name) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("state", "success");
		map.put("data", userService.save(name));
		return map;
	}
}
