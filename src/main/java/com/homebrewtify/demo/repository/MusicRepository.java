package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicRepository extends JpaRepository<Music,String> {
}
