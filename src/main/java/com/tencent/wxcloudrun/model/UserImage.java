package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserImage implements Serializable {
    protected Integer u_image_id;
    protected String u_id;
    protected String i_url;
    protected Integer i_category;
}
