package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Singer;
import com.homebrewtify.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {



}
