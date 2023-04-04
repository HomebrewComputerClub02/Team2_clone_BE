//package com.homebrewtify.demo.controller;
//
//import com.homebrewtify.demo.test.domain.posts.Posts;
//import com.homebrewtify.demo.test.domain.posts.PostsRepository;
//import com.homebrewtify.demo.test.dto.PostsSaveRequestDto;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.List;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//public class PostsApiControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private PostsRepository postsRepository;
//
//    @AfterEach
//    public void tearDown() throws Exception{
//        postsRepository.deleteAll();
//    }
//
//    @Test
//    public void Posts_등록된다() throws Exception{
//        // given
//        String title = "title";
//        String content = "content";
//        PostsSaveRequestDto requestDto = PostsSaveRequestDto.builder()
//                .title(title)
//                .content(content)
//                .author("author22")
//                .build();
//
//        String url ="http://localhost:"+port+"/api/v1/posts";
//
//        // when
//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,requestDto,Long.class);
//
//        // then
//        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(responseEntity.getBody()).isGreaterThan(0L);
//
//        List<Posts> all = postsRepository.findAll();
//        Assertions.assertThat(all.get(0).getTitle()).isEqualTo(title);
//        Assertions.assertThat(all.get(0).getContent()).isEqualTo(content);
//
//    }
//}
