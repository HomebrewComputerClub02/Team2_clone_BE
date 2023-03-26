package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.MusicSinger;
import com.homebrewtify.demo.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicSingerRepository extends JpaRepository<MusicSinger,String> {
    List<MusicSinger> findWithMusicBySinger(Singer singer);
}
