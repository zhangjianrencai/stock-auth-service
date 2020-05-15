package com.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stock.entity.User;
import com.stock.repository.UserRepository;
import com.stock.security.SecurityUtils;

import java.util.Optional;

@Service
@Transactional
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Transactional(readOnly = true)
   public Optional<User> getUserWithAuthorities() {
      return SecurityUtils.getCurrentUsername().flatMap(userRepository::findOneWithAuthoritiesByEmailIgnoreCase);
   }

}
