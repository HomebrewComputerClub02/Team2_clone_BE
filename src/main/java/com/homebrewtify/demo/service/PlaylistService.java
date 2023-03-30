package com.homebrewtify.demo.service;

import com.homebrewtify.demo.dto.MusicInPlaylist;
import com.homebrewtify.demo.dto.PlaylistRes;
import com.homebrewtify.demo.dto.musicinplaylist.AlbumInMusicInPlaylist;
import com.homebrewtify.demo.dto.musicinplaylist.SingerInMusicInPlaylist;
import com.homebrewtify.demo.entity.MusicPlaylist;
import com.homebrewtify.demo.entity.Playlist;
import com.homebrewtify.demo.repository.MusicPlaylistRepository;
import com.homebrewtify.demo.repository.MusicRepository;
import com.homebrewtify.demo.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;

    private final MusicPlaylistRepository musicPlaylistRepository;

    private final MusicRepository musicRepository;
    public PlaylistRes getPlaylist(String playlistId){

        Optional<Playlist> pl = playlistRepository.findById(playlistId);
        String pl_id = pl.map(Playlist::getId).orElse(null);
        String pl_name = pl.map(Playlist::getName).orElse(null);
        String pl_coverImgUrl = null;
        List<MusicInPlaylist> musicList;
        List<MusicPlaylist> findmusics = musicPlaylistRepository.findByPlaylist_Id(playlistId);
        musicList = findmusics.stream().map(m-> MusicInPlaylist.builder()
                .id(m.getMusic().getMusicId())
                .title(m.getMusic().getTitle())
                .singer(m.getMusic().getMusicSingerList().stream().map(ms -> SingerInMusicInPlaylist.builder()
                            .id(ms.getSinger().getId())
                            .name(ms.getSinger().getSingerName())
                            .build())
                        .collect(Collectors.toList()))
                .add_date(m.getPlaylist_date())
                .album(AlbumInMusicInPlaylist.builder()
                                .id(m.getMusic().getAlbum().getId())
                                .name(m.getMusic().getAlbum().getAlbumName())
                                .build())
                .build()).collect(Collectors.toList());

        PlaylistRes result = PlaylistRes.builder().
                        playlist_id(pl_id)
                        .playlist_name(pl_name)
                        .coverImgUrl(pl_coverImgUrl)
                        .musicList(musicList)
                        .build();


        return result;
    }


}
