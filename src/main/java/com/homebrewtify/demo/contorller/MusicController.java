package com.homebrewtify.demo.contorller;

import com.homebrewtify.demo.dto.MusicDto;
import com.homebrewtify.demo.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;

//    @GetMapping("/artist/{singerId}")
//    public ResponseEntity<> getSinger(@PathVariable String singerId){
//        //해당 가수의 인기 곡 리스트
//
//        //앨범 리스트
//
//        return new ResponseEntity<>;
//    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<MusicDto.albumRes> getAlbum(@PathVariable String albumId){
        return ResponseEntity.ok(musicService.getAlbumInfo(albumId));
    }

    @GetMapping("/singer/{singerId}")
    public ResponseEntity<MusicDto.singerRes> getSinger(@PathVariable String singerId){
        return ResponseEntity.ok(musicService.getSingerInfo(singerId));
    }
}
