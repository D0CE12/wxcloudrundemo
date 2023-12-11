package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String uid;
    private String campus;
    private String major;
    private String grade;
    private Integer role;
    private String name;
    private UserImage profilePhoto;
}
