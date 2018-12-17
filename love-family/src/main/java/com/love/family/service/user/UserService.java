package com.love.family.service.user;

import java.util.List;

import com.love.family.entity.user.User;

public interface UserService {
	User findById(Integer id);
    User save(String name);
    List<User> findAll();
}
