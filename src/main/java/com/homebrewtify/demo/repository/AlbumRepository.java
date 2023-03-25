package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Album;
import com.homebrewtify.demo.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album,String> {
    Optional<Album> findByAlbumName(String albumName);

    List<Album> findBySinger(Singer singer);
}
