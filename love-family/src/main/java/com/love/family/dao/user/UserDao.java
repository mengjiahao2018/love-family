package com.love.family.dao.user;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.love.family.entity.user.User;

public interface UserDao extends JpaRepository<User, Serializable>{
	
	User findById(Integer id);
}
