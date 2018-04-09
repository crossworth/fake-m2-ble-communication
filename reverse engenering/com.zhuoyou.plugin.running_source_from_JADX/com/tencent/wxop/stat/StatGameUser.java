package com.tencent.wxop.stat;

public class StatGameUser implements Cloneable {
    private String f4560a = "";
    private String f4561b = "";
    private String f4562c = "";

    public StatGameUser(String str, String str2, String str3) {
        this.f4561b = str;
        this.f4560a = str2;
        this.f4562c = str3;
    }

    public StatGameUser clone() {
        try {
            return (StatGameUser) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public String getAccount() {
        return this.f4561b;
    }

    public String getLevel() {
        return this.f4562c;
    }

    public String getWorldName() {
        return this.f4560a;
    }

    public void setAccount(String str) {
        this.f4561b = str;
    }

    public void setLevel(String str) {
        this.f4562c = str;
    }

    public void setWorldName(String str) {
        this.f4560a = str;
    }

    public String toString() {
        return "StatGameUser [worldName=" + this.f4560a + ", account=" + this.f4561b + ", level=" + this.f4562c + "]";
    }
}
