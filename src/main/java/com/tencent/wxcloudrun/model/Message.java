package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Message implements Serializable {
    protected int mid;
    protected String senderid;
    protected String receiverid;
    protected String content;
    protected LocalDateTime mtime;
    protected int category;//0-普通消息，1-特殊消息
}
