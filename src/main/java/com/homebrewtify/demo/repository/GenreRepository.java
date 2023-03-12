package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Genre;
import com.homebrewtify.demo.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,String> {
}
