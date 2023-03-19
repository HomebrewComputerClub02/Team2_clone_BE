package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Album{
    @Id
    @Column(name = "album_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //@GeneratedValue(strategy = GenerationType.)
    private String id;

    private String albumName;
    private String releaseDate;

    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Singer singer;

    @Builder(builderClassName = "Init",builderMethodName = "init")
    public Album(String albumName,Singer singer){
        singer.getAlbums().add(this);
        this.albumName=albumName;
        this.singer=singer;
    }
}
