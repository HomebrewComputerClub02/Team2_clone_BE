package com.homebrewtify.demo.entity;


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
    private Long userId;
//    private int userIdx;
//    private String name;
    private String nickname;
//    private String phone;
    private String birth;
    private String gender;
    private String email;
    private String password;

}
