package com.homebrewtify.demo.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.homebrewtify.demo.entity.User;
import com.homebrewtify.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class OAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;


    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //MemberDetail principal = (MemberDetail) authentication.getPrincipal();
        System.out.println("OAuthSuccessHandlerCalled");

        //최초 로그인 확인 필요


        String accessToken = tokenProvider.createToken(authentication,"Access");
        String refreshToken = tokenProvider.createToken(authentication,"Refresh");
        Optional<User> optUser = userRepository.findFirstByUsername(authentication.getName());
        optUser.get().setRefreshToken(refreshToken);

        response.addHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + accessToken);
        response.addCookie(new Cookie(JwtFilter.AUTHORIZATION_HEADER, accessToken));

        String targetUrl= UriComponentsBuilder.fromUriString("http://localhost:3000/oauth2").queryParam("token",accessToken).queryParam("refresh",refreshToken).build().toUriString();//        String json=objectMapper.writeValueAsString(token);


        Cookie cookie = new Cookie("kkk", "Bearer");
        response.addCookie(cookie);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
