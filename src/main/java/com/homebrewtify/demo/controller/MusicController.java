package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.homebrewtify.demo.dto.MusicDto.*;

@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;


    @Operation(summary = "앨범 상세 조회", description = "앨범 클릭 시 나오는 노래 리스트")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<AlbumRes> getAlbum(@Parameter(description = "앨범ID", required = true) @PathVariable String albumId){
        return ResponseEntity.ok(musicService.getAlbumInfo(albumId));
    }

    @Operation(summary = "가수 상세 조회", description = "가수 클릭 시 나오는 정보 리스트")
    @GetMapping("/singer/{singerId}")
    public ResponseEntity<SingerRes> getSinger(@Parameter(description = "가수ID", required = true)@PathVariable String singerId){
        return ResponseEntity.ok(musicService.getSingerInfo(singerId));
    }

    @Operation(summary = "음악 재생", description = "음악 재생 시 서버에게 알려주는 api")
    @PostMapping("/play")
    public ResponseEntity<String> notifyPlay(@Parameter(description = "재생 type(SONG,SINGER,PLAYLIST,ALBUM)")@RequestParam("playType") String playType
        ,@Parameter(description = "재생한 대상의 id(플레이리스트면 playListId, 노래 한 곡이면 musicId")@RequestParam("dataId") String dataId
    ){

        //repo에 저장한다
        musicService.saveRecentPlay(playType,dataId);

        //저장 중 문제가 생기면 service에서 throw하니 여기선 ok
        return ResponseEntity.ok("Saved Recent Play");
    }


}
