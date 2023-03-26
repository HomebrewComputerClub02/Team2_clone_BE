package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Singer;
import com.homebrewtify.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    Optional<User> findUserByEmail(String email); // 이메일로 유저찾기
    boolean existsByEmail(String email); // 이메일 중복 확인

    boolean existsByEmailAndPassword(String email,String password); // 이메일과 패스워드로 일치하는 유저 찾기



}
