package com.homebrewtify.demo.dto;


import com.homebrewtify.demo.dto.interfacedto.SingerInterface;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SearchMusicDto {
    private String music_id;
    private String title;
    private List<Artist> singers = new ArrayList<>();

    @Data
    @AllArgsConstructor
    public static class Artist{
        private String id;
        private String name;
    }
}
