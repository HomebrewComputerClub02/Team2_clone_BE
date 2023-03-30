package com.homebrewtify.demo.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

public class MusicDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LikeRes{
        private String userName;
        private List<MusicListDto> musicList;

    }
    @Data
    public static class HomeRes {
        private List<Result> resultList=new ArrayList<>();

        private Result randomResult1;
        private Result randomResult2;
        private Result randomResult3;
    }
    @Data
    @AllArgsConstructor
    public static class Result<T> {
        private T data;

        private String type;
    }

    @Builder
    @Getter
    @Setter
    public static class SingerRes {
        private String singerName;
        @Builder.Default
        private String singerImgUrl="X";

        //인기 10, 참여 노래가 아닌 소유 앨범의 노래 기준,
        private List<MusicListDto> musicList;

        //디스코 그래피 (앨범 리스트만)
        private List<AlbumDto> albumList;


        //참여 앨범
        private List<AlbumDto> joinAlbumList;
    }

    @Builder
    @Getter
    @Setter
    public static class AlbumRes {
        private String albumName;
        private MusicSingerDto albumSinger;
        private List<MusicListDto> musicList;
        //노래당 트랙 id, 제목, 가사(x), 가수목록(가수id,가수이름)

    }
    @Builder
    @Getter
    @Setter
    public static class MusicListDto {
        private String trackId;
        private String title;

        @Builder.Default
        private Long seconds=-1L;
        private List<MusicSingerDto> singerList;
        //가수이름, 가수 id
    }
    @Builder
    @Getter
    @Setter
    public static class MusicSingerDto {
        private String singerId;
        private String singerName;
        @Builder.Default
        private String imgUrl="X";
    }
    @Builder
    @Getter
    @Setter
    public static class AlbumDto {
        private String albumId;
        private String albumName;

        private String singerName;
        private String singerId;
        @Builder.Default
        private String imgUrl="X";
        @Builder.Default
        private String releaseDate="X";
    }

}
