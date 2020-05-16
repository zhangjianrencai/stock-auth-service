package com.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.entity.User;
import com.stock.entity.UserType;
import com.stock.repository.UserRepository;
import com.stock.vo.UserViewModel;


@Controller
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @PostMapping(path="/regist")
  public @ResponseBody User addNewUser (@RequestBody UserViewModel userVO) {
	  User user = new User();
	  BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	  String password = passwordEncoder.encode(userVO.getPassword());
	  user.setPassword(password);
	  char type = userVO.getUserType();
	  if ('1' == type) {
		  user.setUserType(UserType.USER);
	  } else {
		  user.setUserType(UserType.ADMIN);
	  }
	  user.setEmail(userVO.getEmail());
	  user.setMobile(userVO.getMobile());
	  user.setUsername(userVO.getUsername());
	  
	  return userRepository.save(user);
  }
  
  @PutMapping
  public void saveUser(@RequestBody User user) {
	  this.userRepository.save(user);
  }
  
}