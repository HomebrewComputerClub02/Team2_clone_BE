package com.homebrewtify.demo.config.batch;

import com.homebrewtify.demo.entity.Album;
import com.homebrewtify.demo.entity.CsvEntity;
import com.homebrewtify.demo.entity.Singer;
import com.homebrewtify.demo.entity.SingerAlbum;
import com.homebrewtify.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CsvWriter implements ItemWriter<CsvEntity> {

    private final CsvRepository csvRepository;
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final MusicRepository musicRepository;
    private final SingerRepository singerRepository;
    private final FeatureRepository featureRepository;
    private final SingerAlbumRepository singerAlbumRepository;

    @Override
    public void write(List<? extends CsvEntity> list) throws Exception {
//        System.out.println("Called Writer");
//        csvRepository.saveAll(new ArrayList<CsvEntity>(list));
        list.forEach(csvEntity -> {
            //앨범 관련 정보 저장
            Album album;
            Optional<Album> byAlbumName = albumRepository.findByAlbumName(csvEntity.getAlbumName());
            boolean albumFlag = false;
            if(byAlbumName.isPresent()){
                album=byAlbumName.get();
                albumFlag =true;
            }

            else {
                //발매일이나 이미지 주소에 대한 정보가 없음
                Album build = Album.builder().albumName(csvEntity.getAlbumName()).build();
                album=albumRepository.save(build);
            }

            //가수 관련 정보 저장
            String artists = csvEntity.getArtists();
            if(artists.length()>250){
                StringTokenizer st=new StringTokenizer(artists, ";");
                //가수가 너무 많은 노래가 존재하기에 가수는 10명까지만 저장?
                //이름이 긴 가수가 10명이고 이게 255넘으면 안됨 ..
                int cnt=0;
                String newArtists="";
                while(st.hasMoreTokens()||cnt<10){
                    String nextToken = st.nextToken();
                    if(newArtists.length()+nextToken.length()>250){
                        //다음 가수를 추가하면 overflow인 경우
                        break;
                    }
                    newArtists+=nextToken+";";
                    cnt++;
                }
                artists=newArtists;

            }
            //어떤 곡을 여러 가수가 불렀다면 .. 각 가수의 노래라고 여기면 안된다 ?
            Optional<Singer> optSinger = singerRepository.findWithAlbumsBySingerName(artists);
            Singer singer;
            if(optSinger.isPresent()){
                //해당 가수가 이미 DB에 존재
                if(albumFlag){
                    // 앨범도 이미 존재한다면 수정 할 필요가 없다.
                }else{
                    // 가수는 존재하지만 앨범이 존재하지 않는 경우
                    SingerAlbum build = SingerAlbum.builder().album(album).singer(optSinger.get()).build();
                    singerAlbumRepository.save(build);
                }
            }else{
                //신규 가수인 경우 앨범도 처음 생성되었으므로 체크할 필요 없음
                if(albumFlag){
                    log.error("New singer But Album is already exist");
                }else{
                    //마찬가지로 이미지 url이 없다.
                    singer=Singer.builder().singerName(artists).build();
                    singerRepository.save(singer);
                    SingerAlbum build = SingerAlbum.builder().singer(singer).album(album).build();
                    singerAlbumRepository.save(build);
                }
            }


        });

    }
}