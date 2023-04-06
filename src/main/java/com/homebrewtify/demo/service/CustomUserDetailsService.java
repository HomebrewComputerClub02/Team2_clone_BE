package com.homebrewtify.demo.service;


import com.homebrewtify.demo.entity.MemberDetail;
import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService")
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
   private final UserRepository userRepository;

   public CustomUserDetailsService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   @Override
   @Transactional
   public UserDetails loadUserByUsername(final String username) {
      log.info("Loading user");
      User user = userRepository.findFirstByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + " Not Found"));
      return new MemberDetail(user);
   }


}
