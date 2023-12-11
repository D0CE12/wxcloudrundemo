package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.DealMapper;
import com.tencent.wxcloudrun.dao.OperationMapper;
import com.tencent.wxcloudrun.model.Deal;
import com.tencent.wxcloudrun.service.PersonalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PersonalServiceImpl implements PersonalService {
    @Resource
    private DealMapper dealMapper;

    @Override
    public List<Deal> buyOrSell(String buyerId, String sellerId) {
        return dealMapper.buyOrSell(buyerId, sellerId);
    }
}
