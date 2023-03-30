package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.Music;
import com.homebrewtify.demo.entity.MusicPlaylist;
import com.homebrewtify.demo.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MusicPlaylistRepository extends JpaRepository<MusicPlaylist, String> {
    Optional<MusicPlaylist> findFirstByPlaylist_Id(String id);


    List<MusicPlaylist> findByPlaylist_Id(String id);

    List<MusicPlaylist> findByPlaylist(Playlist playlist);

    List<MusicPlaylist> findByPlaylistAndMusic(Playlist playlist, Music music);


}
