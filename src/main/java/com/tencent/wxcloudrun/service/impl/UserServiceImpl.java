package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final UserMapper userMapper;

    public UserServiceImpl(@Autowired UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public boolean userExist(String openid) {
        String uid = userMapper.getUID(openid);
        if(uid == null || uid.isEmpty()){
            return false;
        }
        return uid.equals(openid);
    }

    @Override
    public void createUser(User user) {
        try{
            userMapper.createUser(user);
            userMapper.insertUserProfilePhoto(user.getProfilePhoto());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
