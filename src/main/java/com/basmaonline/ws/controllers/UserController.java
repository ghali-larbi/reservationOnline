package com.basmaonline.ws.controllers;

import java.awt.PageAttributes.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basmaonline.exceptions.UserException;
import com.basmaonline.ws.request.UserRequest;
import com.basmaonline.ws.responses.ErrorMessage;
import com.basmaonline.ws.responses.UserResponse;
import com.basmaonline.ws.services.UserService;
import com.basmaonline.ws.shared.dto.UserDto;

@RestController

@RequestMapping("/users") 
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	@GetMapping(path ="/{id}",produces = org.springframework.http.MediaType.APPLICATION_XML_VALUE) 
	public UserResponse getUser(@PathVariable String id) {
		
	UserDto userDto = userService.getUserByUserId(id);
	
	UserResponse userResponse = new UserResponse();
	
	BeanUtils.copyProperties(userDto, userResponse);
	
	return userResponse;
	}
	
public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) throws Exception {
		if(userRequest.getFirstName().isEmpty() || userRequest.getLastName().isEmpty() || userRequest.getEmail().isEmpty() || userRequest.getPassword().isEmpty()) throw new UserException(ErrorMessage.MISSING_REQUIRED_FIELD.getErrorMessage());
		UserDto userDto = new UserDto();
		BeanUtils.copyProperties(userRequest, userDto);
		UserDto createUser =  userService.createUser(userDto);
		UserResponse userResponse = new UserResponse();
		BeanUtils.copyProperties(createUser, userResponse);
		
		return new ResponseEntity<>(userResponse ,HttpStatus.CREATED);
		
		
	}
	
	@PutMapping(path ="/{id}") 
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {
		
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(userRequest, userDto);
		
		UserDto updateUser =  userService.updateUser(id , userDto);
		
		UserResponse userResponse = new UserResponse();
		
		BeanUtils.copyProperties(updateUser, userResponse);
		
		return userResponse;
	}
	
	@DeleteMapping(path ="/{id}") 
	public void deleteUser(@PathVariable String id) {
		userService.deleteUser(id);
	}
}
