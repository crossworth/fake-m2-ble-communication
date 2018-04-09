package com.zhuoyou.plugin.rank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class RankInfo implements Serializable {
    private static final long serialVersionUID = -4024181216006849270L;
    private String accountId;
    private String mHeadUrl;
    private int mImgId;
    private String mName;
    private int mRank;
    private String mSteps;

    public RankInfo(int rank, int imgId, String accountId, String name, String steps, String headUrl) {
        this.mRank = rank;
        this.mImgId = imgId;
        this.accountId = accountId;
        this.mName = name;
        this.mSteps = steps;
        this.mHeadUrl = headUrl;
    }

    public int getRank() {
        return this.mRank;
    }

    public String getName() {
        return this.mName;
    }

    public void setRank(int mRank) {
        this.mRank = mRank;
    }

    public int getmImgId() {
        return this.mImgId;
    }

    public void setmImgId(int mImgId) {
        this.mImgId = mImgId;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getmSteps() {
        return this.mSteps;
    }

    public void setmSteps(String mSteps) {
        this.mSteps = mSteps;
    }

    public String getHeadUrl() {
        return this.mHeadUrl;
    }

    public void setHeadUrl(String mHeadUrl) {
        this.mHeadUrl = mHeadUrl;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(this.mRank);
        out.writeInt(this.mImgId);
        out.writeUTF(this.accountId);
        out.writeUTF(this.mName);
        out.writeUTF(this.mSteps);
        out.writeUTF(this.mHeadUrl);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.mRank = in.readInt();
        this.mImgId = in.readInt();
        this.accountId = in.readUTF();
        this.mName = in.readUTF();
        this.mSteps = in.readUTF();
        this.mHeadUrl = in.readUTF();
    }
}
