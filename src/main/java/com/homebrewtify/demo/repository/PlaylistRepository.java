package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Playlist;
import com.homebrewtify.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository <Playlist, String> {

    List<Playlist> findByUser_UserId(Long userId);

    //user_id와 플레이리스트 이름으로 조회
    List<Playlist> findByUser_UserIdAndName(Long userId, String name);

    List<Playlist> findByUser(User user);
}
