package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.dto.interfacedto.AlbumInterface;
import com.homebrewtify.demo.entity.Album;
import com.homebrewtify.demo.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album,String> {
    Optional<Album> findByAlbumName(String albumName);

    List<Album> findBySinger(Singer singer);

    @Query(value = "SELECT album.album_id as id, album_name as name FROM album INNER JOIN (SELECT album_id, MAX(music_feature.popularity) as pop FROM music INNER JOIN music_feature ON music.feature_id = music_feature.feature_id GROUP BY album_id ORDER BY pop DESC LIMIT 10) as T ON T.album_id = album.album_id", nativeQuery = true)
    List<AlbumInterface> findTop10ByPopularity();

    @Query(value = "SELECT A.album_id as id, A.album_name as name, MAX(popularity) as popularity FROM (( SELECT * FROM album WHERE album_name like :keyword% ) AS A \n" +
            "INNER JOIN music ON music.album_id = A.album_id )\n" +
            "INNER JOIN music_feature ON music.feature_id = music_feature.feature_id GROUP BY A.album_id order by popularity desc limit 1000"
            , nativeQuery = true
    )
    List<AlbumInterface> searchAlbumStart(@Param("keyword") String keyword);

    @Query(value = "SELECT A.album_id as id, A.album_name as name, MAX(popularity) as popularity FROM (( SELECT * FROM album WHERE album_name like %:keyword ) AS A \n" +
            "INNER JOIN music ON music.album_id = A.album_id )\n" +
            "INNER JOIN music_feature ON music.feature_id = music_feature.feature_id GROUP BY A.album_id order by popularity desc limit 1000"
            , nativeQuery = true
    )
    List<AlbumInterface> searchAlbumEnd(@Param("keyword") String keyword);

    @Query(value = "SELECT A.album_id as id, A.album_name as name, MAX(popularity) as popularity FROM (( SELECT * FROM album WHERE album_name like %:keyword% ) AS A \n" +
            "INNER JOIN music ON music.album_id = A.album_id )\n" +
            "INNER JOIN music_feature ON music.feature_id = music_feature.feature_id GROUP BY A.album_id order by popularity desc limit 1000"
            , nativeQuery = true
    )
    List<AlbumInterface> searchAlbumContain(@Param("keyword") String keyword);

}
