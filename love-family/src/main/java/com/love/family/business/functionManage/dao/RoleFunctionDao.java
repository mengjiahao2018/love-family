package com.love.family.business.functionManage.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.love.family.business.roleFunctionManage.entity.RoleFunction;

@Repository
public interface RoleFunctionDao extends JpaRepository<RoleFunction, Long> {

	List<RoleFunction> findByFunctionId(Long id);

}
