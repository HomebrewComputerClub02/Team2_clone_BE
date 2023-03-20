package com.homebrewtify.demo.service;


import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.repository.UserDao;
import com.homebrewtify.demo.repository.UserRepository;
import com.homebrewtify.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProvider userProvider;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, UserProvider userProvider, JwtService jwtService) {
        this.userRepository = userRepository;
        this.userProvider = userProvider;
        this.jwtService = jwtService;
    }


}
