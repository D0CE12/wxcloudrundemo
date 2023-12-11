package com.tencent.wxcloudrun.service;

import com.tencent.wxcloudrun.dto.OperationLog;
import com.tencent.wxcloudrun.model.Operation;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface OperationService {
    int Operation(OperationLog operationLog);

    int wannaBuy(String uid,Integer gid);

    ArrayList<Operation> queryCollectByUid(String userid);

    ArrayList<Operation> querybrowseByUid(String userid);

}
