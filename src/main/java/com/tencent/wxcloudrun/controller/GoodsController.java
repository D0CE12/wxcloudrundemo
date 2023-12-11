package com.tencent.wxcloudrun.controller;


import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.ModifyRequest;
import com.tencent.wxcloudrun.dto.ReleaseRequest;
import com.tencent.wxcloudrun.model.Errand;
import com.tencent.wxcloudrun.model.Good;
import com.tencent.wxcloudrun.model.IdleItem;
import com.tencent.wxcloudrun.service.GoodsService;
import com.tencent.wxcloudrun.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class GoodsController {

    final GoodsService goodsService;
    final UserService userService;
    final Logger logger;

    public GoodsController(@Autowired GoodsService goodsService, @Autowired UserService userService) {
        this.goodsService = goodsService;
        this.userService = userService;
        this.logger = LoggerFactory.getLogger(CounterController.class);
    }

    @GetMapping("/api/goods/get/{gid}")
    public ApiResponse getDetails(@PathVariable Integer gid) {
        Optional<Good> details = goodsService.getGoodsDetail(gid);
        if (details.isPresent()) {
            return ApiResponse.ok(details); // 如果成功获取到商品详情，则返回成功的响应
        } else {
            return ApiResponse.error("Product details not found for GID: " + gid); // 商品详情未找到的错误响应
        }
    }

    //@RequestBody解析json注入对象这一过程对对象的属性名命名十分严格，建议改成全部小写并以下划线分隔单词（前两个字母不能1小2大）
    @PostMapping("/api/goods/release")
    ApiResponse releaseGood(@RequestBody ReleaseRequest request, @RequestHeader("x-wx-openid") String openID) {
        logger.info("/api/goods/release POST request");
        logger.info(request.toString());

        if (request.getCategory() == 0) {
            IdleItem idleItem = new IdleItem();
            idleItem.setGdes(request.getDescription());
            idleItem.setGprice(request.getPrice());
            idleItem.setGcampus(request.getCampus());
            idleItem.setGoodsImageList(request.getImageList());
            idleItem.setGcategory(0);
            idleItem.setStatus(1);
            idleItem.setUID(openID);

            try {
                int goodsId = goodsService.insertIdleItem(idleItem);
                return ApiResponse.ok(goodsId);
            } catch (Exception e) {
                e.printStackTrace();
                return ApiResponse.error("发布失败");
            }
        } else {
            Errand errand = new Errand();
            errand.setGdes(request.getDescription());
            errand.setGprice(request.getPrice());
            errand.setGcampus(request.getCampus());
            errand.setGoodsImageList(request.getImageList());
            errand.setGcategory(1);
            errand.setStatus(1);
            errand.setUID(openID);
            errand.setDeadline(request.getDeadline());

            try {
                int goodsId = goodsService.insertErrand(errand);
                return ApiResponse.ok(goodsId);
            } catch (Exception e) {
                e.printStackTrace();
                return ApiResponse.error("发布失败");
            }
        }
    }

    @PostMapping("/api/goods/modify")
    ApiResponse modifyGood(@RequestBody ModifyRequest request, @RequestHeader("x-wx-openid") String openid) {
        if(! request.getUid().equals(openid)){
            return ApiResponse.error("操作人与商品所属人不一致");
        }
        Good good;
        if (request.getCategory() == 0) {
            good = new IdleItem();
            good.setGcategory(0);
        } else if(request.getCategory() == 1) {
            good = new Errand();
            good.setGcategory(1);
            ((Errand) good).setDeadline(request.getDeadline());
        }else {
            return ApiResponse.error("商品类型未知");
        }
        good.setGID(request.getGid());
        good.setGdes(request.getDescription());
        good.setGcampus(request.getCampus());
        good.setStatus(request.getStatus());
        good.setUID(openid);
        good.setGprice(request.getPrice());
        good.setGoodsImageList(request.getGoodsImages());

        try{
            goodsService.modifyGood(good);
            return ApiResponse.ok();
        }catch (Exception e){
            e.printStackTrace();
            return ApiResponse.error("修改失败");
        }
    }

    @PostMapping("/api/goods/test")
    ApiResponse releaseGood(@RequestBody String jsonString) {
        logger.info("/api/goods/test POST request");
        logger.info(jsonString);
        return ApiResponse.ok();
    }


    //delete a good by gid (not actually delete, just set status to 3)
    @PostMapping("/api/goods/delete/{gid}")
    ApiResponse deleteGood(@PathVariable Integer gid, @RequestHeader("x-wx-openid") String openid) {
        logger.info("/api/goods/delete POST request");
        logger.info("gid: " + gid);
        logger.info("openid: " + openid);
        try {
            goodsService.deleteGood(gid, openid);
            return ApiResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return ApiResponse.error("删除失败");
        }
    }

    @GetMapping("/api/goods/getones/{uid}")
    ApiResponse getOnesSellingGoods(@PathVariable String uid){
        Optional<ArrayList<Good>> goods=goodsService.getSomeonesSellingGoods(uid);
        if (goods.isPresent()) {
            return ApiResponse.ok(goods);
        } else {
            return ApiResponse.error("");
        }
    }
    @GetMapping("/api/goods/saleconfirm/{sellerid}/{buyerid}/{gid}")
    ApiResponse saleConfirm(@PathVariable String sellerid,@PathVariable String buyerid,@PathVariable int gid){
        int result=goodsService.setGoodSold(sellerid,buyerid,gid);
        if(result == 1){
            return ApiResponse.ok("操作成功");
        }
        else {
            return ApiResponse.error("操作失败");
        }
    }

}