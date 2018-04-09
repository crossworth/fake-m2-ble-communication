package com.zhuoyou.plugin.address;

import java.util.List;

public class ProvinceModel {
    private List<CityModel> cityList;
    private String name;

    public ProvinceModel(String name, List<CityModel> cityList) {
        this.name = name;
        this.cityList = cityList;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCityList() {
        return this.cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }

    public String toString() {
        return "ProvinceModel [name=" + this.name + ", cityList=" + this.cityList + "]";
    }
}
