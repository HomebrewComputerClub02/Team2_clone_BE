package com.homebrewtify.demo.config.batch;

import com.homebrewtify.demo.entity.*;
import com.homebrewtify.demo.repository.*;
//import com.homebrewtify.demo.repository.jdbc.JdbcAlbumRepoSitory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CsvWriter2 implements ItemWriter<CsvEntity> {
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final MusicRepository musicRepository;
    private final SingerRepository singerRepository;
    private final FeatureRepository featureRepository;

    private final MusicSingerRepository musicSingerRepository;


    private List<Album> albumList =new ArrayList<>();
    private List<Singer> singerList =new ArrayList<>();

    //private List<MusicFeature> featureList=new ArrayList<>();

    private List<Genre> genreList=new ArrayList<>();

    private List<MusicSinger> musicSingerList=new ArrayList<>();
    private List<Music> musicList=new ArrayList<>();
    Long iter=0L;
    @Override
    public void write(List<? extends CsvEntity> items) throws Exception {
        //AtomicInteger idx= new AtomicInteger(0);

        List<Album> iterAlbumList =new ArrayList<>();
        List<Singer> iterSingerList =new ArrayList<>();
        List<MusicFeature> iterFeatureList=new ArrayList<>();
        List<Genre> iterGenreList=new ArrayList<>();
        List<MusicSinger> iterMusicSingerList=new ArrayList<>();
        List<Music> iterMusicList=new ArrayList<>();

        items.forEach(item->{

//            int i = idx.incrementAndGet();
//            System.out.println("i = " + i);


            String albumName = item.getAlbumName();
            List<String> artists = getArtistName(item.getArtists());
            //사실 여기 list보단 set이 더 빠르긴 하지만, 가수는 매 item마다 몇명안되니 pass
            //가수길이가 너무 긴 경우 그냥 저장 안하고 Pass
            if(artists.isEmpty()) {
                log.warn("No artists");
                return;
            }

            //음악 특징 저장
            MusicFeature musicFeature = MusicFeature.builder().popularity(item.getPopularity()).duration(item.getDuration()).explicit(item.getExplicit()).dance(item.getDance())
                    .energy(item.getDance()).keyValue(item.getKeyValue()).loudness(item.getLoudness()).mode(item.getMode()).speech(item.getSpeech())
                    .acoustic(item.getAcoustic()).instrument(item.getInstrument()).live(item.getLive()).valence(item.getValence())
                    .tempo(item.getTempo()).timeSignature(item.getTimeSignature()).build();
            //featureList.add(musicFeature);
            iterFeatureList.add(musicFeature);

            //앨범의 주인은 첫번째 등장하는 가수만으로 한정
            String mainSinger=artists.get(0);


            Album saveAlbum;

            //메인 가수(앨범 주인)와 앨범의 관계 저장
            List<Album> sameAlbum = albumList.stream().filter(album -> album.getAlbumName().equals(albumName)).collect(Collectors.toList());
            if(sameAlbum.isEmpty()){
                Optional<Singer> findSinger = singerList.stream().filter(singer -> singer.getSingerName().equals(mainSinger)).findAny();
                if(findSinger.isPresent()){
                    //이미 존재하는 가수
                    Album albumBuild = Album.init().albumName(albumName).singer(findSinger.get()).build();
                    albumList.add(albumBuild);
                    iterAlbumList.add(albumBuild);
                    saveAlbum=albumBuild;
                }else{
                    Singer buildSinger = Singer.init().singerName(mainSinger).build();
                    Album buildAlbum = Album.init().albumName(albumName).singer(buildSinger).build();
                    albumList.add(buildAlbum);
                    iterAlbumList.add(buildAlbum);
                    buildSinger.getAlbums().add(buildAlbum);
                    singerList.add(buildSinger);
                    iterSingerList.add(buildSinger);
                    saveAlbum=buildAlbum;
                }
            }
            else{
                Optional<Album> opt = sameAlbum.stream().filter(album -> album.getSinger().getSingerName().equals(mainSinger)).findAny();
                if(opt.isPresent()){
                    saveAlbum=opt.get();
                }else{
                    //동일한 앨범이 존재는 하나, 현재 가수의 앨범은 아닌 경우
                    //마찬가지로 현재 가수가 이미 존재하는 가수인지 신규 가수인지 판단 후 저장 필요
                    Optional<Singer> findSinger = singerList.stream().filter(singer -> singer.getSingerName().equals(mainSinger)).findAny();
                    if(findSinger.isPresent()){
                        //이미 존재하는 가수
                        Album albumBuild = Album.init().albumName(albumName).singer(findSinger.get()).build();
                        albumList.add(albumBuild);
                        iterAlbumList.add(albumBuild);
                        saveAlbum=albumBuild;
                    }else{
                        Singer buildSinger = Singer.init().singerName(mainSinger).build();
                        Album buildAlbum = Album.init().albumName(albumName).singer(buildSinger).build();
                        albumList.add(buildAlbum);
                        iterAlbumList.add(buildAlbum);
                        buildSinger.getAlbums().add(buildAlbum);
                        singerList.add(buildSinger);
                        iterSingerList.add(buildSinger);
                        saveAlbum=buildAlbum;
                    }
                }
            }



            Genre genreBuild;
            //장르 이름 중복 확인
            Optional<Genre> optGenre = genreList.stream().filter(g -> g.getGenreName().equals(item.getTrackGenre())).findAny();
            if(!optGenre.isPresent()){
                //새로운 장르 인 경우
                genreBuild=Genre.init().genreName(item.getTrackGenre()).build();
            }else{
                //기존 있던 장르인 경우
                genreBuild=optGenre.get();
            }
            Music buildMusic = Music.init().title(item.getTrackName()).trackId(item.getTrackId()).album(saveAlbum).feature(musicFeature).genre(genreBuild).build();
            //genreBuild.getMusicList().add(buildMusic); 연관관계 메소드로 처리(그냥 init에서 바로 추가)
            genreList.add(genreBuild);
            iterGenreList.add(genreBuild);
            musicList.add(buildMusic);
            iterMusicList.add(buildMusic);
            //가수 list 저장 ( 모든 가수 list에 곡 추가) (메인가수는 어차피 이미 존재하니 pass)
            artists.remove(0);
            List<Singer> nowSingerList=new ArrayList<>();

            artists.forEach(singer->{
                Optional<Singer> opt = singerList.stream().filter(s -> s.getSingerName().equals(singer)).findAny();
                if(opt.isPresent())
                    nowSingerList.add(opt.get());
                else{
                    Singer buildSinger = Singer.init().singerName(singer).build();
                    singerList.add(buildSinger);
                    iterSingerList.add(buildSinger);
                    nowSingerList.add(buildSinger);
                }
            });

            nowSingerList.forEach(singer -> {
                //현재 들어온 가수들에게 현재 노래를 추가한다..
                MusicSinger build = MusicSinger.init().music(buildMusic).singer(singer).build();
                //연관관계 init에서 정리하니까 ..
                musicSingerList.add(build);
                iterMusicSingerList.add(build);
            });



        });




        featureRepository.saveAll(iterFeatureList);
        singerRepository.saveAll(iterSingerList);
        albumRepository.saveAll(iterAlbumList);
        genreRepository.saveAll(iterGenreList);
        musicRepository.saveAll(iterMusicList);
        musicSingerRepository.saveAll(iterMusicSingerList);
    }
    private static List<String> getArtistName(String artists) {
        List<String> artistList=new ArrayList<String>();
        if(artists.length()<200){
            StringTokenizer st=new StringTokenizer(artists, ";");
            while(st.hasMoreTokens()){
                artistList.add(st.nextToken());
            }
            return artistList;

        }else return new ArrayList<>();
    }
}
