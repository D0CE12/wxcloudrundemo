package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.dto.MessageRequest;
import com.tencent.wxcloudrun.model.Chat;
import com.tencent.wxcloudrun.service.MessageService;
import com.tencent.wxcloudrun.config.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Optional;

@RestController
public class MessageController {
    final MessageService messageService;
    final Logger logger;
    public MessageController(@Autowired MessageService messageService){
        this.messageService = messageService;
        this.logger = LoggerFactory.getLogger(MessageController.class);
    }
    @GetMapping("/api/message/get/{senderid}/{receiverid}")
    public ApiResponse getMessage(@PathVariable String senderid,@PathVariable String receiverid) {
        Optional<ArrayList<MessageRequest>> message = messageService.getMessage(senderid,receiverid);
        if (message.isPresent()) {
            return ApiResponse.ok(message);
        } else {
            return ApiResponse.error("Message not found");
        }
    }

    @PostMapping("/api/message/insert")
    public ApiResponse insertMessage(@RequestBody MessageRequest message){
        logger.info("/api/goods/release POST request");
        logger.info(message.toString());
        int result = messageService.insertMessage(message);
        if(result == 0){
            return ApiResponse.ok("保存成功");
        }
        else {
            return ApiResponse.error("保存失败");
        }
    }
    @GetMapping ("/api/message/list/{uid}")
    public ApiResponse getMessageList(@PathVariable String uid){
        Optional<ArrayList<Chat>> chats = messageService.getMessageList(uid);
        if (chats.isPresent()) {
            return ApiResponse.ok(chats);
        } else {
            return ApiResponse.error("");
        }
    }
}
