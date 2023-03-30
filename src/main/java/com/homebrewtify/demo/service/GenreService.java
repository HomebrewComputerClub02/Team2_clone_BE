package com.homebrewtify.demo.service;

import com.homebrewtify.demo.dto.GetAllGenreRes;
import com.homebrewtify.demo.dto.PlaylistCover;
import com.homebrewtify.demo.dto.UpperGenre;
import com.homebrewtify.demo.entity.*;
import com.homebrewtify.demo.repository.MusicPlaylistRepository;
import com.homebrewtify.demo.repository.MusicRepository;
import com.homebrewtify.demo.repository.PlaylistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {
    private UpperGenre[] genreLists = {new UpperGenre("포크 및 어쿠스틱", Arrays.asList("acoustic"), "#bc5900", "https://i.scdn.co/image/ab67fb8200005cafcc70a3c2e4c71398708bdc4a"),
    new UpperGenre("국가별", Arrays.asList("afrobeat", "brazil", "british", "french", "german", "iranian", "malay", "mandopop", "swedish", "turkish", "indian"), "#e8115b", "https://t.scdn.co/images/37732285a0ff4e24987cdf5c45bdf31f.png"),
    new UpperGenre("록", Arrays.asList("alt-rock","garage", "goth", "hard-rock", "hardcore", "hardstyle", "industrial", "psych-rock", "punk-rock", "rock-n-roll", "rock", "rockabilly"),"#e91429","https://i.scdn.co/image/ab67fb8200005cafae7e69beb88f16969641b53e"),
    new UpperGenre("얼터너티브", Arrays.asList("alternative"),"#e91429", "https://i.scdn.co/image/ab67fb8200005cafda178a834e4f87371e9fa543"),
    new UpperGenre("댄스/일렉트로닉", Arrays.asList("ambient", "breakbeat", "chicago-house", "club", "dance", "dancehall", "detroit-techno", "drum-and-bass", "edm", "elctro", "electronic", "idm", "minimal-techno", "progressive-house", "techno", "trance"),"#d84000", "https://i.scdn.co/image/ab67fb8200005cafdfdaac1cf9574a196ca25196"),
    new UpperGenre("애니메이션", Arrays.asList("animation", "disney"),"#e41d63","https://i.scdn.co/image/ab67706f00000002c19c5f13f8b3ff2d73ff00bc"),
    new UpperGenre("메탈", Arrays.asList("black-metal", "death-metal", "emo", "grindcore", "heavy-metal", "metal", "metalcore"),"#e91429", "https://i.scdn.co/image/ab67fb8200005cafefa737b67ec51ec989f5a51d"),
    new UpperGenre("컨트리", Arrays.asList("bluegrass", "country", "folk"),"#d84000","https://i.scdn.co/image/ab67fb8200005cafc0d2222b4c6441930e1a386e"),
    new UpperGenre("블루스", Arrays.asList("blues"),"#b06239","https://i.scdn.co/image/ab67fb8200005caff22ac5cab318d550b593ffac"),
    new UpperGenre("팝", Arrays.asList("pop","cantopop","power-pop", "ska", "synth-pop", "world-music"),"#148a08","https://i.scdn.co/image/ab67fb8200005cafa862ab80dd85682b37c4e768"),
    new UpperGenre("어린이 및 가족", Arrays.asList("children", "kids", ""),"#8d67ab","https://i.scdn.co/image/ab67fb8200005caf8a04560a209b3f32165ea8a2"),
    new UpperGenre("휴식/집중", Arrays.asList("chill","sleep", "study"),"#d84000", "https://i.scdn.co/image/ab67fb8200005caf47e942f5bea637f4f4760170"),
    new UpperGenre("클래식", Arrays.asList("classical"),"#7d4b32", "https://i.scdn.co/image/ab67fb8200005caf12809992dfc5b318892ea07b"),
    new UpperGenre("무드", Arrays.asList("comedy", "groove", "happy", "romance", "sad", "party"), "#e1118c", "https://i.scdn.co/image/ab67fb8200005caf271f9d895003c5f5561c1354"),
    new UpperGenre("소울", Arrays.asList("deep-house", "soul"),"#dc148c", "https://i.scdn.co/image/ab67fb8200005cafd82e2c83fe100a89e9cbb2a2"),
    new UpperGenre("래게", Arrays.asList("reggae","reggaeton","dub", "dubstep"),"#006450","https://i.scdn.co/image/ab67fb8200005caf6ba3a99776446ab3b295d2a6"),
    new UpperGenre("펑크 및 디스코", Arrays.asList("disco", "funk", "house", "punk"),"#e61e32", "https://i.scdn.co/image/ab67fb8200005cafbb0e4ea229824157eee7467d"),
    new UpperGenre("라틴", Arrays.asList("latin","latino","forro", "mpb", "pagode", "salsa", "samba", "sertanejo", "tango"),"#e1118c", "https://i.scdn.co/image/ab67fb8200005cafa59f90c077c9f618fd0dde30"),
    new UpperGenre("크리스천 및 가스펠", Arrays.asList("gospel"),"#0d73ec", "https://i.scdn.co/image/ab67fb8200005cafc237e034906c45e2920a54cb"),
    new UpperGenre("연주곡", Arrays.asList("guitar", "new-age", "piano", ""), "#537aa1", "https://i.scdn.co/image/ab67fb8200005cafc237e034906c45e2920a54cb"),
    new UpperGenre("힙합", Arrays.asList("hip-hop", "trip-hop"), "#bc5900", "https://i.scdn.co/image/ab67fb8200005caf7e11c8413dc33c00740579c1"),
    new UpperGenre("재즈", Arrays.asList("jazz","honky-tonk"), "#777777", "https://i.scdn.co/image/ab67fb8200005cafe289743024639ea8f202364d"),
    new UpperGenre("인디", Arrays.asList("indie","indie-pop"), "#e91429", "https://i.scdn.co/image/ab67fb8200005cafa1a252e3a815b65778d8c2aa"),
    new UpperGenre("일본트랙", Arrays.asList("j-dance", "j-idol", "j-pop", "j-rock"), "#bc5900", "https://i.scdn.co/image/ab67fb8200005cafa3fc0b1839678a0efd6c4302"),
    new UpperGenre("가요", Arrays.asList("k-pop"), "#2d46b9", "https://i.scdn.co/image/ab67fb8200005caf5a7a37ae85c166fb90eb9b5d"),
    new UpperGenre("TV 및 영화", Arrays.asList("pop-film", "show-tunes"), "#af2896", "https://i.scdn.co/image/ab67fb8200005cafb4c4523336133ec3c7fd1744"),
    new UpperGenre("R&B", Arrays.asList("r-n-b"), "#dc148c", "https://i.scdn.co/image/ab67fb8200005cafbe6a6e705e1a71117c2d0c2c"),
    new UpperGenre("작곡가/작사가", Arrays.asList("singer-songwriter", "songwriter"), "#8c1932", "https://i.scdn.co/image/ab67fb8200005cafb973ab1288f74f333e7e2e22"),
    };

    private final PlaylistRepository playlistRepository;
    private final MusicPlaylistRepository musicPlaylistRepository;
    private final MusicRepository musicRepository;
    public GetAllGenreRes[] getAllGenre(){
        return Arrays.stream(genreLists)
                .map(g->new GetAllGenreRes(g.getName(), g.getColor(), g.getImgUrl()))
                .collect(Collectors.toList())
                .toArray(new GetAllGenreRes[0]);
    }

    public List<PlaylistCover> getPlaylistCoversByGenre(String genre){
        List<String> genres = null;
        List<PlaylistCover> result = new ArrayList<>();

        //uppergenre의 하위장르들 모두 genres에 저장하기
        for (UpperGenre ele : genreLists) {
            if(ele.getName().equals(genre)){
                genres = ele.getGenres();
                break;
            }
        }

        /*
        하위장르들에 대한 playlist가 이미 db에 저장되어있는지를 확인하고 저장되어있으면 조회해서 플레이리스트를 result에 저장하고
        저장되어있지 않으면 플레이리스트를 생성하고 db에 저장한다.
         */
        for(String gen : genres){
            String coverImgUrl;
            List<Playlist> genPlaylist = playlistRepository.findByUser_UserIdAndName(null, gen);
            Playlist pl;
            if(!genPlaylist.isEmpty()){
                pl = genPlaylist.get(0);
            }else{
                //플레이리스트 생성후 db에 저장
                //1. save tb_playlist
                pl = Playlist.builder()
                            .user(null)
                            .name(gen)
                            .build();
                playlistRepository.save(pl);

                //2. find music_list
                List<Music> musicList = musicRepository.findFirst10ByGenre_GenreName(gen);
                List<MusicPlaylist> musicPlaylists = new ArrayList<MusicPlaylist>();


                for (Music m: musicList) {
                    musicPlaylists.add(MusicPlaylist.builder()
                            .music(m)
                            .playlist(pl)
                            .build());
                }

                //3. 결과값 db에 저장
                musicPlaylistRepository.saveAll(musicPlaylists);

            }

            //플레이리스트에 포함된 첫번째 곡의 앨범이미지를 대표이미지로 함
            coverImgUrl = musicPlaylistRepository.findFirstByPlaylist_Id(pl.getId()).map(MusicPlaylist:: getMusic).map(Music :: getAlbum).map(Album::getImgUrl).orElse(null);

            //조회된 플레이리스트를 result에 저장
            result.add(new PlaylistCover(pl.getId(), pl.getName(), coverImgUrl));

        }



        return result;
    }

    public String getRandomUpperGenre(String... passGenre){
        Random random=new Random();
        random.setSeed(System.currentTimeMillis());
        List<String> nameList = Arrays.stream(genreLists).map(UpperGenre::getName).collect(Collectors.toList());
        nameList.removeIf(name-> Arrays.stream(passGenre).anyMatch(pass->pass.equals(name)));

        int idx = random.nextInt(nameList.size()-1);
        return nameList.get(idx);
    }

}