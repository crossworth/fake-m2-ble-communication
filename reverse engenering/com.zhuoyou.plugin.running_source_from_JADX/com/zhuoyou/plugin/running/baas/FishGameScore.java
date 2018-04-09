package com.zhuoyou.plugin.running.baas;

import com.droi.sdk.core.DroiExpose;
import com.droi.sdk.core.DroiObject;

public class FishGameScore extends DroiObject {
    @DroiExpose
    String account;
    @DroiExpose
    int coin;
    @DroiExpose
    String date;
    @DroiExpose
    int score;
    @DroiExpose
    int time = 3;
    @DroiExpose
    int topLevel;
    @DroiExpose
    int topScore;

    public String getAccount() {
        return this.account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTopLevel() {
        return this.topLevel;
    }

    public void setTopLevel(int topLevel) {
        this.topLevel = topLevel;
    }

    public int getTopScore() {
        return this.topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public int getCoin() {
        return this.coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }
}
