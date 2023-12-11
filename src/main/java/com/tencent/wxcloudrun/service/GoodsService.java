package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Errand;
import com.tencent.wxcloudrun.model.Good;
import com.tencent.wxcloudrun.model.IdleItem;

import java.util.ArrayList;
import java.util.Optional;


public interface GoodsService {
    Optional<Good> getGoodsDetail(Integer gid);

    int insertIdleItem(IdleItem idleItem);

    int insertErrand(Errand errand);

    Optional<ArrayList<Good>> getSomeonesSellingGoods(String uid);

    void modifyGood(Good good);


    void deleteGood(Integer gid, String openid);

    int setGoodSold(String sellerID,String buyerID,int gid);

}
