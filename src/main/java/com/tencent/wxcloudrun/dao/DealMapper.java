package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.Deal;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DealMapper {

  List<Deal> buyOrSell(@Param("buyerId") String buyerId, @Param("sellerId") String sellerId);
  @Insert("insert into deal(GID, BuyerID, SellerID) values (#{gid}, #{buyerid}, #{sellerid})")
  void insertDeal(@Param("gid") int gid,@Param("buyerid") String buyerid,@Param("sellerid") String sellerid);
}
