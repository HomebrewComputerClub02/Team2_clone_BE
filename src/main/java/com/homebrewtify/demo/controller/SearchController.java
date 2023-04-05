package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.dto.MusicDto;
import com.homebrewtify.demo.dto.SearchMusicDto;
import com.homebrewtify.demo.dto.interfacedto.AlbumInterface;
import com.homebrewtify.demo.dto.interfacedto.SingerInterface;
import com.homebrewtify.demo.entity.Music;
import com.homebrewtify.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/searchAll/{keyword}")
    public ResponseEntity<MusicDto.SearchRes> search(@PathVariable("keyword") String keyword){
        MusicDto.SearchRes res = new MusicDto.SearchRes();

        res.setMusicResult(new MusicDto.Result<>(searchService.searchSong(keyword), "곡 검색 "));
        res.setAlbumResult(new MusicDto.Result<>(searchService.searchAlbum(keyword), "앨범 검색"));
        res.setSingerResult(new MusicDto.Result<>(searchService.searchSinger(keyword), "아티스트 검색"));

        return ResponseEntity.ok(res);
    }

    @GetMapping("/searchMusic/{keyword}")
    public List<SearchMusicDto> searchMusic(@PathVariable("keyword") String keyword) {
        return searchService.searchSong(keyword);
    }

    @GetMapping("/searchAlbum/{keyword}")
    public List<AlbumInterface> searchAlbum(@PathVariable("keyword") String keyword) {
        return searchService.searchAlbum(keyword);
    }

    @GetMapping("/searchArtist/{keyword}")
    public List<SingerInterface> searchArtist(@PathVariable("keyword") String keyword) {
        return searchService.searchSinger(keyword);
    }
}
