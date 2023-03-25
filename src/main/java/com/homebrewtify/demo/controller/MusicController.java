package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.dto.MusicDto;
import com.homebrewtify.demo.service.MusicService;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.homebrewtify.demo.dto.MusicDto.*;

@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

    @Operation(summary = "앨범 상세 조회", description = "앨범 클릭 시 나오는 노래 리스트")
    @GetMapping("/album/{albumId}")
    public ResponseEntity<albumRes> getAlbum(@Parameter(description = "앨범ID", required = true) @PathVariable String albumId){
        return ResponseEntity.ok(musicService.getAlbumInfo(albumId));
    }

    @Operation(summary = "가수 상세 조회", description = "가수 클릭 시 나오는 정보 리스트")
    @GetMapping("/singer/{singerId}")
    public ResponseEntity<singerRes> getSinger(@Parameter(description = "가수ID", required = true)@PathVariable String singerId){
        return ResponseEntity.ok(musicService.getSingerInfo(singerId));
    }

}
