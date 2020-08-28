package com.myspringsecurity.JWT.congiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import com.myspringsecurity.JWT.filter.MyCustomFilter;
import com.myspringsecurity.JWT.services.MyUserDetailsService;
import com.myspringsecurity.JWT.filter.MyCustomFilter;
@EnableWebSecurity(debug = true)
@Configuration
public class MysecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	private MyCustomFilter myCustomFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("configure method adapter auth");
		auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		//super.configure(auth);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("configure method adapter http security");
		http.csrf().disable().authorizeRequests()
			.antMatchers("/authenticate/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(myCustomFilter, UsernamePasswordAuthenticationFilter.class);
			
		//super.configure(http);
	}

//	@Bean
//	public PasswordEncoder passwordEncoder()
//	{
//		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//	}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
	    return super.authenticationManagerBean();
	}

}
