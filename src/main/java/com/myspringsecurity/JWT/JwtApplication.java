package com.myspringsecurity.JWT;
import javax.annotation.PostConstruct;

import com.myspringsecurity.JWT.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.myspringsecurity.JWT.repository.UserRepository;

@SpringBootApplication
public class JwtApplication {
	
	@Autowired
	private UserRepository userrepository;
	
	@PostConstruct
	public void initUsers()
	{
		
		User user = new User(1,"gaurav","$2y$10$lrmF30jzhXmBm8yPGPbjS.o4ZWWd7ld8400Qh7MEc1SKlzyKkN52e");//123456
		//User user = new User(1,"gaurav","123456");
		userrepository.save(user);
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

}
