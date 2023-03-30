package com.homebrewtify.demo.dto.musicinplaylist;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlbumInMusicInPlaylist {
    private String id;
    private String name;

    @Builder
    AlbumInMusicInPlaylist(String id, String name){
        this.id = id;
        this.name = name;
    }
}

