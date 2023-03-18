package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.MusicSinger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MusicSingerRepository extends JpaRepository<MusicSinger,String> {
}
