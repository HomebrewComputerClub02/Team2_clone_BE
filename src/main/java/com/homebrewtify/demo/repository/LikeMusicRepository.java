package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.LikeMusic;
import com.homebrewtify.demo.entity.Music;
import com.homebrewtify.demo.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import java.util.List;
import java.util.Optional;

public interface LikeMusicRepository extends JpaRepository<LikeMusic,String> {

    void deleteByUserAndMusic(User user, Music music);

    @EntityGraph(attributePaths = "music")
    List<LikeMusic> findWithMusicByUser(User user);

    Optional<LikeMusic> findByMusicAndUser(Music music, User user);
}
