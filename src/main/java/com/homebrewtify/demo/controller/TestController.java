package com.homebrewtify.demo.controller;


import com.homebrewtify.demo.dto.musicinplaylist.AlbumInMusicInPlaylist;
import com.homebrewtify.demo.dto.popularity.AlbumInterface;
import com.homebrewtify.demo.dto.popularity.SingerInterface;
import com.homebrewtify.demo.service.MusicPopularityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private MusicPopularityService musicPopularityService;
    @GetMapping("/test")
    public List<SingerInterface> test(){
        return musicPopularityService.getTop10PopularArtist();
    }

}
