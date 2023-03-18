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
    private Long popularity;
    private Long duration;
    private String explicit;
    private Double dance;
    private Double energy;
    private Long keyValue;
    private Double loudness;
    private Long mode;
    private Double speech;
    private Double acoustic;
    private Double instrument;
    private Double live;
    private Double valence;
    private Double tempo;
    private Long timeSignature;
    private String trackGenre;

}
