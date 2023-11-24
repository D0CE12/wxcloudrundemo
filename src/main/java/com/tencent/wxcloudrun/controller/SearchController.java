package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.model.Good;
import com.tencent.wxcloudrun.service.SearchService;
import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class SearchController {
    private final SearchService searchService;

    public SearchController(@Autowired SearchService searchService) { this.searchService = searchService; }

    @GetMapping("/api/goods/search/{Gdes}")
    public ApiResponse searchGoods(@PathVariable String Gdes) {
        Optional<ArrayList<Good>> goodsList = searchService.searchGoods(Gdes);
        if (goodsList.isPresent()) {
            return ApiResponse.ok(goodsList); // 如果成功获取到商品，则返回成功的响应
        } else {
            return ApiResponse.error("Product not found for Goods Description: " + Gdes); // 根据商品描述搜索商品但未找到的错误响应
        }
    }

}


