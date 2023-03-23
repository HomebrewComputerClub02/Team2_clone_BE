package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Music {
    @Id
    @Column(name = "music_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String musicId;

    private String title;
    private String lyrics;
    //spotify open api trackID
    private String trackId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Album album;


    @OneToMany(mappedBy = "music")
    private List<MusicSinger> musicSingerList=new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="feature_id")
    private MusicFeature feature;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="genre_id")
    private Genre genre;


    @Builder(builderClassName = "Init",builderMethodName = "init")
    public Music(String title,String trackId, Album album, MusicFeature feature, Genre genre){
        this.title=title;
        this.trackId=trackId;
        this.album=album;
//        this.singer=singer;
        this.feature=feature;
        //여기서 연관관계 추가
        genre.getMusicList().add(this);
        this.genre=genre;
    }



}
