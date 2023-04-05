package com.homebrewtify.demo.controller;


import com.homebrewtify.demo.dto.interfacedto.SingerInterface;
import com.homebrewtify.demo.service.MusicPopularityService;
import com.homebrewtify.demo.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private MusicPopularityService musicPopularityService;

    @Autowired
    private SearchService searchService;
    @GetMapping("/test")
    public List<SingerInterface> test(){
        return musicPopularityService.getTop10PopularArtist();
    }

//    @GetMapping("/test")
//    public ResponseEntity<MusicDto.SearchRes> test(@RequestParam("keyword") String keyword){
//        MusicDto.SearchRes res = new MusicDto.SearchRes();
//
//        res.setMusicResult(new MusicDto.Result<>(searchService.searchSong(keyword), "곡 검색 "));
//        res.setAlbumResult(new MusicDto.Result<>(searchService.searchAlbum(keyword), "앨범 검색"));
//        res.setSingerResult(new MusicDto.Result<>(searchService.searchSinger(keyword), "아티스트 검색"));
//
//        return ResponseEntity.ok(res);
//    }

}
