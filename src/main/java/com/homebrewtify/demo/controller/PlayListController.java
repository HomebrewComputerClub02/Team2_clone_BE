package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/playList")
public class PlayListController {
    private final MusicService musicService;

    //플레이리스트 만들기
    @Operation(summary = "플레리이스트 만들기")
    @PostMapping("")
    public ResponseEntity<String> createPlayList(){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.createPlayList(userId);
        return ResponseEntity.ok("PlayList is created");
    }
    @Operation(summary = "플레이리스트 조회")
    @GetMapping("/{playListId}")
    public ResponseEntity<String> getUserPlayList(@PathVariable String playListId){
        //userId를 JWT로 받아서
        Long userId=2L;
        //TODO : 내 플레이리스트 클릭 시 나오는 화면에 대한 정보 반환 기능 추가
        musicService.getPlaylist(playListId);
        return ResponseEntity.ok("PlayList is created");
    }
    @Operation(summary = "플레이리스트 이름 변경")
    @PutMapping("")
    public ResponseEntity<String> updatePlayListName(@RequestParam String playListId, @RequestParam String name){
        musicService.renamePlaylist(playListId, name);
        return ResponseEntity.ok("PlayList is updated");
    }
    @Operation(summary = "유저의 플레이리스트 목록 조회")
    @GetMapping("/getUserPlayList")
    public ResponseEntity<String> getListOfPlaylist(){
        //userId를 JWT로 받아서
        Long userId=2L;
        //TODO: 유저의 플레이리스트 목록 조회 후 플리 id, 이름 리턴하는 api
        return ResponseEntity.ok("PlayList is updated");
    }
}
