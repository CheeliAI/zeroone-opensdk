package com.cheeli.models.pdd;

public class Item_list {

    private int goods_count;
    private long goods_id;
    private String goods_img;
    private String goods_name;
    private int goods_price;
    private String goods_spec;
    private String outer_goods_id;
    private String outer_id;
    private long sku_id;
    public void setGoods_count(int goods_count) {
        this.goods_count = goods_count;
    }
    public int getGoods_count() {
        return goods_count;
    }

    public void setGoods_id(long goods_id) {
        this.goods_id = goods_id;
    }
    public long getGoods_id() {
        return goods_id;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }
    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }
    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_price(int goods_price) {
        this.goods_price = goods_price;
    }
    public int getGoods_price() {
        return goods_price;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }
    public String getGoods_spec() {
        return goods_spec;
    }

    public void setOuter_goods_id(String outer_goods_id) {
        this.outer_goods_id = outer_goods_id;
    }
    public String getOuter_goods_id() {
        return outer_goods_id;
    }

    public void setOuter_id(String outer_id) {
        this.outer_id = outer_id;
    }
    public String getOuter_id() {
        return outer_id;
    }

    public void setSku_id(long sku_id) {
        this.sku_id = sku_id;
    }
    public long getSku_id() {
        return sku_id;
    }

}