package com.myspringsecurity.JWT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myspringsecurity.JWT.model.AuthenticationRequest;
import com.myspringsecurity.JWT.model.AuthenticationResponse;
import com.myspringsecurity.JWT.services.MyUserDetailsService;
import com.myspringsecurity.JWT.util.JwtUtil;


@RestController
public class AuthController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@GetMapping(path = "/auth", produces = MediaType.TEXT_HTML_VALUE)
	public ResponseEntity<String> authUser()
	{
		return new ResponseEntity<String>("hello-world",HttpStatus.OK);
	}
	
	@PostMapping(path = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			Authentication x=authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword()));
				System.out.println(x.isAuthenticated()+"----------  "+x.getName()+"--------------"+x.getPrincipal());	
			
		}
		catch (BadCredentialsException e) {
			e.printStackTrace();
			throw new Exception("Incorrect username or password", e);
		}


		//final UserDetails userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUserName());

		//final String jwt = jwtTokenUtil.generateToken(userDetails);
		final String jwt = jwtTokenUtil.generateToken(myUserDetailsService.getUser());
		AuthenticationResponse ar=new AuthenticationResponse();
		ar.setJwtToken(jwt);
		return new ResponseEntity<AuthenticationResponse>(ar,HttpStatus.OK);
	}
	
	
		
		
	

}
