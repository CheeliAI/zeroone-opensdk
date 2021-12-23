/**
  * Copyright 2021 jb51.net 
  */
package com.cheeli.models.taobao.waybill;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Adsinfo {

    @JsonProperty("adId")
    private String adid;
    @JsonProperty("advertisementType")
    private String advertisementtype;
    @JsonProperty("bannerUrl")
    private String bannerurl;
    @JsonProperty("miniBannerUrl")
    private String minibannerurl;
    @JsonProperty("trackUrl")
    private String trackurl;
    public void setAdid(String adid) {
         this.adid = adid;
     }
     public String getAdid() {
         return adid;
     }

    public void setAdvertisementtype(String advertisementtype) {
         this.advertisementtype = advertisementtype;
     }
     public String getAdvertisementtype() {
         return advertisementtype;
     }

    public void setBannerurl(String bannerurl) {
         this.bannerurl = bannerurl;
     }
     public String getBannerurl() {
         return bannerurl;
     }

    public void setMinibannerurl(String minibannerurl) {
         this.minibannerurl = minibannerurl;
     }
     public String getMinibannerurl() {
         return minibannerurl;
     }

    public void setTrackurl(String trackurl) {
         this.trackurl = trackurl;
     }
     public String getTrackurl() {
         return trackurl;
     }

}