package com.homebrewtify.demo.controller;

import com.homebrewtify.demo.repository.AlbumRepository;
import com.homebrewtify.demo.service.MusicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.homebrewtify.demo.dto.MusicDto.*;

@RestController
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final AlbumRepository albumRepository;

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
        //JWT로 사용자 인증 및 사용자의 username 이나 userId를 알아내고
        Long userId =2L;
        //repo에 저장한다
        musicService.saveRecentPlay(playType,userId,dataId);

        //저장 중 문제가 생기면 service에서 throw하니 여기선 ok
        return ResponseEntity.ok("Saved Recent Play");
    }

    @Operation(summary = "홈 화면 Api", description = "홈 화면에 필요한 최근 재생목록 , 랜덤 장르 플레이리스트 반환")
    @GetMapping("/home")
    public ResponseEntity<HomeRes> homeRes(){
        //JWT로 사용자 아이디 획득
        Long userId =2L;
        //해당 사용자 아이디로 play_record테이블에서 최근 재생목록 5개 뽑아옴
        HomeRes res = musicService.getRecentPlayListByUser(userId);
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "노래에 좋아요 표시", description = "노래에 좋아요 표시하는 api")
    @PostMapping("/like/{musicId}")
    public ResponseEntity<String>saveLikeMusic(@PathVariable String musicId){
        //userId를 JWT로 받아서
        Long userId=2L;
        //해당 유저의 likeList에 music을 추가한다.
        musicService.saveLike(userId,musicId);

        return ResponseEntity.ok("like Saved");
    }
    @Operation(summary = "노래에 좋아요 삭제", description = "노래에 좋아요 삭제하는 api")
    @DeleteMapping("/like/{musicId}")
    public ResponseEntity<String> deleteLikeMusic(@PathVariable String musicId){
        //userId를 JWT로 받아서
        Long userId=2L;
        //해당 유저의 likeList에 music을 삭제한다.
        musicService.deleteLike(userId,musicId);
        return ResponseEntity.ok("like Removed");
    }

    @Operation(summary = "좋아요 리스트 페이지 Api", description = "좋아요 리스트 페이지에 필요한 정보 반환(유저이름, 음악리스트)")
    @GetMapping("/like")
    public ResponseEntity<LikeRes> getLikeList(){
        //userId를 JWT로 받아서
        Long userId=2L;
        //해당 유저의 likeList를 반환해준다.
        return ResponseEntity.ok(musicService.getLikeMusicRes(userId));
    }

    @Operation(summary = "앨범 팔로우")
    @PostMapping("/follow/album/{albumId}")
    public ResponseEntity<String> addFollowAlbum(@PathVariable String albumId){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.addFollowAlbum(userId,albumId);
        return ResponseEntity.ok("Album Follow Saved");
    }
    @Operation(summary = "앨범 팔로우 목록 조히")
    @GetMapping("/follow/album")
    public ResponseEntity<Result> getFollowAlbum(){
        //userId를 JWT로 받아서
        Long userId=2L;
        List<AlbumDto> followAlbumList = musicService.getFollowAlbumList(userId);
        Result result=new Result(followAlbumList,"albumList");
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "앨범팔로우 취소")
    @DeleteMapping("/follow/album/{albumId}")
    public ResponseEntity<String> deleteFollowAlbum(@PathVariable String albumId){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.deleteFollowAlbum(userId,albumId);
        return ResponseEntity.ok("Album Follow Deleted");
    }
    @Operation(summary = "가수 팔로우")
    @PostMapping("/follow/singer/{singerId}")
    public ResponseEntity<String> addFollowSinger(@PathVariable String singerId){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.addFollowSinger(userId,singerId);
        return ResponseEntity.ok("Album Follow Saved");
    }
    @Operation(summary = "가수 팔로우 목록 조히")
    @GetMapping("/follow/singer")
    public ResponseEntity<Result> getFollowSinger(){
        //userId를 JWT로 받아서
        Long userId=2L;
        List<MusicSingerDto> followSingerList = musicService.getFollowSingerList(userId);
        Result result=new Result(followSingerList,"albumList");
        return ResponseEntity.ok(result);
    }
    @Operation(summary = "가수 팔로우 취소")
    @DeleteMapping("/follow/singer/{singerId}")
    public ResponseEntity<String> deleteFollowSinger(@PathVariable String singerId){
        //userId를 JWT로 받아서
        Long userId=2L;
        musicService.deleteFollowSinger(userId,singerId);
        return ResponseEntity.ok("Album Singer Deleted");
    }


}
