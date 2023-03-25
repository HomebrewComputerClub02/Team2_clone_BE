package com.homebrewtify.demo.dto;

import com.homebrewtify.demo.entity.Music;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MusicDto {
    public static class HomeRes{

        //?
        private String genreName;
//        List<PlayList> playlists;
//
//        List<PlayList> randomPlaylists;
    }
    @Builder
    @Getter
    @Setter
    public static class singerRes{
        private String singerName;

        //인기 10, 참여 노래가 아닌 소유 앨범의 노래 기준,
        private List<musicListDto> musicList;

        //디스코 그래피 (앨범 리스트만)
        private List<albumDto> albumList;
        //피처링(플레이리스트형태)
        //그냥 피처링한 곡을 모아서 줘야 하나..?


        //피처링, 팬들이 좋아하는 다른 음악 , 참여 앨, 발견 위치
        //피처링은 가능은 한데 힘들고
        //팬들이 좋아하는거 불가능 user 추천관련
        //참여 앨범 불가능(해당 앨범에 그냥 앨범 주인 한명만 존재
        //발견위 불가능..
    }

    @Builder
    @Getter
    @Setter
    public static class albumRes{
        private String albumName;
        private musicSingerDto singer;

        private List<musicListDto> musicList;
        //노래당 트랙 id, 제목, 가사(x), 가수목록(가수id,가수이름)

    }
    @Builder
    @Getter
    @Setter
    public static class musicListDto{
        private String trackId;
        private String title;
        private List<musicSingerDto> singerList;
        //가수이름, 가수 id
    }
    @Builder
    @Getter
    @Setter
    public static class musicSingerDto{
        private String singerId;
        private String singerName;
        @Builder.Default
        private String imgUrl="X";
    }
    @Builder
    @Getter
    @Setter
    public static class albumDto{
        private String albumId;
        private String albumName;
        @Builder.Default
        private String imgUrl="X";
        @Builder.Default
        private String releaseDate="X";
    }

}
