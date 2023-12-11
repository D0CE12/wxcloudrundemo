package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.GoodsImage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ImageMapper {

    @Insert("insert into goods_image (g_id, i_url, i_order) values (#{g_id}, #{i_url}, #{i_order})")
    void insertGoodImage(GoodsImage image);

    @Delete("delete from goods_image where g_image_id = #{g_image_id} LIMIT 1")
    void deleteGoodImage(@Param("g_image_id") Integer goodImageId);
}
