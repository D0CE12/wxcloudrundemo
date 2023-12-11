package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.service.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.tencent.wxcloudrun.dto.OperationLog;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class OperationController {

    final OperationService operationService;

    public OperationController(@Autowired OperationService operationService){
        this.operationService = operationService;
    }

    @PostMapping ("/api/operation")
    public ApiResponse Operation(@RequestBody OperationLog operationLog) {
        int result = operationService.Operation(operationLog);
        if(result == 1){
            return ApiResponse.ok("操作成功");
        }
        else {
            return ApiResponse.error("操作失败");
        }
    }
    @GetMapping("/api/operation/wannabuy/{uid}/{gid}")
    public ApiResponse wannaBuy(@PathVariable String uid,@PathVariable Integer gid){
        int result = operationService.wannaBuy(uid,gid);
        if(result == 1){
            return ApiResponse.ok("操作成功");
        }
        else {
            return ApiResponse.error("操作失败");
        }
    }

}
