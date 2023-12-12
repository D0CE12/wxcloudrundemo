package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Errand;
import com.tencent.wxcloudrun.model.Good;
import com.tencent.wxcloudrun.model.GoodsImage;
import com.tencent.wxcloudrun.model.IdleItem;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface GoodsMapper {
    @Select("select * from goods where GID = #{GID}")
    Good getGood(@Param("GID") int gid);

    @Select("SELECT * FROM goods WHERE MATCH(Gdes) AGAINST (#{keyword} IN NATURAL LANGUAGE MODE WITH QUERY EXPANSION) AND Status = 1")
    ArrayList<Good> getGoods(@Param("keyword") String keyword);

    @Select("select * from goods where Gcategory = #{Gcategory} and Status = 1")
    ArrayList<Good> getSellingGoods(@Param("Gcategory") int Gcategory);

    @Select("select * from goods_image where g_id = #{g_id}")
    ArrayList<GoodsImage> getGoodImage(@Param("g_id") int gid);

    @Insert("insert into goods(Gdes, Gprice, Gcampus, Status, Gcategory, UID) values (#{Gdes}, #{Gprice}, #{Gcampus}, #{Status}, #{Gcategory}, #{UID})")
    @Options(useGeneratedKeys = true, keyProperty = "GID", keyColumn = "GID")
    void insertGood(Good good);

    @Insert("insert into idle_item (GID) values (#{GID})")
    void insertIdleItem(IdleItem idleItem);

    @Insert("insert into errand (GID, deadline) values (#{GID}, #{deadline})")
    void insertErrand(Errand errand);

    @Select("select * from goods where UID = #{uid} and Status = 1")
    ArrayList<Good> getSomeonesSellingGoods(@Param("uid") String uid);

    @Select("select * from goods_image where g_id = #{g_id} and i_order=1")
    ArrayList<GoodsImage> getFirstImage(@Param("g_id") int gid);

    @Update("update goods set Gdes = #{Gdes}, Gprice = #{Gprice}, Gcampus = #{Gcampus}, Gupdatetime = NOW(), Status = #{Status}, Gcategory = #{Gcategory} where GID = #{GID}")
    void modifyGood(Good good);

    @Update("update errand set deadline = #{deadline} where gid = #{GID}")
    void modifyErrand(Errand good);

    //set end time
    @Update("update ${category} set endtime = NOW() where gid = #{GID}")
    void endGood(@Param("category") String category, @Param("GID") Integer gid);

    @Update("update goods set Status = 2 where gid = #{GID}")
    void setGoodSold(@Param("GID") int gid);

    @Select("select Gcategory from goods where gid = #{GID}")
    int getCategory(@Param("GID") int gid);
}


