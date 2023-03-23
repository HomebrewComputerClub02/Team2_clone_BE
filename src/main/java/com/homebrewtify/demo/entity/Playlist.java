package com.homebrewtify.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "user_id_index", columnList = "user_id")})
public class Playlist {
    @Id
    @Column(name = "playlist_id")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String playlistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;

}
