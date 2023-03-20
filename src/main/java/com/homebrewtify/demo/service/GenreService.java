package com.homebrewtify.demo.service;

import com.homebrewtify.demo.dto.UpperGenre;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GenreService {
    private UpperGenre[] genreLists = {new UpperGenre("포크 및 어쿠스틱", Arrays.asList("acoustic"), "#bc5900", "https://i.scdn.co/image/ab67fb8200005cafcc70a3c2e4c71398708bdc4a"),
    new UpperGenre("국가별", Arrays.asList("afrobeat", "brazil", "british", "french", "german", "iranian", "malay", "mandopop", "swedish", "turkish"), "#e8115b", "https://t.scdn.co/images/37732285a0ff4e24987cdf5c45bdf31f.png"),
    new UpperGenre("록", Arrays.asList("alt-rock","garage", "goth", "hard-rock", "hardcore", "hardstyle", "industrial", "psych-rock", "punk-rock", "rock-n-roll", "rock", "rockabilly"),"#e91429","https://i.scdn.co/image/ab67fb8200005cafae7e69beb88f16969641b53e"),
    new UpperGenre("얼터너티브", Arrays.asList("alternative"),"#e91429", "https://i.scdn.co/image/ab67fb8200005cafda178a834e4f87371e9fa543"),
    new UpperGenre("댄스/일렉트로닉", Arrays.asList("ambient", "breakbeat", "chicago-house", "club", "dance", "dancehall", "detroit-techno", "drum-and-bass", "edm", "elctro", "electronic", "idm", "minimal-techno", "progressive-house", "techno", "trance"),"#d84000", "https://i.scdn.co/image/ab67fb8200005cafdfdaac1cf9574a196ca25196"),
    new UpperGenre("애니메이션", Arrays.asList("animation", "disney"),"#e41d63","https://i.scdn.co/image/ab67706f00000002c19c5f13f8b3ff2d73ff00bc"),
    new UpperGenre("메탈", Arrays.asList("black-metal", "death-metal", "emo", "grindcore", "heavy-metal", "metal", "metalcore"),"#e91429", "https://i.scdn.co/image/ab67fb8200005cafefa737b67ec51ec989f5a51d"),
    new UpperGenre("컨트리", Arrays.asList("bluegrass", "country", "folk"),"#d84000","https://i.scdn.co/image/ab67fb8200005cafc0d2222b4c6441930e1a386e"),
    new UpperGenre("블루스", Arrays.asList("blues"),"#b06239","https://i.scdn.co/image/ab67fb8200005caff22ac5cab318d550b593ffac"),
    new UpperGenre("팝", Arrays.asList("pop","cantopop","power-pop", "ska", "synth-pop", "world-music"),"#148a08","https://i.scdn.co/image/ab67fb8200005cafa862ab80dd85682b37c4e768"),
    new UpperGenre("어린이 및 가족", Arrays.asList("children", "kids", ""),"#8d67ab","https://i.scdn.co/image/ab67fb8200005caf8a04560a209b3f32165ea8a2"),
    new UpperGenre("휴식", Arrays.asList("chill","sleep"),"#d84000", "https://i.scdn.co/image/ab67fb8200005caf47e942f5bea637f4f4760170"),
    new UpperGenre("클래식", Arrays.asList("classical"),"#7d4b32", "https://i.scdn.co/image/ab67fb8200005caf12809992dfc5b318892ea07b")};

    public UpperGenre[] getAllGenre(){
        return genreLists;
    }
}
