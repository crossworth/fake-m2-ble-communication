package com.zhuoyou.plugin.running.bean;

import java.io.Serializable;

public class GroupNumber implements Serializable {
    private String image;
    private String name;
    private int rank;
    private int steps;

    public GroupNumber(int rank, String name, int steps) {
        this.rank = rank;
        this.name = name;
        this.steps = steps;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getSteps() {
        return this.steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
