package com.homebrewtify.demo.service;

import com.google.common.collect.Lists;
import com.homebrewtify.demo.dto.MusicDto;
import com.homebrewtify.demo.entity.Album;
import com.homebrewtify.demo.entity.Music;
import com.homebrewtify.demo.entity.Singer;
import com.homebrewtify.demo.repository.AlbumRepository;
import com.homebrewtify.demo.repository.MusicRepository;
import com.homebrewtify.demo.repository.MusicSingerRepository;
import com.homebrewtify.demo.repository.SingerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class MusicService {
    private  final AlbumRepository albumRepository;
    private final MusicRepository musicRepository;

    private final SingerRepository singerRepository;
    private final MusicSingerRepository musicSingerRepository;

    //특정 장르의 플레이리스트 여러개(9) 리턴하는 메소드
//    public List<String> getPlaylistByGenre(String genre){
//        //여기서 장르를 UpperGenre로 하고 플레이리스트에 들어가는 걸 Genre로 할 지 결정해야 함
//        //상위 장르를 장르라고 여기고 플레이리스트를 하위 장르라고 여기고 해당 플레이리스트를 고르면 해당 장르의 노래 랜덤하게 ..?
//        //근데, 홈에서 어떤 플레이리스트를 눌러서 해당 플레이리스트 재생 창으로 넘어가려면
//        //플레이리스트에 id가 있어야 한다..;
//        //그러니까 애초에 이 메소드에서는 플레이리스트들을 생성하고 저장을 해놓아야 함.(뭐 inmemory나 db에 )
//
//    }
//    //전체 장르의 플레이리스트 여러개 리턴하는 메소드
//
//    //특정 플레이리스트의 노래 목록 리턴
//
//
//
    public MusicDto.albumRes getAlbumInfo(String albumId){
        Optional<Album> optAlbum = albumRepository.findById(albumId);
        if(!optAlbum.isPresent()){
            log.error("Invalid album Id: "+albumId);
            //추후 에러 throw

            return null;
        }
        Album album=optAlbum.get();
        List<Music> musicList = musicRepository.findByAlbum(album);

        List<MusicDto.musicListDto> musicDtoList = getMusicListDtos(musicList);

        MusicDto.musicSingerDto musicSingerDto = MusicDto.musicSingerDto.builder().singerName(album.getSinger().getSingerName()).singerId(album.getSinger().getId()).build();

        MusicDto.albumRes albumRes = MusicDto.albumRes.builder().albumName(album.getAlbumName()).singer(musicSingerDto).musicList(musicDtoList).build();
        return albumRes;
    }



    public MusicDto.singerRes getSingerInfo(String singerId){
        Optional<Singer> byId = singerRepository.findById(singerId);
        if(!byId.isPresent()){
            //추후 throw err
            log.error("Invalid singer Id : "+singerId);
        }
        //해당 가수의 앨범 list
        List<Album> bySinger = albumRepository.findBySinger(byId.get());
        List<Music> musicList=new ArrayList<>();
        List<Music> popularMusicList;
        List<MusicDto.albumDto> albumDtoList=new ArrayList<>();
        log.info("singer Id:"+singerId+"albumSize:"+bySinger.size());
        //해당 가수의 앨범 list를 돌며 앨범의 노래를 musicList에 저장하고, albumDto를 list에 저장한다.
        AtomicInteger i= new AtomicInteger(1);
        bySinger.forEach(album -> {
            List<Music> byAlbum = musicRepository.findWithFeatureByAlbum(album);
            i.getAndIncrement();
            System.out.println(i+"th iter album name : "+album.getAlbumName());
            byAlbum.forEach(a->{
                System.out.println("in album : "+a.getTitle());
            });
            musicList.addAll(byAlbum);
            albumDtoList.add(MusicDto.albumDto.builder().albumId(album.getId()).albumName(album.getAlbumName()).build());
        });
        musicList.forEach(music ->{
            System.out.println("music:"+music.getTitle()+"  //  popularity:  "+music.getFeature().getPopularity());
        });
        System.out.println("--------------------------------");
        Collections.sort(musicList, new Comparator<Music>() {
            @Override
            public int compare(Music o1, Music o2) {
                return (int) (o2.getFeature().getPopularity()-o1.getFeature().getPopularity());
            }
        });
        musicList.forEach(music ->{
            System.out.println("music:  "+music.getTitle()+"  //  popularity:  "+music.getFeature().getPopularity());
        });
        List<List<Music>> partition = Lists.partition(musicList, 10);
        popularMusicList=partition.get(0);
        List<MusicDto.musicListDto> musicDtoList = getMusicListDtos(popularMusicList);

        MusicDto.singerRes build = MusicDto.singerRes.builder().singerName(byId.get().getSingerName()).musicList(musicDtoList).albumList(albumDtoList).build();
        return build;
    }

    private static List<MusicDto.musicListDto> getMusicListDtos(List<Music> musicList) {
        List<MusicDto.musicListDto> musicDtoList=new ArrayList<>();
        musicList.forEach(music -> {
            List<MusicDto.musicSingerDto> singerList=new ArrayList<>();

            music.getMusicSingerList().forEach(musicSinger->{
                MusicDto.musicSingerDto singerDto = MusicDto.musicSingerDto.builder()
                        .singerId(musicSinger.getSinger().getId()).singerName(musicSinger.getSinger().getSingerName()).build();
                singerList.add(singerDto);
            });

            MusicDto.musicListDto build = MusicDto.musicListDto.builder().trackId(music.getTrackId()).title(music.getTitle()).singerList(singerList).build();
            musicDtoList.add(build);
        });
        return musicDtoList;
    }
}
