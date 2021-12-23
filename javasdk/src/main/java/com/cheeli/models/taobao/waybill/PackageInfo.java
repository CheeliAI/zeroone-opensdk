/**
  * Copyright 2021 jb51.net 
  */
package com.cheeli.models.taobao.waybill;

import java.util.List;

public class PackageInfo {

    private String id;
    private List<PackageItem> items;
    private int volume;
    private int weight;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setItems(List<PackageItem> items) {
        this.items = items;
    }
    public List<PackageItem> getItems() {
        return items;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }
    public int getVolume() {
        return volume;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    public int getWeight() {
        return weight;
    }

}
