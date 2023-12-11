package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsImage implements Serializable, Comparable {
    protected Integer g_image_id;
    protected Integer g_id;
    protected String i_url;
    protected Integer i_order;


    //以除了image_id的属性作为是否相等的比较依据
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodsImage)) return false;

        GoodsImage that = (GoodsImage) o;

        if (!g_id.equals(that.g_id)) return false;
        if (!i_url.equals(that.i_url)) return false;
        return i_order.equals(that.i_order);
    }


    //使得arraylist的sort以GoodsImage的order为依据
    @Override
    public int compareTo(Object o) {
        int order1 = this.getI_order();
        int order2 = ((GoodsImage)o).getI_order();
        return Integer.compare(order1, order2);
    }
}
