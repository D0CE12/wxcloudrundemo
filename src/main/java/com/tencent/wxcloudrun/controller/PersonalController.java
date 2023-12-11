package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Deal;
import com.tencent.wxcloudrun.model.Operation;
import com.tencent.wxcloudrun.service.OperationService;
import com.tencent.wxcloudrun.service.PersonalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PersonalController {

    final Logger logger;

    final PersonalService personalService;


    final OperationService operationService;

    public PersonalController(@Autowired PersonalService personalService, @Autowired OperationService operationService) {
        this.logger = LoggerFactory.getLogger(PersonalController.class);
        this.personalService = personalService;
        this.operationService = operationService;
    }

    @GetMapping ("/api/personal/queryCollectByUid")
    public ApiResponse queryCollectByUid(@RequestHeader("x-wx-openid") String uid){
        logger.info("openid: " + uid);
        try {
            ArrayList<Operation> list = operationService.queryCollectByUid(uid);
            logger.info(String.valueOf(list));
            return ApiResponse.ok(list);
        } catch (Exception e){
            e.printStackTrace();
            return ApiResponse.error("失败");
        }
    }

    @GetMapping ("/api/personal/querybrowseByUid")
    public ApiResponse querybrowseByUid(@RequestHeader("x-wx-openid") String uid){
        logger.info("openid: " + uid);
        try {
            ArrayList<Operation> list = operationService.querybrowseByUid(uid);
            logger.info(String.valueOf(list));
            return ApiResponse.ok(list);
        } catch (Exception e){
            e.printStackTrace();
            return ApiResponse.error("失败");
        }
    }

    @GetMapping("/api/personal/queryBuyByUid")
    public ApiResponse queryBuyByUid(@RequestHeader("x-wx-openid") String uid){
        List<Deal> list =  personalService.buyOrSell(uid, null);
        return ApiResponse.ok(list);
    }

    @GetMapping("/api/personal/querySellByUid")
    public ApiResponse querySellByUid(@RequestHeader("x-wx-openid") String uid){
        List<Deal> list =  personalService.buyOrSell(null, uid);
        return ApiResponse.ok(list);
    }
}
