package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.GoodsImage;

import java.util.ArrayList;

public interface ImageService {

    boolean imageListsAreSame(ArrayList<GoodsImage> originalGoodImages, ArrayList<GoodsImage> goodsImageList);
}
