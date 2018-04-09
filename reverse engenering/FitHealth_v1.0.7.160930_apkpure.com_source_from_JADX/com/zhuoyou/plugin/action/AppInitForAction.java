package com.zhuoyou.plugin.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AppInitForAction implements Serializable {
    private List<ActionListItemInfo> mActionList = new ArrayList();
    private List<MessageInfo> mMsgList = new ArrayList();
    private ActionWelcomeInfo mWelcomeData = null;

    public void SetWelcomeInfo(ActionWelcomeInfo welcomedata) {
        this.mWelcomeData = welcomedata;
    }

    public ActionWelcomeInfo GetWelcomeInfo() {
        return this.mWelcomeData;
    }

    public void SetMsgList(List<MessageInfo> msglist) {
        this.mMsgList = msglist;
    }

    public void AddMsgItem(MessageInfo msg) {
        this.mMsgList.add(msg);
    }

    public List<MessageInfo> GetMsgList() {
        return this.mMsgList;
    }

    public void SetActionListItem(List<ActionListItemInfo> mlist) {
        this.mActionList = mlist;
    }

    public void AddActionListItem(ActionListItemInfo item) {
        this.mActionList.add(item);
    }

    public List<ActionListItemInfo> GetActionList() {
        return this.mActionList;
    }

    public void CacheDate() {
    }
}
