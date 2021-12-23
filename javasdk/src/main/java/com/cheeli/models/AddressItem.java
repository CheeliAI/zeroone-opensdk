package com.cheeli.models;

import com.alibaba.fastjson.annotation.JSONField;

public class AddressItem {

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getResolveAddress() {
        return ResolveAddress;
    }

    public void setResolveAddress(String resolveAddress) {
        ResolveAddress = resolveAddress;
    }

    @JSONField(name="object_id")
    private String objectId;

    @JSONField(name="resolve_address")
    private String ResolveAddress;

}
