package com.tencent.wxcloudrun.dao;


import com.tencent.wxcloudrun.dto.MessageRequest;
import org.apache.ibatis.annotations.Insert;
import com.tencent.wxcloudrun.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import java.time.LocalDateTime;

import java.util.ArrayList;

@Mapper
public interface MessageMapper {

    @Select("select * from message where (SenderID = #{senderid} and ReceiverID = #{receiverid})or(SenderID = #{receiverid} and ReceiverID = #{senderid})")
    ArrayList<MessageRequest>getMessage(@Param("senderid") String senderid,@Param("receiverid") String receiverid);

    @Insert("insert into message (SenderID,ReceiverID,Content,Mtime,Category) values(#{senderid},#{receiverid},#{content},#{mtime},#{category})")
    void insertMessage(@Param("senderid")String senderid, @Param("receiverid")String receiverid, @Param("content")String content, @Param("mtime")LocalDateTime mtime,@Param("category")int category);

    @Select("SELECT * FROM ( SELECT * FROM message WHERE Mtime IN ( SELECT MAX( Mtime ) FROM message WHERE ReceiverID = #{uid} OR SenderID = #{uid} GROUP BY CONCAT( IF ( SenderID > ReceiverID, SenderID, ReceiverID ), IF ( SenderID < ReceiverID, SenderID, ReceiverID ))) AND ( ReceiverID = #{uid} OR SenderID = #{uid} ) ORDER BY MID DESC ) c ORDER BY Mtime DESC")
    ArrayList<Message> getMessageList(@Param("uid") String uid);

}

