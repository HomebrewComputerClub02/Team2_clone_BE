package com.homebrewtify.demo.service;

import com.homebrewtify.demo.dto.musicinplaylist.AlbumInMusicInPlaylist;
import com.homebrewtify.demo.dto.musicinplaylist.SingerInMusicInPlaylist;
import com.homebrewtify.demo.dto.popularity.AlbumInterface;
import com.homebrewtify.demo.dto.popularity.SingerInterface;
import com.homebrewtify.demo.repository.AlbumRepository;
import com.homebrewtify.demo.repository.MusicRepository;
import com.homebrewtify.demo.repository.SingerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicPopularityService {
    private final MusicRepository musicRepository;
    private final SingerRepository singerRepository;
    private final AlbumRepository albumRepository;

    public List<SingerInterface> getTop10PopularArtist(){
        return singerRepository.findTop10ByPopularity();
    }

    public List<AlbumInterface> getTop10PopularAlbum(){
        return albumRepository.findTop10ByPopularity();
    }
}
