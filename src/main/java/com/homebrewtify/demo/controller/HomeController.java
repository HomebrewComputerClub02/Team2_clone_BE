package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.dto.MusicDto;
import com.homebrewtify.demo.service.GenreService;
import com.homebrewtify.demo.service.MusicPopularityService;
import com.homebrewtify.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
    private final MusicService musicService;
    private final GenreService genreService;

    private final MusicPopularityService popularityService;
    @Operation(summary = "홈 화면 Api", description = "홈 화면에 필요한 최근 재생목록 , 랜덤 장르 플레이리스트 반환")
    @GetMapping("/home")
    public ResponseEntity<MusicDto.HomeRes> homeRes(){
        //해당 사용자 아이디로 play_record테이블에서 최근 재생목록 5개 뽑아옴
        MusicDto.HomeRes res = musicService.getRecentPlayListByUser();
        String uGenre1= genreService.getRandomUpperGenre();
        String uGenre2= genreService.getRandomUpperGenre(uGenre1);
        String uGenre3= genreService.getRandomUpperGenre(uGenre1,uGenre2);
        res.setRandomResult1(new MusicDto.Result<>(genreService.getPlaylistCoversByGenre(uGenre1),uGenre1));
        res.setRandomResult2(new MusicDto.Result<>(genreService.getPlaylistCoversByGenre(uGenre2),uGenre2));
        res.setRandomResult3(new MusicDto.Result<>(genreService.getPlaylistCoversByGenre(uGenre3),uGenre3));
        res.setTop10PopularSinger(new MusicDto.Result<>(popularityService.getTop10PopularArtist(),"인기 아티스트"));
        res.setTop10PopularAlbums(new MusicDto.Result<>(popularityService.getTop10PopularAlbum(), "인기 앨범"));

        return ResponseEntity.ok(res);
    }
}
