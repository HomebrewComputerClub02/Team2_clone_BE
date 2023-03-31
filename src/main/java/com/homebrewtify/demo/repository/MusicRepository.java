package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Album;
import com.homebrewtify.demo.entity.Music;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music,String> {
    List<Music> findByAlbum_Id(String albumId);

    List<Music> findByAlbum(Album album);

    List<Music> findFirst10ByGenre_GenreName(String name);
    List<Music> findTop10ByGenre_GenreNameOrderByFeature_PopularityDesc(String name);

    @Query("select m from Music m join fetch m.album where m.album.id=:albumId")
    List<Music> findByAlbumId(String albumId);

    @Query("select m from Music m where m.album.id=:albumId")
    List<Music> findByAlbumId2(String albumId);

    @EntityGraph(attributePaths = "feature")
    List<Music> findWithFeatureByAlbum(Album album);

    @Query(value = "select * from music m join music_feature f on m.feature_id=f.feature_id " +
            "where m.title like :keyword%  order by popularity desc limit 1000"
            , nativeQuery = true
    )
    List<Music> searchMusicStart(@Param("keyword") String keyword);

    @Query(value = "select * from music m join music_feature f on m.feature_id=f.feature_id " +
            "where m.title like %:keyword  order by popularity desc limit 1000"
            , nativeQuery = true
    )
    List<Music> searchMusicEnd(@Param("keyword") String keyword);

    @Query(value = "select * from music m join music_feature f on m.feature_id=f.feature_id " +
            "where m.title like %:keyword%  order by popularity desc limit 1000"
            , nativeQuery = true
    )
    List<Music> searchMusicContain(@Param("keyword") String keyword);


}
