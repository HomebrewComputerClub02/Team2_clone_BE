package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Album;
import com.homebrewtify.demo.entity.Music;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music,String> {
    List<Music> findByAlbum_Id(String albumId);

    List<Music> findByAlbum(Album album);

    @Query("select m from Music m join fetch m.album where m.album.id=:albumId")
    List<Music> findByAlbumId(String albumId);

    @Query("select m from Music m where m.album.id=:albumId")
    List<Music> findByAlbumId2(String albumId);

    @EntityGraph(attributePaths = "feature")
    List<Music> findWithFeatureByAlbum(Album album);


}
