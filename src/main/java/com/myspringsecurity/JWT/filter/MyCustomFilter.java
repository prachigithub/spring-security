package com.myspringsecurity.JWT.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.myspringsecurity.JWT.services.MyUserDetailsService;
import com.myspringsecurity.JWT.util.JwtUtil;

@Component
public class MyCustomFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authHeaderValue=request.getHeader("Authorization");
		
		System.out.println("My filter");
		
		if(authHeaderValue!=null && authHeaderValue.startsWith("Bearer "))
		{
			String token=authHeaderValue.substring(7);
			String userName=jwtUtil.extractUsername(token);
			
			if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
				UserDetails userdetails=myUserDetailsService.loadUserByUsername(userName);
				if(jwtUtil.validateToken(token, userdetails))
				{
					UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken 
															= new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
					usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));
					SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}
