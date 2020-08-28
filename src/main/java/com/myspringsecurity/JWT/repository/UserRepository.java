package com.myspringsecurity.JWT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myspringsecurity.JWT.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByusername(String username);

}
