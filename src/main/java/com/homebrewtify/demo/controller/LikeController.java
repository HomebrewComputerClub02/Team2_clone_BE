package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.dto.MusicDto;
import com.homebrewtify.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final MusicService musicService;
    @Operation(summary = "노래에 좋아요 표시", description = "노래에 좋아요 표시하는 api")
    @PostMapping("/{musicId}")
    public ResponseEntity<String> saveLikeMusic(@PathVariable String musicId){
        //해당 유저의 likeList에 music을 추가한다.
        musicService.saveLike(musicId);

        return ResponseEntity.ok("like Saved");
    }
    @Operation(summary = "노래에 좋아요 삭제", description = "노래에 좋아요 삭제하는 api")
    @DeleteMapping("/{musicId}")
    public ResponseEntity<String> deleteLikeMusic(@PathVariable String musicId){
        //해당 유저의 likeList에 music을 삭제한다.
        musicService.deleteLike(musicId);
        return ResponseEntity.ok("like Removed");
    }

    @Operation(summary = "좋아요 리스트 페이지 Api", description = "좋아요 리스트 페이지에 필요한 정보 반환(유저이름, 음악리스트)")
    @GetMapping("")
    public ResponseEntity<MusicDto.LikeRes> getLikeList(){
        //userId를 JWT로 받아서
        //해당 유저의 likeList를 반환해준다.
        return ResponseEntity.ok(musicService.getLikeMusicRes());
    }
}
