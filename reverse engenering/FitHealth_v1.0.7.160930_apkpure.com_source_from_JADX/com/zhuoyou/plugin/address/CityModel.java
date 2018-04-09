package com.zhuoyou.plugin.address;

import java.util.List;

public class CityModel {
    private List<DistrictModel> districtList;
    private String name;

    public CityModel(String name, List<DistrictModel> districtList) {
        this.name = name;
        this.districtList = districtList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DistrictModel> getDistrictList() {
        return this.districtList;
    }

    public void setDistrictList(List<DistrictModel> districtList) {
        this.districtList = districtList;
    }

    public String toString() {
        return "CityModel [name=" + this.name + ", districtList=" + this.districtList + "]";
    }
}
