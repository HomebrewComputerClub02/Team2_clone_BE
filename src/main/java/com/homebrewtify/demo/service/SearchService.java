package com.homebrewtify.demo.service;

import com.homebrewtify.demo.entity.Music;
import com.homebrewtify.demo.repository.FeatureRepository;
import com.homebrewtify.demo.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {
    private final MusicRepository musicRepository;
    private final FeatureRepository featureRepository;
    public void searchSong(String keyword){
//        List<Music> musicList = musicRepository.searchMusicStart(keyword);
        List<Music> musicList = musicRepository.searchMusicContain(keyword);
        log.info("size: "+musicList.size());
        musicList.forEach(music -> System.out.println("music.getTitle() = " + music.getTitle()));
    }
}
