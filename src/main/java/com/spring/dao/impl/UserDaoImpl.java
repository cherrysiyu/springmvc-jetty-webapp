package com.spring.dao.impl;

import org.springframework.stereotype.Repository;

import com.spring.dao.UserDao;

@Repository
public class UserDaoImpl implements UserDao {

	public String findUser(int userId) {
		return "张三";
	}

}
