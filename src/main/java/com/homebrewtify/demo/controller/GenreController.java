package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.dto.GetAllGenreRes;
import com.homebrewtify.demo.dto.PlaylistCover;
import com.homebrewtify.demo.dto.UpperGenre;
import com.homebrewtify.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/search")
    public GetAllGenreRes[] getAllGenre(){
        return genreService.getAllGenre();
    }

    @GetMapping("/genre/{genreName}")
    public List<PlaylistCover> getPlaylistByGenre(@PathVariable String genreName){
        return genreService.getPlaylistCoversByGenre(genreName);
    }

}