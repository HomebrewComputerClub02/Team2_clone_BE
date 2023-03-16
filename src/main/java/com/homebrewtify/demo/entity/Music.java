package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
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

    @ManyToOne
    @JoinColumn(name = "album_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Album album;

    @ManyToOne
    @JoinColumn(name = "singer_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Singer singer;

    @OneToOne
    @JoinColumn(name="feature_id")
    private MusicFeature feature;

    //장르를 OneToMany로 가져야 하나 ??

}
