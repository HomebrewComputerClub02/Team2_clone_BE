package com.homebrewtify.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "follow_album")
public class FollowAlbum {
    @Id
    @Column(name = "follow_album_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    //유저의 가수 팔로우 목록을 조회하는 경우 유저와 가수 정보 모두 필요하니 eager
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    public void createFollowAlbum(User user, Album album) {
        this.user = user;
        this.album=album;
        user.getFollowAlbumList().add(this);
    }
}
