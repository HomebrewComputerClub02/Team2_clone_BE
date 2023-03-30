package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
    private final MusicService musicService;
    @Operation(summary = "앨범 팔로우")
    @PostMapping("/album/{albumId}")
    public ResponseEntity<String> addFollowAlbum(@PathVariable String albumId){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.addFollowAlbum(userId,albumId);
        return ResponseEntity.ok("Album Follow Saved");
    }

    @Operation(summary = "앨범팔로우 취소")
    @DeleteMapping("/album/{albumId}")
    public ResponseEntity<String> deleteFollowAlbum(@PathVariable String albumId){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.deleteFollowAlbum(userId,albumId);
        return ResponseEntity.ok("Album Follow Deleted");
    }
    @Operation(summary = "가수 팔로우")
    @PostMapping("/singer/{singerId}")
    public ResponseEntity<String> addFollowSinger(@PathVariable String singerId){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.addFollowSinger(userId,singerId);
        return ResponseEntity.ok("Album Follow Saved");
    }

    @Operation(summary = "가수 팔로우 취소")
    @DeleteMapping("/singer/{singerId}")
    public ResponseEntity<String> deleteFollowSinger(@PathVariable String singerId){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.deleteFollowSinger(userId,singerId);
        return ResponseEntity.ok("Album Singer Deleted");
    }
}
