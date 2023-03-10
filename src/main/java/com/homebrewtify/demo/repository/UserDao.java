package com.homebrewtify.demo.repository;


import com.homebrewtify.demo.dto.GetUserRes;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao { // Db 연결



    public GetUserRes getUsersByEmail(String email){
        String getUsersByEmailQuery = "select userIdx,name,nickName,email from User where email=?";
        String getUsersByEmailParams = email;
//        return this.jdbcTemplate.queryForObject(getUsersByEmailQuery,
//                (rs, rowNum) -> new GetUserRes(
//                        rs.getInt("userIdx"),
//                        rs.getString("name"),
//                        rs.getString("nickName"),
//                        rs.getString("email")),
//                getUsersByEmailParams);
        return null;
    }

}
