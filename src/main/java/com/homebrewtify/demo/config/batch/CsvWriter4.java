package com.homebrewtify.demo.config.batch;

import com.homebrewtify.demo.entity.*;
import com.homebrewtify.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Configuration;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CsvWriter4 implements ItemWriter<CsvEntity> {
    private final AlbumRepository albumRepository;
    private final GenreRepository genreRepository;
    private final MusicRepository musicRepository;
    private final SingerRepository singerRepository;
    private final FeatureRepository featureRepository;

    private final MusicSingerRepository musicSingerRepository;


    private List<Album> albumList =new ArrayList<>();
    private Map<String,Singer> singerMap =new HashMap<>();
    //singerList로 가수가 기존에 존재하던 가수인 지 확인하는 작업 만 하고, 가수 이름의 중복을 애초에 허용하지 않았으니 map

    private Map<String,Genre> genreMap=new HashMap<>();
    //얘도 장르 이름으로 구분하고 장르 이름에 중복을 허용하지 않는다.

    private Map<String,String> checkDuplicates=new HashMap<>();


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
            //중복된 row는 제거
            Optional<String> rowCheck = Optional.ofNullable(checkDuplicates.get(item.getTrackId()));
            if(!rowCheck.isPresent()){
                checkDuplicates.put(item.getTrackId(),"checked");
            }else{
                log.warn("Duplicate row");
                return;
            }

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
            iterFeatureList.add(musicFeature);

            //앨범의 주인은 첫번째 등장하는 가수만으로 한정
            String mainSinger=artists.get(0);


            Album saveAlbum;

            //메인 가수(앨범 주인)와 앨범의 관계 저장
            List<Album> sameAlbum = albumList.stream().filter(album -> album.getAlbumName().equals(albumName)).collect(Collectors.toList());
            if(sameAlbum.isEmpty()){
                Optional<Singer> findSinger = Optional.ofNullable(singerMap.get(mainSinger));
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
                    singerMap.put(buildSinger.getSingerName(),buildSinger);
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
                    Optional<Singer> findSinger = Optional.ofNullable(singerMap.get(mainSinger));
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
                        singerMap.put(buildSinger.getSingerName(),buildSinger);
                        iterSingerList.add(buildSinger);
                        saveAlbum=buildAlbum;
                    }
                }
            }



            Genre genreBuild;
            //장르 이름 중복 확인
            Optional<Genre> optGenre = Optional.ofNullable(genreMap.get(item.getTrackGenre()));
            if(!optGenre.isPresent()){
                //새로운 장르 인 경우
                genreBuild=Genre.init().genreName(item.getTrackGenre()).build();
                genreMap.put(genreBuild.getGenreName(),genreBuild);
                iterGenreList.add(genreBuild);
            }else{
                //기존 있던 장르인 경우 추가할 필요가 없으니 itergenre리스트에도 추가하면 안된다.
                genreBuild=optGenre.get();
            }
            Music buildMusic = Music.init().title(item.getTrackName()).trackId(item.getTrackId()).album(saveAlbum).feature(musicFeature).genre(genreBuild).build();
            iterMusicList.add(buildMusic);

            List<Singer> nowSingerList=new ArrayList<>();

            artists.forEach(singer->{
                Optional<Singer> opt = Optional.ofNullable(singerMap.get(singer));
                if(opt.isPresent())
                    nowSingerList.add(opt.get());
                else{
                    //item의 가수 list 중 신규 가수는 생성 후 추가한다.
                    Singer buildSinger = Singer.init().singerName(singer).build();
                    singerMap.put(buildSinger.getSingerName(), buildSinger);
                    iterSingerList.add(buildSinger);
                    nowSingerList.add(buildSinger);
                }
            });



            nowSingerList.forEach(singer -> {
                //현재 들어온 가수들에게 현재 노래를 추가한다..
                MusicSinger build = MusicSinger.init().music(buildMusic).singer(singer).build();
                //연관관계 init에서 정리하니까 ..
                iterMusicSingerList.add(build);
            });



        });




        featureRepository.saveAll(iterFeatureList);
        singerRepository.saveAll(iterSingerList);
        albumRepository.saveAll(iterAlbumList);

        if(!iterGenreList.isEmpty())
            genreRepository.saveAll(iterGenreList);
        else log.info("There is no genre for add");

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
