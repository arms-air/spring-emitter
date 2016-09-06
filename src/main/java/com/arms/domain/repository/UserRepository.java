package com.arms.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arms.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public List<User> findAllByOrderByCreatedDateDesc();
}
