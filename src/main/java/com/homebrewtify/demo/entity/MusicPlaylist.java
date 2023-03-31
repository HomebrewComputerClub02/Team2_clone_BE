package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicPlaylist {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "music_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Music music;

    @ManyToOne
    @JoinColumn(name= "playlist_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Playlist playlist;

    private java.sql.Date playlist_date = new java.sql.Date(System.currentTimeMillis());

    @Builder
    public MusicPlaylist(Music music, Playlist playlist){
        this.music = music;
        this.playlist = playlist;
        this.playlist_date = new java.sql.Date(System.currentTimeMillis());
    }

}
