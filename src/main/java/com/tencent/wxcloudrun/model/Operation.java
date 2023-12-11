package com.tencent.wxcloudrun.model;



import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Operation implements Serializable{
    private int OID;

    private int GID;

    private String UserID;

    private int Otype;

    private LocalDateTime Otime;


    private String gdes ;
    private String iurl ;
    private float gprice ;
}
