package com.homebrewtify.demo.service;

import com.homebrewtify.demo.dto.SearchMusicDto;
import com.homebrewtify.demo.dto.interfacedto.AlbumInterface;
import com.homebrewtify.demo.dto.interfacedto.SingerInterface;
import com.homebrewtify.demo.entity.Music;
import com.homebrewtify.demo.repository.AlbumRepository;
import com.homebrewtify.demo.repository.FeatureRepository;
import com.homebrewtify.demo.repository.MusicRepository;
import com.homebrewtify.demo.repository.SingerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    private final MusicRepository musicRepository;
    private final AlbumRepository albumRepository;
    private final SingerRepository singerRepository;
    private final FeatureRepository featureRepository;
    public List<SearchMusicDto> searchSong(String keyword){
//        List<Music> musicList = musicRepository.searchMusicStart(keyword);
        List<SearchMusicDto> musicRes;
        List<Music> musicList = musicRepository.searchMusicContain(keyword);
        log.info("size: "+musicList.size());
        musicList.forEach(music -> System.out.println("music.getTitle() = " + music.getTitle()));
        musicRes = musicList.stream().map(music -> new SearchMusicDto(music.getMusicId(), music.getTitle(), music.getMusicSingerList().stream().map(ms -> new SearchMusicDto.Artist(ms.getSinger().getId(), ms.getSinger().getSingerName())).collect(Collectors.toList()))).collect(Collectors.toList());
        return musicRes;
    }

    public List<AlbumInterface> searchAlbum(String keyword){
//        List<AlbumInterface> albumList = musicRepository.searchAlbumStart(keyword);
        List<AlbumInterface> albumList = albumRepository.searchAlbumContain(keyword);
        log.info("size: "+albumList.size());
        albumList.forEach(album -> System.out.println("album.getAlbumName() = " + album.getName()));
        return albumList;
    }

    public List<SingerInterface> searchSinger(String keyword){
//        List<SingerInterface> singerList = musicRepository.searchAlbumStart(keyword);
        List<SingerInterface> singerList = singerRepository.searchSingerContain(keyword);
        log.info("size: "+singerList.size());
        singerList.forEach(singer -> System.out.println("singer.getName() = " + singer.getName()));
        return singerList;
    }
}
