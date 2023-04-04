package com.homebrewtify.demo.service;

import com.homebrewtify.demo.entity.MemberDetail;
import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.entity.userInfo.GoogleUserInfo;
import com.homebrewtify.demo.entity.userInfo.NaverUserInfo;
import com.homebrewtify.demo.entity.userInfo.OAuthUserInfo;
import com.homebrewtify.demo.repository.UserRepository;
import com.homebrewtify.demo.utils.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Transactional
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser (OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("OAuth Load User called");
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuthUserInfo oAuthUserInfo=null;
        String provider = userRequest.getClientRegistration().getRegistrationId();    // google or naver ...
        if(provider.equals("google")){
            oAuthUserInfo=new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(provider.equals("naver")){
            oAuthUserInfo=new NaverUserInfo(oAuth2User.getAttributes());
        }
        String providerId = oAuthUserInfo.getProviderId();
        String username = provider+"_"+providerId;  			// 사용자가 입력한 적은 없지만 만들어준다

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("패스워드"+uuid);  // 사용자가 입력한 적은 없지만 만들어준다
        //String password="dsfsdf";
        String email = oAuthUserInfo.getEmail();

        Optional<User> opt = userRepository.findFirstByUsername(username);

        if(opt.isPresent()){
            //기 가입자
            return new MemberDetail(opt.get(),oAuthUserInfo);
        }else{
            User build = User.builder().username(username).password(password)
                    .role(Role.USER)
                    .build();
            userRepository.save(build);
            return new MemberDetail(build, oAuthUserInfo);
        }

    }

}
