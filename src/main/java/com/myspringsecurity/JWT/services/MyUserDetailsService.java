package com.myspringsecurity.JWT.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myspringsecurity.JWT.repository.UserRepository;


@Service
public class MyUserDetailsService implements UserDetailsService {
	
	private User user;

	@Autowired
	private UserRepository userrepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		/*
		 * Here we can call DB or any thing else to fetch userdetails
		 * We can use username(input parameters) to fetch userdetails
		 * When we call rest controller spring security will compare the 
		 * Password with whatever is present here
		 * 
		 * */
		System.out.println("userdetails service");
		System.out.println(username);
		com.myspringsecurity.JWT.entity.User usertemp=userrepository.findByusername(username);
		System.out.println(usertemp.getUsername()+"--------------"+usertemp.getPassword());
		user =new User(usertemp.getUsername(), usertemp.getPassword(), new ArrayList<>());
		 
		return user;
	}

	public User getUser() {
		return user;
	}
	
	
	
	

}
