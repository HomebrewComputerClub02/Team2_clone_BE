package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.dto.PlaylistRes;
import com.homebrewtify.demo.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/playlist/{playlistId}")
    public PlaylistRes getPlaylist(@PathVariable String playlistId){
        return playlistService.getPlaylist(playlistId);
    }


}
