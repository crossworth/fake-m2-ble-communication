package com.zhuoyou.plugin.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActionPannelItemInfo implements Serializable {
    private List<ActParagraph> mActParagraph = new ArrayList();
    private String mPannelTitle;

    public ActionPannelItemInfo(String pannelTitle) {
        this.mPannelTitle = pannelTitle;
    }

    public void SetPannelTitle(String mtitle) {
        this.mPannelTitle = mtitle;
    }

    public String GetPannelTitle() {
        return this.mPannelTitle;
    }

    public void AddPannelParagraph(ActParagraph actparagraph) {
        this.mActParagraph.add(actparagraph);
    }

    public void SetPannelParagraph(List<ActParagraph> actparagraphlist) {
        this.mActParagraph = actparagraphlist;
    }

    public List<ActParagraph> GetActParagraphList() {
        return this.mActParagraph;
    }
}
