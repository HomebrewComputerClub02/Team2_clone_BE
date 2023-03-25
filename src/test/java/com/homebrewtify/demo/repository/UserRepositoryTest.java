package com.homebrewtify.demo.repository;

import com.homebrewtify.demo.entity.User;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

//    @AfterEach
//    public void cleanup(){
//        userRepository.deleteAll();
//    }

    @Test
    public void 회원가입(){
        // given
        String nickname="spring2023";
        String birth = "2023-03-20";
        String gender="female";
        String email = "test@naver.com";
        String password = "123456";

        userRepository.save(User.builder()
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .email(email)
                .password(password)
                .build());
        // when
        List<User> userList = userRepository.findAll();

        // then
        User user = userList.get(0);
        Assertions.assertThat(user.getEmail()).isEqualTo(email);
        Assertions.assertThat(user.getNickname()).isEqualTo(nickname);
    }

    @Test
    public void 로그인(){
        // given
        String nickname="spring2023";
        String birth = "2023-03-20";
        String gender="female";
        String email = "test@naver.com";
        String password = "123456";


        userRepository.save(User.builder()
                .nickname(nickname)
                .birth(birth)
                .gender(gender)
                .email(email)
                .password(password)
                .build());

        boolean b = userRepository.existsByEmail(email);

        Assertions.assertThat(b).isEqualTo(true);

    }
}
