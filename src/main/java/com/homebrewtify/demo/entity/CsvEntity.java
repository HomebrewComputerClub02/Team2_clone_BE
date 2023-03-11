package com.homebrewtify.demo.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Getter
@Setter
@AllArgsConstructor
public class CsvEntity {
    @Id
    @Column(name = "id")
    private String id;
    private String trackId;
    private String artists;
    private String albumName;
    private String trackName;
    private String popularity;
    private String duration;
    private String explicit;
    private String dance;
    private String energy;
    private String keyValue;
    private String loudness;
    private String mode;
    private String speech;
    private String acoustic;
    private String instrument;
    private String live;
    private String valence;
    private String tempo;
    private String timeSignature;
    private String trackGenre;

}
