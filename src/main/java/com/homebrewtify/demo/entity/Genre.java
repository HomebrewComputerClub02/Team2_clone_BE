package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Genre {
    @Id
    @Column(name = "genre_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String genreId;

    private String genreName;

    @OneToMany(mappedBy = "genre")
    private List<Music> musicList=new ArrayList<>();

    @Builder(builderClassName = "Init",builderMethodName = "init")
    public Genre(String genreName){
        this.genreName=genreName;
    }
}
