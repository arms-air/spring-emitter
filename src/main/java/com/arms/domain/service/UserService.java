package com.arms.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.arms.domain.entity.User;
import com.arms.domain.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;

	public List<User> findAllUser(){
		return userRepository.findAllByOrderByCreatedDateDesc();
	}

}
