package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.model.GoodsImage;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Data
public class ModifyRequest implements Serializable {
    protected Integer gid;
    protected String description;
    protected Integer campus;
    protected Integer status;
    protected String uid;
    protected Float price;
    protected Integer category;
    protected ArrayList<GoodsImage> goodsImages;
    protected LocalDateTime deadline;
}
