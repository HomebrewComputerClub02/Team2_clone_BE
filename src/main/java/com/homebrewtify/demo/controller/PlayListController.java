package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.dto.MusicDto;
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

    @Operation(summary = "플레이리스트 이름 변경")
    @PutMapping("")
    public ResponseEntity<String> updatePlayListName(@RequestParam String playListId, @RequestParam String name){
        musicService.renamePlaylist(playListId, name);
        return ResponseEntity.ok("PlayList is updated");
    }

    @Operation(summary = "플레이리스트 삭제")
    @DeleteMapping("/{playListId}")
    public ResponseEntity<String> deletePlayList(@PathVariable String playListId){
        musicService.deletePlaylist(playListId);
        return ResponseEntity.ok("PlayList is deleted");
    }
    @Operation(summary = "유저의 플레이리스트 목록 조회")
    @GetMapping("/getUserPlayList")
    public ResponseEntity<MusicDto.Result> getListOfPlaylist(){
        //userId를 JWT로 받아서
        Long userId=2L;
        return ResponseEntity.ok(musicService.getUserPlayList(userId));
    }
    @Operation(summary = "플레이리스트 조회", description = "플리 이름은 알아서..? type=유저이름 or homebrewtify")
    @GetMapping("/{playListId}")
    public ResponseEntity<MusicDto.Result> getUserPlayList(@PathVariable String playListId){
        return ResponseEntity.ok(musicService.getMusicByPlayList(playListId));
    }

    @Operation(summary = "플레이리스트에 노래 추가하기")
    @PostMapping("/music")
    public ResponseEntity<String> addMusicToPlayList(@RequestParam String playListId, @RequestParam String musicId){
        musicService.addMusicToPlayList(playListId, musicId);
        return ResponseEntity.ok("Music is added successfully");
    }
    @Operation(summary = "플레이리스트에 노래 삭제하기")
    @DeleteMapping("/music")
    public ResponseEntity<String> deleteMusicToPlayList(@RequestParam String playListId, @RequestParam String musicId){
        musicService.deleteMusicFromPlaylist(playListId, musicId);
        return ResponseEntity.ok("Music is deleted successfully");
    }
}