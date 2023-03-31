package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/searchMusic")
    public void searchMusic(@RequestParam("keyword") String keyword) {
        searchService.searchSong(keyword);
    }
}
