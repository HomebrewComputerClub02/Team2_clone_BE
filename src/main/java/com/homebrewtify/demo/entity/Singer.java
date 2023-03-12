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
public class Singer {
    @Id
    @Column(name = "singer_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String singerId;

    private String singerName;
    private String imgUrl;

    @OneToMany(mappedBy = "singer")
    private Set<SingerAlbum> singerAlbums=new HashSet<>();
}
