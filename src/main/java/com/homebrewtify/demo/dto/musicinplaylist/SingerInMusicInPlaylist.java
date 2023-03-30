package com.homebrewtify.demo.dto.musicinplaylist;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingerInMusicInPlaylist {
    private String id;      //singer_id
    private String name;

    @Builder
    SingerInMusicInPlaylist(String id, String name){
        this.id = id;
        this.name = name;
    }
}
