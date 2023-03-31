package com.homebrewtify.demo.entity;


import com.homebrewtify.demo.utils.Role;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id //pk
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; // 필수
//    private int userIdx;
//    private String name;
    private String nickname;
//    private String phone;
    private String birth;
    private String gender;

    @Column(nullable = false)
    private String email; // 필수

    private String password;

    private String picture;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User update(String nickname,String picture){
        this.nickname = nickname;
        this.picture = picture;
        return this;
    }
    public String getRoleKey(){
        return this.role.getKey();
    }
}
