package com.tencent.wxcloudrun.dto;

import lombok.Data;


import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MessageRequest implements Serializable {
    private String senderid;
    private String receiverid;
    private String content;
    private LocalDateTime mtime;
    private int category;


}
