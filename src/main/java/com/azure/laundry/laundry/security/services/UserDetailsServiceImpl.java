package com.azure.laundry.laundry.security.services;


import com.azure.laundry.laundry.models.User;
import com.azure.laundry.laundry.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    return UserDetailsImpl.build(user);
  }

  public UserDetails loadUserByEmail(String email) throws Exception {

    User user = userRepository.findByEmail(email)
    .orElseThrow(()-> new Exception("User Not Found By Email"));
    return UserDetailsImpl.build(user);
    
  }

}
