package com.homebrewtify.demo.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MusicFeature {
    @Id
    @Column(name = "feature_id")
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String featureId;

    //0~100 100에 가까울 수록 인기곡
    Long popularity;
    //ms단위의 노래 길이
    Long duration;
    // 가사의 존재 여부?
    String explicit;
    // 0~1 , 1이 가장 dance
    Double dance;
    // 0~1, 1이 가장 energy있다 (빠르고 시끄럽다)
    Double energy;
    // 음계? 0=C 1=C# ..
    Long keyValue;
    //트랙의 db
    Double loudness;
    //modality( major or minor) 나타낸다. 0=minor 1=major #무슨말인지 모르겠음
    Long mode;
    //단어가 많이 나올 수록 1.0에 근접한다.
    Double speech;
    //어쿠스틱한지에 대한 정보 1.0에 가까울수록 어쿠스틱하다.
    Double acoustic;
    //1.0에 가까울 수록 악기소리가 많다
    Double instrument;
    //청중의 소리가 많을 수록 1.0 (라이브일 것이다.)
    Double live;
    //음악적으로 선한? 정도를 나타냄, 1.0에 가까울수록 기쁜 노래
    Double valence;
    //bpm
    Double tempo;
    // n/4박자 를 의미한다
    Long timeSignature;

//    @OneToOne
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    private Music music;
    //음악을 조회하며 음악 특징을 조히하는 경우가 더 많을 것이므로 외래키 주인을 음악으로 설정

}
