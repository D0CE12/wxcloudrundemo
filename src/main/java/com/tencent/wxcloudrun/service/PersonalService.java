package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.model.Deal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonalService {
    List<Deal> buyOrSell(String buyerId, String sellerId);
}
