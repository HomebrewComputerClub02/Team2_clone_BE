package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.dto.MusicDto;
import com.homebrewtify.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library")
public class LibraryController {
    private final MusicService musicService;
    @Operation(summary = "앨범 팔로우 목록 조히", description = "내 라이브러리 내의 앨범 팔로우 목록 조회")
    @GetMapping("/album")
    public ResponseEntity<MusicDto.Result> getFollowAlbum(){
        //userId를 JWT로 받아서
        List<MusicDto.AlbumDto> followAlbumList = musicService.getFollowAlbumList();
        MusicDto.Result result=new MusicDto.Result(followAlbumList,"albumList");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "가수 팔로우 목록 조히", description = "내 라이브러리 내 아티스트 팔로우 목록 조회")
    @GetMapping("/singer")
    public ResponseEntity<MusicDto.Result> getFollowSinger(){
        //userId를 JWT로 받아서
        List<MusicDto.MusicSingerDto> followSingerList = musicService.getFollowSingerList();
        MusicDto.Result result=new MusicDto.Result(followSingerList,"albumList");
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "플레이리스트 목록 조회", description = "!! type:유저닉네임  !! 내 라이브러리 내의 내 플레이리스트 조회")
    @GetMapping("/playList")
    public ResponseEntity<MusicDto.Result> getLibraryPlayList(){
        //userId를 JWT로 받아서
        return ResponseEntity.ok(musicService.getUserPlayList());
    }
}
