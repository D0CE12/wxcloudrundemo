package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.MessageMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.dto.MessageRequest;
import com.tencent.wxcloudrun.model.Chat;
import com.tencent.wxcloudrun.model.Message;
import com.tencent.wxcloudrun.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


@Service
public class MessageServiceImpl implements MessageService {
    final MessageMapper messageMapper;
    final UserMapper userMapper;

    public MessageServiceImpl(@Autowired MessageMapper messageMapper,UserMapper userMapper){
        this.messageMapper=messageMapper;
        this.userMapper=userMapper;
    }
    @Override
    public Optional<ArrayList<MessageRequest>> getMessage(String senderid,String receiverid) {
        ArrayList<MessageRequest> message = messageMapper.getMessage(senderid,receiverid);
        return Optional.of(message);
    }
    @Override
    public int insertMessage(MessageRequest message){
        String senderid = message.getSenderid();
        String reveiverid = message.getReceiverid();
        String content = message.getContent();
        LocalDateTime otime = message.getMtime();
        int category = message.getCategory();
        messageMapper.insertMessage(senderid,reveiverid,content,otime,category);
        return 0;
    }

    @Override
    public Optional<ArrayList<Chat>> getMessageList(String uid) {
        ArrayList<Message> messages;
        messages=messageMapper.getMessageList(uid);
        ArrayList<Chat> chats=new ArrayList<>();
        for (Message message : messages) {
            if (message.getSenderid().equals(uid)) {
                Chat chat = new Chat(message, userMapper.getUserName(message.getReceiverid()), userMapper.getUserProfilePhoto(message.getReceiverid()),message.getReceiverid());
                chats.add(chat);
            } else {
                Chat chat = new Chat(message, userMapper.getUserName(message.getSenderid()), userMapper.getUserProfilePhoto(message.getSenderid()),message.getSenderid());
                chats.add(chat);
            }
        }
        return Optional.of(chats);
    }
}
