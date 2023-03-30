package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.dto.musicinplaylist.SingerInMusicInPlaylist;
import com.homebrewtify.demo.dto.popularity.SingerInterface;
import com.homebrewtify.demo.entity.Singer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SingerRepository extends JpaRepository<Singer,String> {
    @EntityGraph(attributePaths = "albums")
    Optional<Singer> findWithAlbumsById(String singeId);

    @Query(value = "SELECT singer_id AS id, singer_name AS name from singer INNER JOIN (SELECT album.singer_id as id, AVG(pop) as avgpop FROM album INNER JOIN (SELECT album_id, MAX(music_feature.popularity) as pop FROM music INNER JOIN music_feature ON music.feature_id = music_feature.feature_id GROUP BY album_id) as T ON T.album_id = album.album_id GROUP BY singer_id ORDER BY avgpop DESC LIMIT 10) as T2 ON T2.id = singer.singer_id", nativeQuery = true)
    List<SingerInterface> findTop10ByPopularity();
}
