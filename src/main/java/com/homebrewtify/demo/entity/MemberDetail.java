package com.homebrewtify.demo.entity;

import com.homebrewtify.demo.entity.userInfo.OAuthUserInfo;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@ToString
@Slf4j
public class MemberDetail implements UserDetails, OAuth2User {
    public User getUser() {
        return user;
    }

    private User user;
    public MemberDetail(User user) {
        this.user = user;
    }
    public MemberDetail(User user, OAuthUserInfo oAuthUserInfo) {
        this.user = user;
        this.oAuthUserInfo=oAuthUserInfo;
    }
    private OAuthUserInfo oAuthUserInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }
        });
//        log.info("user info : {} {} {}",user.getUserId(),user.getNickname(),user.getClass());
//        log.info("getAuthorities class : {}",user.getAuthorities().getClass());
//        user.getAuthorities()
//                .forEach(authority -> {
//            collect.add(new SimpleGrantedAuthority(authority.getAuthorityName()));
//        });

        return collect;
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuthUserInfo.getAttributes();
    }

    @Override
    public String getName() {
        return oAuthUserInfo.getProviderId();
    }

    @Override
    public int hashCode() {
        return this.getUsername().hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof MemberDetail){
            return this.getUsername().equals(((MemberDetail)obj).getUsername());
        }
        return false;
    }
}
