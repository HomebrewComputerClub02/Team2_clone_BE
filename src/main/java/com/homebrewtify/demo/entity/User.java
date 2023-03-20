package com.homebrewtify.demo.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private int userIdx;
    private String name;
    private String nickName;
    private String phone;
    private String email;
    private String password;


}
