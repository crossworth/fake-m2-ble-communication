package com.zhuoyou.plugin.address;

public class DistrictModel {
    private String name;
    private String zipcode;

    public DistrictModel(String name, String zipcode) {
        this.name = name;
        this.zipcode = zipcode;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String toString() {
        return "DistrictModel [name=" + this.name + ", zipcode=" + this.zipcode + "]";
    }
}
