package com.homebrewtify.demo.service;

import com.google.common.collect.Lists;
import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.config.BaseResponseStatus;
import com.homebrewtify.demo.entity.*;
import com.homebrewtify.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.homebrewtify.demo.dto.MusicDto.*;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MusicService {
    private final PlaylistRepository playlistRepository;
    private final UserRepository userRepository;
    private  final AlbumRepository albumRepository;
    private final MusicRepository musicRepository;

    private final SingerRepository singerRepository;
    private final MusicSingerRepository musicSingerRepository;

    private final PlayRecordRepository playRecordRepository;

    private final LikeMusicRepository likeMusicRepository;

    private final FollowAlbumRepository followAlbumRepository;
    private final FollowSingerRepository followSingerRepository;

    public AlbumRes getAlbumInfo(String albumId){
        Album album=getAlbum(albumId);
        List<Music> musicList = musicRepository.findByAlbum(album);

        List<MusicListDto> musicDtoList = getMusicListDtos(musicList);

        MusicSingerDto musicSingerDto = MusicSingerDto.builder().singerName(album.getSinger().getSingerName()).singerId(album.getSinger().getId()).build();

        AlbumRes albumRes = AlbumRes.builder().albumName(album.getAlbumName()).albumSinger(musicSingerDto).musicList(musicDtoList).build();
        return albumRes;
    }



    public SingerRes getSingerInfo(String singerId){
        Singer singer = getSinger(singerId);
        //해당 가수의 앨범 list
        List<Album> bySinger = albumRepository.findBySinger(singer);
        List<Music> musicList=new ArrayList<>();
        List<Music> popularMusicList;
        List<AlbumDto> albumDtoList=new ArrayList<>();
        bySinger.forEach(album -> {
            List<Music> byAlbum = musicRepository.findWithFeatureByAlbum(album);
            musicList.addAll(byAlbum);

            albumDtoList.add(AlbumDto.builder().albumId(album.getId()).albumName(album.getAlbumName())
                    .singerName(album.getSinger().getSingerName()).singerId(album.getSinger().getId()).build());
        });
        Collections.sort(musicList, new Comparator<Music>() {
            @Override
            public int compare(Music o1, Music o2) {
                return (int) (o2.getFeature().getPopularity()-o1.getFeature().getPopularity());
            }
        });
        List<List<Music>> partition = Lists.partition(musicList, 10);
        popularMusicList=partition.get(0);
        List<MusicListDto> musicDtoList = getMusicListDtos(popularMusicList);


        //참여 앨범 찾기 (본인 앨범 제외)
        List<AlbumDto> joinAlbumBySinger = getJoinAlbumBySinger(singer);
        SingerRes build = SingerRes.builder().singerName(singer.getSingerName())
                .musicList(musicDtoList).albumList(albumDtoList)
                .joinAlbumList(joinAlbumBySinger).build();
        return build;
    }

    private Singer getSinger(String singerId)  {
        Singer byId = singerRepository.findById(singerId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_SINGER_ID));
        return byId;
    }


    public List<AlbumDto> getJoinAlbumBySinger(Singer singer) {
        List<MusicSinger> bySinger = musicSingerRepository.findWithMusicBySinger(singer);
        //조회 대상이 없으면 error
        Singer withAlbumsById = singerRepository.findWithAlbumsById(singer.getId())
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_SINGER_ID));

        Map<String, String> albumIdMap=new HashMap<>();
        withAlbumsById.getAlbums().forEach(a->
            albumIdMap.put(a.getId(),a.getAlbumName())
        );

        List<AlbumDto> albumDtoList=new ArrayList<>();
        bySinger.stream().forEach(ms->{
            Album album = ms.getMusic().getAlbum();
            if(albumIdMap.get(album.getId())==null){
                //album response 만들어서 list에 저장
                AlbumDto build = AlbumDto.builder().albumId(album.getId()).albumName(album.getAlbumName())
                        .singerId(album.getSinger().getId()).singerName(album.getSinger().getSingerName())
                        .build();
                albumDtoList.add(build);
            }
        });
        return albumDtoList;
    }

    private static List<MusicListDto> getMusicListDtos(List<Music> musicList) {
        List<MusicListDto> musicDtoList=new ArrayList<>();
        musicList.forEach(music -> {
            List<MusicSingerDto> singerList=new ArrayList<>();

            music.getMusicSingerList().forEach(musicSinger->{
                MusicSingerDto singerDto = MusicSingerDto.builder()
                        .singerId(musicSinger.getSinger().getId()).singerName(musicSinger.getSinger().getSingerName()).build();
                singerList.add(singerDto);
            });

            MusicListDto build = MusicListDto.builder().trackId(music.getTrackId()).title(music.getTitle()).singerList(singerList).build();
            musicDtoList.add(build);
        });
        return musicDtoList;
    }

    @Transactional
    public PlayRecord saveRecentPlay(String type, Long userId, String dataId){
        User user = getUser(userId);
        PlayType pType;
        if(type.equals("SONG")){
            pType=PlayType.SONG;
        }else if(type.equals("SINGER")){
            pType=PlayType.SINGER;
        }else if(type.equals("ALBUM")){
            pType=PlayType.ALBUM;
        } else pType=PlayType.PLAYLIST;


        List<PlayRecord> playRecentList = playRecordRepository.findAllByUserOrderByPlayDate(user);
        for (PlayRecord playRecord : playRecentList) {
            if(playRecord.getType().equals(pType)&&playRecord.getDataId().equals(dataId)){
                log.info("Found duplicated Play Record will touch time");
                //현재 저장할 PLay가 이미 존재하므로 시간만 touch하고 return
                playRecord.setPlayDate(LocalDateTime.now());
                return playRecord;
            }
        }

        //만약 현재 play가 중복되지 않았다면 지정한 갯수(5개)를 초과하는지 체크하고 초과 시 FIFO
        log.info("user's recent count:"+playRecentList.size());
        if(playRecentList.size()>4){
            for(int i=0;i<playRecentList.size()-4;i++){
                log.info("Will delete");
                playRecordRepository.delete(playRecentList.get(i));
            }
        }

        PlayRecord build = PlayRecord.create().user(user).playDate(LocalDateTime.now()).type(pType).dataId(dataId).build();
        return playRecordRepository.save(build);
    }

    private User getUser(Long userId) {
        User byId = userRepository.findById(userId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_USER_ID));
        return byId;
    }
    private Album getAlbum(String albumId) {
        Album byId = albumRepository.findById(albumId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_ALBUM_ID));
        return byId;
    }
    private Music getMusic(String musicId) {
        Music byId = musicRepository.findById(musicId)
                .orElseThrow(()->new BaseException(BaseResponseStatus.INVALID_MUSIC_ID));
        return byId;
    }
    @Transactional
    public HomeRes getRecentPlayListByUser(Long userId){
        User user = getUser(userId);

        List<PlayRecord> allByUser = playRecordRepository.findAllByUser(user);


        HomeRes res=new HomeRes();
        List<Result> resultList = res.getResultList();

        List<String> invalidList=new ArrayList<>();

        for (PlayRecord playRecord : allByUser) {
            Result result;

            //각 타입에 맞게 result 설정
            if (playRecord.getType().equals(PlayType.SONG)) {
                Optional<Music> opt = musicRepository.findById(playRecord.getDataId());
                if (!opt.isPresent()) {
                    //해당 id의 데이터가 사라진 경우 삭제 리스트에 추가
                    invalidList.add(playRecord.getId());
                    continue;
                } else {
                    //하나의 음악만 dto로 만들었으니 해당 리스트의 크기는 무조건 1이어야 한다. (나중에 테스트 시 포함)
                    List<MusicListDto> musicListDtos = getMusicListDtos(Collections.singletonList(opt.get()));
                    result = new Result(musicListDtos.get(0),"SONG");
                }
            } else if (playRecord.getType().equals(PlayType.SINGER)) {
                //TODO : 가수로 바로 재생하는 부분 구현 후 수정 체크 필요
                //가수 이름, img, 가수 id 필요
                Optional<Singer> opt = singerRepository.findById(playRecord.getDataId());
                if(!opt.isPresent()){
                    //해당 id의 데이터가 사라진 경우 삭제 리스트에 추가
                    invalidList.add(playRecord.getId());
                    continue;
                }else{
                    Singer singer=opt.get();
                    MusicSingerDto build = MusicSingerDto.builder().singerId(singer.getId())
                            .singerName(singer.getSingerName()).build();
                    result=new Result(build,"SINGER");
                }
            } else if (playRecord.getType().equals(PlayType.ALBUM)){
                Optional<Album> opt = albumRepository.findById(playRecord.getDataId());
                if(!opt.isPresent()){
                    //해당 id의 데이터가 사라진 경우 삭제 리스트에 추가
                    invalidList.add(playRecord.getId());
                    continue;
                }else{
                    Album album=opt.get();
                    AlbumDto build = AlbumDto.builder().albumId(album.getId()).albumName(album.getAlbumName())
                            .singerName(album.getSinger().getSingerName()).singerId(album.getSinger().getId()).build();
                    result=new Result(build,"ALBUM");
                }
            }
            else {
                //TODO : 플레이리스트 구현 완료 후 이 부분 테스트 필요
                Optional<Playlist> opt = playlistRepository.findById(playRecord.getDataId());
                if(!opt.isPresent()){
                    //해당 id의 데이터가 사라진 경우 삭제 리스트에 추가
                    invalidList.add(playRecord.getId());
                    continue;
                }else{
                    //플레이리스트 이름, 플레이리스트 id, img 정도 넣은 DTO 만들어서 result에 할당
                    result=new Result("TmpPlayList","PLAYLIST");
                }
            }

            resultList.add(result);

        }
        //데이터가 사라진 record 삭제
        playRecordRepository.deleteAllById(invalidList);

        return res;
    }

    @Transactional
    public LikeMusic saveLike(Long userId, String musicId) {
        User user=getUser(userId);
        Music music = getMusic(musicId);
        //중복 저장 시 error (이미 있으면 error)
        likeMusicRepository.findByMusicAndUser(music, user)
                .ifPresent(a->{throw new BaseException(BaseResponseStatus.INVALID_SAVE_ATTEMPT);});


        LikeMusic likeMusic = new LikeMusic();
        likeMusic.setUser(user);
        likeMusic.setMusic(music);
        likeMusic.setLikeDate(LocalDateTime.now());
        return likeMusicRepository.save(likeMusic);

    }
    @Transactional
    public void deleteLike(Long userId, String musicId){
        User user=getUser(userId);
        Music music = getMusic(musicId);
        likeMusicRepository.deleteByUserAndMusic(user, music);
    }
    public LikeRes getLikeMusicRes(Long userId){
        User user=getUser(userId);

        List<LikeMusic> withMusicByUser = likeMusicRepository.findWithMusicByUser(user);
        LikeRes likeRes=new LikeRes();
        likeRes.setUserName(user.getNickname());


        List<MusicListDto> musicListDtoList=new ArrayList<>();
        withMusicByUser.forEach(likeMusic->{

            //TODO: 지연로딩 발생할 지점 ( 나중에 네이티브 쿼리를 짜든 해야 할 듯..)
            List<MusicSinger> musicSingerList = likeMusic.getMusic().getMusicSingerList();

            List<MusicSingerDto> msDtoList=new ArrayList<>();
            musicSingerList.forEach(musicSinger ->
                msDtoList.add(MusicSingerDto.builder()
                        .singerName(musicSinger.getSinger().getSingerName()).singerId(musicSinger.getSinger().getId())
                        .build()
                )
            );


            Long seconds = Duration.between(likeMusic.getLikeDate(), LocalDateTime.now()).getSeconds();
            MusicListDto build = MusicListDto.builder().trackId(likeMusic.getMusic().getTrackId())
                    .title(likeMusic.getMusic().getTitle()).seconds(seconds).singerList(msDtoList).build();
            musicListDtoList.add(build);
        });
        likeRes.setMusicList(musicListDtoList);
        return likeRes;
    }

    @Transactional
    public FollowAlbum addFollowAlbum(Long userId, String albumId){
        User user=getUser(userId);
        Album album = getAlbum(albumId);

        //이미 팔로우 한 앨범을 다시 팔로우 하는 경우 error
        user.getFollowAlbumList().stream().filter(followAlbum -> followAlbum.getAlbum().equals(album))
                .findAny().ifPresent(a->{throw  new BaseException(BaseResponseStatus.INVALID_SAVE_ATTEMPT);});


        FollowAlbum follow=new FollowAlbum();
        follow.createFollowAlbum(user,album);
        return followAlbumRepository.save(follow);
    }
    @Transactional
    public FollowSinger addFollowSinger(Long userId, String singerId){
        User user=getUser(userId);
        Singer singer = getSinger(singerId);
        //이미 팔로우 한 가수를 다시 팔로우 하는 경우
        user.getFollowSingerList().stream().filter(followSinger ->
                followSinger.getSinger().equals(singer)).findAny()
                .ifPresent(a->{throw new BaseException(BaseResponseStatus.INVALID_SAVE_ATTEMPT);});


        FollowSinger follow=new FollowSinger();
        follow.createFollowSinger(user,singer);
        return followSingerRepository.save(follow);
    }
    public List<AlbumDto> getFollowAlbumList(Long userId){
        User user = getUser(userId);
        List<FollowAlbum> followAlbumList = user.getFollowAlbumList();
        List<AlbumDto> albumDtoList=new ArrayList<>();
        followAlbumList.stream().forEach(followAlbum -> {
            AlbumDto build = AlbumDto.builder().albumId(followAlbum.getAlbum().getId()).albumName(followAlbum.getAlbum().getAlbumName())
                    .singerId(followAlbum.getAlbum().getSinger().getId())
                    .singerName(followAlbum.getAlbum().getSinger().getSingerName()).build();
            albumDtoList.add(build);
        });

        return albumDtoList;
    }
    public List<MusicSingerDto> getFollowSingerList(Long userId){
        User user = getUser(userId);
        List<FollowSinger> followSingerList = user.getFollowSingerList();
        List<MusicSingerDto> singerDtoList=new ArrayList<>();

        followSingerList.stream().forEach(followSinger -> {
            MusicSingerDto build = MusicSingerDto.builder().singerId(followSinger.getSinger().getId())
                    .singerName(followSinger.getSinger().getSingerName()).build();
            singerDtoList.add(build);
        });

        return singerDtoList;
    }
    @Transactional
    public void deleteFollowAlbum(Long userId,String albumId){
        User user=getUser(userId);
        Album album = getAlbum(albumId);
        List<FollowAlbum> collect = user.getFollowAlbumList().stream().filter(followAlbum -> followAlbum.getAlbum().equals(album))
                .collect(Collectors.toList());
        user.getFollowAlbumList().removeAll(collect);
        followAlbumRepository.deleteAll(collect);
    }
    @Transactional
    public void deleteFollowSinger(Long userId,String singerId){
        User user=getUser(userId);
        Singer singer = getSinger(singerId);
        List<FollowSinger> collect = user.getFollowSingerList().stream().filter(followSinger -> followSinger.getSinger().equals(singer))
                .collect(Collectors.toList());
        user.getFollowSingerList().removeAll(collect);
        followSingerRepository.deleteAll(collect);
    }
}
