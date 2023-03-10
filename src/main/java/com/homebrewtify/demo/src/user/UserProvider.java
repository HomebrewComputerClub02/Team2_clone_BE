package com.homebrewtify.demo.src.user;


import com.homebrewtify.demo.config.BaseException;
import com.homebrewtify.demo.src.user.model.GetUserRes;
import org.springframework.stereotype.Service;

import static com.homebrewtify.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class UserProvider {

    private final UserDao userDao;

    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public GetUserRes getUsersByEmail(String email) throws BaseException {
        try{
            GetUserRes getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
