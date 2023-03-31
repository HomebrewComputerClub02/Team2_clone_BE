package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.dto.musicinplaylist.AlbumInMusicInPlaylist;
import com.homebrewtify.demo.dto.popularity.AlbumInterface;
import com.homebrewtify.demo.entity.Album;
import com.homebrewtify.demo.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album,String> {
    Optional<Album> findByAlbumName(String albumName);

    List<Album> findBySinger(Singer singer);

    @Query(value = "SELECT album.album_id as id, album_name as name FROM album INNER JOIN (SELECT album_id, MAX(music_feature.popularity) as pop FROM music INNER JOIN music_feature ON music.feature_id = music_feature.feature_id GROUP BY album_id ORDER BY pop DESC LIMIT 10) as T ON T.album_id = album.album_id", nativeQuery = true)
    List<AlbumInterface> findTop10ByPopularity();
}
