package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Album {
    @Id
    @Column(name = "album_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String albumId;

    private String albumName;
    private String releaseDate;

    private String imgUrl;

    @OneToMany(mappedBy = "album")
    private Set<SingerAlbum> singers = new HashSet<>();
}
