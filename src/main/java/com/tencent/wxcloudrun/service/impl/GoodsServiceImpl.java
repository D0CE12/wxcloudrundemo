package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.DealMapper;
import com.tencent.wxcloudrun.dao.GoodsMapper;
import com.tencent.wxcloudrun.dao.ImageMapper;
import com.tencent.wxcloudrun.model.Errand;
import com.tencent.wxcloudrun.model.Good;
import com.tencent.wxcloudrun.model.GoodsImage;
import com.tencent.wxcloudrun.model.IdleItem;
import com.tencent.wxcloudrun.service.GoodsService;
import com.tencent.wxcloudrun.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class GoodsServiceImpl implements GoodsService {

    final GoodsMapper goodsMapper;
    final ImageMapper imageMapper;
    final ImageService imageService;
    final DealMapper dealMapper;

    public GoodsServiceImpl(@Autowired GoodsMapper goodsMapper, @Autowired ImageMapper imageMapper, @Autowired ImageService imageService,@Autowired DealMapper dealMapper) {
        this.goodsMapper = goodsMapper;
        this.imageMapper = imageMapper;
        this.imageService = imageService;
        this.dealMapper = dealMapper;
    }

    @Override
    public Optional<Good> getGoodsDetail(Integer gid) {
        Good good = goodsMapper.getGood(gid);
        good.setGoodsImageList(goodsMapper.getGoodImage(gid));
        return Optional.of(good);
    }

    @Override
    public int insertIdleItem(IdleItem idleItem) {
        try {
            goodsMapper.insertGood(idleItem);
            goodsMapper.insertIdleItem(idleItem);
            Integer gid = idleItem.getGID();
            ArrayList<GoodsImage> goodsImages = idleItem.getGoodsImageList();
            for (GoodsImage x :
                    goodsImages) {
                x.setG_id(gid);
                imageMapper.insertGoodImage(x);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return idleItem.getGID();
    }

    @Override
    public int insertErrand(Errand errand) {
        try {
            goodsMapper.insertGood(errand);
            goodsMapper.insertErrand(errand);
            Integer gid = errand.getGID();
            ArrayList<GoodsImage> goodsImages = errand.getGoodsImageList();
            for (GoodsImage x :
                    goodsImages) {
                x.setG_id(gid);
                imageMapper.insertGoodImage(x);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return errand.getGID();
    }

    @Override
    public Optional<ArrayList<Good>> getSomeonesSellingGoods(String uid) {
        ArrayList<Good> goods=goodsMapper.getSomeonesSellingGoods(uid);
        goods.forEach((e)->{
            e.setGoodsImageList(goodsMapper.getFirstImage(e.getGID()));
        });
        return Optional.of(goods);
    }

    @Override
    public void modifyGood(Good good) {
        try {
            goodsMapper.modifyGood(good);
            if (good.getGcategory() == 0) {
                System.out.println("idle item 表不含待修改项");
            } else {
                goodsMapper.modifyErrand((Errand) good);
            }

            ArrayList<GoodsImage> originalGoodImages = goodsMapper.getGoodImage(good.getGID());
            if (!imageService.imageListsAreSame(originalGoodImages, good.getGoodsImageList())) {
                for (GoodsImage x : originalGoodImages) {
                    imageMapper.deleteGoodImage(x.getG_image_id());
                }
                for (GoodsImage y :
                        good.getGoodsImageList()) {
                    imageMapper.insertGoodImage(y);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGood(Integer gid, String openid) {
        try {
            Good good = goodsMapper.getGood(gid);
            //check if the operator is the same person as the owner of the good
            if (! openid.equals(good.getUID())) {
                throw new RuntimeException("操作人与商品所属人不一致");
            } else {
                //set good status to 3 (deleted)
                good.setStatus(3);
                goodsMapper.modifyGood(good);
                //set good end time to now
                if(good.getGcategory() == 0) {
                    goodsMapper.endGood("idle_item", gid);
                } else {
                    goodsMapper.endGood("errand", gid);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int setGoodSold(String sellerID,String buyerID,int gid) {
        goodsMapper.setGoodSold(gid);
        dealMapper.insertDeal(gid,buyerID,sellerID);
        int category=goodsMapper.getCategory(gid);
        if (category==0){
            goodsMapper.endGood("idle_item",gid);
        }else {
            goodsMapper.endGood("errand",gid);
        }

        return 1;

    }
}
