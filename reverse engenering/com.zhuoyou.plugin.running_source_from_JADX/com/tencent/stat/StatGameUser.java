package com.tencent.stat;

public class StatGameUser implements Cloneable {
    private String f4315a = "";
    private String f4316b = "";
    private String f4317c = "";

    public StatGameUser clone() {
        try {
            return (StatGameUser) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getAccount() {
        return this.f4316b;
    }

    public String getLevel() {
        return this.f4317c;
    }

    public String getWorldName() {
        return this.f4315a;
    }

    public void setAccount(String str) {
        this.f4316b = str;
    }

    public void setLevel(String str) {
        this.f4317c = str;
    }

    public void setWorldName(String str) {
        this.f4315a = str;
    }
}
