package com.homebrewtify.demo.contorller;

import com.homebrewtify.demo.dto.UpperGenre;
import com.homebrewtify.demo.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping()
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping("/search")
    public UpperGenre[] getAllGenre(){
        return genreService.getAllGenre();
    }


}
