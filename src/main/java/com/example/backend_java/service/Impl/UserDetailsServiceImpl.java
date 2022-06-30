package com.example.backend_java.service.Impl;

import com.example.backend_java.domain.entity.UserEntity;
import com.example.backend_java.repository.UserRepository;
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
    UserEntity userEntity = userRepository.findByTaiKhoan(username);
    if (userEntity == null) {
      throw new UsernameNotFoundException("User not found");
    }
    return UserDetailsImpl.build(userEntity);
  }

}
