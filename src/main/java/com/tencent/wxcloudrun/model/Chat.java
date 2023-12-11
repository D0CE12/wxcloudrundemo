package com.tencent.wxcloudrun.model;


import lombok.Data;
import java.io.Serializable;

@Data
public class Chat implements Serializable {
    protected Message message;
    protected String nameOfCounterpart;
    protected String profilePhoto;
    protected String IDOfCounterpart;
public Chat(Message message, String userName, String userProfilePhoto,String IDOfCounterpart) {
        this.message=message;
        this.nameOfCounterpart=userName;
        this.profilePhoto=userProfilePhoto;
        this.IDOfCounterpart=IDOfCounterpart;
    }
}
