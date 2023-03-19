package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Singer{
    @Id
    @Column(name = "singer_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String singerName;
    private String imgUrl;

    @OneToMany(mappedBy = "singer")
    private List<Album> albums=new ArrayList<>();

    @OneToMany(mappedBy = "singer")
    private List<MusicSinger> singerMusicList=new ArrayList<>();

    @Builder(builderClassName = "Init",builderMethodName = "init")
    public Singer(String singerName){
        //this.id= UUID.randomUUID().toString();
        this.singerName = singerName;
    }
}
