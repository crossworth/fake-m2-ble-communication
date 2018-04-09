package com.tencent.stat;

public class StatGameUser implements Cloneable {
    private String f2813a = "";
    private String f2814b = "";
    private String f2815c = "";

    public StatGameUser clone() {
        try {
            return (StatGameUser) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getAccount() {
        return this.f2814b;
    }

    public String getLevel() {
        return this.f2815c;
    }

    public String getWorldName() {
        return this.f2813a;
    }

    public void setAccount(String str) {
        this.f2814b = str;
    }

    public void setLevel(String str) {
        this.f2815c = str;
    }

    public void setWorldName(String str) {
        this.f2813a = str;
    }
}
