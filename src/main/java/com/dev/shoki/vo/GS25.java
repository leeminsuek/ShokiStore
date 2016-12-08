package com.dev.shoki.vo;

import lombok.Data;

/**
 * Created by shoki on 16. 12. 8..
 */
public class GS25 {

    private String name;
    private int price;
    private String prodItemType;
    private String StoreType;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProdItemType() {
        return prodItemType;
    }

    public void setProdItemType(String prodItemType) {
        this.prodItemType = prodItemType;
    }

    public String getStoreType() {
        return StoreType;
    }

    public void setStoreType(String storeType) {
        StoreType = storeType;
    }
}
