package com.zhuoyi.account.model;

public class User {
    private String TOKEN;
    private String UID;
    private int age;
    private String desc;
    private String expires_in;
    private String gender;
    private int level;
    private String logoUrl;
    private String openKey;
    private String openid;
    private String password;
    private int recode = 0;
    private String regtype;
    private int result = -1;
    private String username;

    public String getOpenid() {
        return this.openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenKey() {
        return this.openKey;
    }

    public void setOpenKey(String openKey) {
        this.openKey = openKey;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public int getRecode() {
        return this.recode;
    }

    public void setRecode(int recode) {
        this.recode = recode;
    }

    public String getLogoUrl() {
        return this.logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegtype() {
        return this.regtype;
    }

    public void setRegtype(String regtype) {
        this.regtype = regtype;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUID() {
        return this.UID;
    }

    public void setUID(String uID) {
        this.UID = uID;
    }

    public String getTOKEN() {
        return this.TOKEN;
    }

    public void setTOKEN(String tOKEN) {
        this.TOKEN = tOKEN;
    }
}
