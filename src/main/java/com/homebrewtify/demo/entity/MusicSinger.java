package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicSinger {
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
    @JoinColumn(name="singer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Singer singer;

    @Builder(builderClassName = "Init",builderMethodName = "init")
    public MusicSinger(Singer singer, Music music){
        singer.getSingerMusicList().add(this);
        this.singer=singer;
        music.getMusicSingerList().add(this);
        this.music=music;
    }
}
