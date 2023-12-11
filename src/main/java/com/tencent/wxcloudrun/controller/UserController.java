package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.LoginRequest;
import com.tencent.wxcloudrun.model.User;
import com.tencent.wxcloudrun.model.UserImage;
import com.tencent.wxcloudrun.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/login/get/openid")
    ApiResponse getOpenid(@RequestHeader("x-wx-openid") String openid) {
        return ApiResponse.ok(openid);
    }

    @PostMapping("/api/login")
    ApiResponse login(@RequestBody LoginRequest request, @RequestHeader("x-wx-openid") String openid) {
        if (userService.userExist(openid)) {
            return ApiResponse.ok(openid);
        } else {
            try {
                User user = new User();
                user.setUid(openid);
                user.setRole(0);
                user.setName(request.getUname());
                UserImage profilePhoto = new UserImage();
                profilePhoto.setU_id(openid);
                profilePhoto.setI_category(0); //设置图片为用户头像
                profilePhoto.setI_url(request.getProfile_image_url());
                user.setProfilePhoto(profilePhoto);
                userService.createUser(user);
                return ApiResponse.ok(openid);
            } catch (Exception e){
                e.printStackTrace();
                return ApiResponse.error("创建用户失败");
            }
        }
    }
}
