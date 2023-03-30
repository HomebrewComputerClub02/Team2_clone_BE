package com.homebrewtify.demo.dto;

import com.homebrewtify.demo.entity.Music;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistRes{
    private String playlist_id;  //playlist id;
    private String playlist_name;
    private String coverImgUrl;
    private List<MusicInPlaylist> musicList;

    @Builder
    public PlaylistRes(String playlist_id, String playlist_name, String coverImgUrl, List<MusicInPlaylist> musicList){
        this.playlist_id = playlist_id;
        this.playlist_name = playlist_name;
        this.coverImgUrl = coverImgUrl;
        this.musicList = musicList;
    }

}
