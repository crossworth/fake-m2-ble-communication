package com.zhuoyou.plugin.action;

public class ActionRankingItemInfo {
    private String mAccountId;
    private int mCount;
    private int mHeadImgId;
    private String mHeadImgUrl;
    private String mName;
    private int mSteps;

    public ActionRankingItemInfo(int count, String accountId, int steps, int headimgId, String name, String headImgUrl) {
        this.mCount = count;
        this.mAccountId = accountId;
        this.mSteps = steps;
        this.mHeadImgId = headimgId;
        this.mName = name;
        this.mHeadImgUrl = headImgUrl;
    }

    public int GetCount() {
        return this.mCount;
    }

    public String GetAccountId() {
        return this.mAccountId;
    }

    public int GetSteps() {
        return this.mSteps;
    }

    public int GetHeadImgId() {
        return this.mHeadImgId;
    }

    public String GetName() {
        return this.mName;
    }

    public String GetHeadImgUrl() {
        return this.mHeadImgUrl;
    }

    public void SetRank(int count, String accountId, int steps, int headimgId, String name, String headImgUrl) {
        this.mCount = count;
        this.mAccountId = accountId;
        this.mSteps = steps;
        this.mHeadImgId = headimgId;
        this.mName = name;
        this.mHeadImgUrl = headImgUrl;
    }
}
