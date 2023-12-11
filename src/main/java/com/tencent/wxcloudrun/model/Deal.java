package com.tencent.wxcloudrun.model;

import lombok.Data;

@Data
public class Deal {
    private int id;
    private int goodID;
    private String gdes;
    private float gprice;

    private String buyerID;
    private String sellerID;
    private String iurl;
}
