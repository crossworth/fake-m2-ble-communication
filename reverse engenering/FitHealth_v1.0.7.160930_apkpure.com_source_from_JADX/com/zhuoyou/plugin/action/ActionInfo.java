package com.zhuoyou.plugin.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionInfo implements Serializable {
    private int is_join = 0;
    private int mActionId = -1;
    private List<ActionPannelItemInfo> mPannelContent = new ArrayList();
    private List<ActionRankingItemInfo> mRankList = new ArrayList();
    private ActionRankingItemInfo myRank = new ActionRankingItemInfo();

    public void AddPannel(ActionPannelItemInfo actionpannelitem) {
        this.mPannelContent.add(actionpannelitem);
    }

    public List<ActionPannelItemInfo> getPannel() {
        return this.mPannelContent;
    }

    public List<ActionRankingItemInfo> getRankList() {
        return this.mRankList;
    }

    public void SetPannelList(List<ActionPannelItemInfo> mm) {
        this.mPannelContent = mm;
    }

    public void AddRank(ActionRankingItemInfo mrankinfo) {
        this.mRankList.add(mrankinfo);
    }

    public void SetRankList(List<ActionRankingItemInfo> mrankinfolist) {
        this.mRankList = mrankinfolist;
    }

    public void SetMyRankInfo(ActionRankingItemInfo mrank) {
        this.myRank.SetRank(mrank.GetCount(), mrank.GetAccountId(), mrank.GetSteps(), mrank.GetHeadImgId(), mrank.GetName(), mrank.GetHeadImgUrl());
    }

    public ActionRankingItemInfo getMyRankInfo() {
        return this.myRank;
    }

    public void SetMyRankInfo(int count, String accountId, int steps, int headimgId, String name, String headImgUrl) {
        this.myRank.SetRank(count, accountId, steps, headimgId, name, headImgUrl);
    }

    public void SetActionId(int id) {
        this.mActionId = id;
    }

    public int GetActionId() {
        return this.mActionId;
    }

    public void SetJoinFlag(int flag) {
        this.is_join = flag;
    }

    public int GetIsJoin() {
        return this.is_join;
    }
}
