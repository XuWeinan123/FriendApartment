package com.aaronxu.friendapartment.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by AaronXu on 2016-10-26.
 */

public class HouseBean extends BmobObject {
    private String houseName;
    private String titleImage;
    private String masterName;
    private Float houseSize;
    private String houseLocation;
    private Integer housePrice;
    private List<String> houseLabel;
    private Integer houseAge;
    private Integer houseCollectNumber;

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public Float getHouseSize() {
        return houseSize;
    }

    public void setHouseSize(Float houseSize) {
        this.houseSize = houseSize;
    }

    public String getHouseLocation() {
        return houseLocation;
    }

    public void setHouseLocation(String houseLocation) {
        this.houseLocation = houseLocation;
    }

    public Integer getHousePrice() {
        return housePrice;
    }

    public void setHousePrice(Integer housePrice) {
        this.housePrice = housePrice;
    }

    public List<String> getHouseLabel() {
        return houseLabel;
    }

    public void setHouseLabel(List<String> houseLabel) {
        this.houseLabel = houseLabel;
    }

    public Integer getHouseAge() {
        return houseAge;
    }

    public void setHouseAge(Integer houseAge) {
        this.houseAge = houseAge;
    }

    public Integer getHouseCollectNumber() {
        return houseCollectNumber;
    }

    public void setHouseCollectNumber(Integer houseCollectNumber) {
        this.houseCollectNumber = houseCollectNumber;
    }
}
