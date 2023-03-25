package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository <Playlist, String> {

    List<Playlist> findByUser_UserId(Long userId);

    List<Playlist> findByUser_UserIdAndName(Long userId, String name);
}
