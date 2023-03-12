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

    @ManyToMany
    @JoinTable(
            name = "music_genre",
            joinColumns = {@JoinColumn(name = "music_id", referencedColumnName = "music_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id", referencedColumnName = "genre_id")})
    private Set<Genre> genreSet;

}
