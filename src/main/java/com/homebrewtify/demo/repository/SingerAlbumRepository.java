package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.SingerAlbum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SingerAlbumRepository extends JpaRepository<SingerAlbum,Long> {
}
