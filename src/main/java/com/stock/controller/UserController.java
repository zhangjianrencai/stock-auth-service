package com.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stock.entity.User;
import com.stock.repository.UserRepository;


@Controller
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @PostMapping(path="/regist")
  public @ResponseBody User addNewUser (@RequestParam String name
      , @RequestParam String email) {

    User user = new User();
    user.setEmail(email);
    return userRepository.save(user);
  }
  
  @PutMapping
  public void saveUser(@RequestBody User user) {
	this.userRepository.save(user);
  }
  
}