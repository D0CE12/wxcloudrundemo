package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.model.GoodsImage;
import com.tencent.wxcloudrun.service.ImageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ImageServiceImpl implements ImageService {
    @Override
    public boolean imageListsAreSame(ArrayList<GoodsImage> originalGoodImages, ArrayList<GoodsImage> goodsImageList) {
        if (originalGoodImages.size() == goodsImageList.size()){
           originalGoodImages.sort(null);
           goodsImageList.sort(null);
            for (int i = 0; i < originalGoodImages.size(); i++){
                if (! originalGoodImages.get(i).equals(goodsImageList.get(i))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
