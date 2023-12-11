package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.User;

public interface UserService {
    boolean userExist(String openid);

    void createUser(User user);
}
