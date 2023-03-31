package com.homebrewtify.demo.dto;

import com.homebrewtify.demo.dto.musicinplaylist.AlbumInMusicInPlaylist;
import com.homebrewtify.demo.dto.musicinplaylist.SingerInMusicInPlaylist;
import com.homebrewtify.demo.entity.Album;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MusicInPlaylist {
    private String id;  //music id
    private String title;
    private List<SingerInMusicInPlaylist> singer;
    private java.sql.Date add_date;
    private AlbumInMusicInPlaylist album;

    @Builder
    public MusicInPlaylist(String id, String title, List<SingerInMusicInPlaylist> singer, java.sql.Date add_date, AlbumInMusicInPlaylist album)
    {
        this.id = id;
        this.title = title;
        this.singer = singer;
        this.add_date = add_date;
        this.album = album;
    }
}
