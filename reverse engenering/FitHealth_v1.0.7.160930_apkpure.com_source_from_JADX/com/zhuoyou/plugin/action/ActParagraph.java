package com.zhuoyou.plugin.action;

public class ActParagraph {
    private String mContent;
    private String mDescription;
    private String mImgUrl;
    private int mParagraphNum;

    public ActParagraph(String description, int paragraphnum, String imgUrl, String content) {
        this.mDescription = description;
        this.mParagraphNum = paragraphnum;
        this.mImgUrl = imgUrl;
        this.mContent = content;
    }

    public String GetDescription() {
        return this.mDescription;
    }

    public int GetParagraphNum() {
        return this.mParagraphNum;
    }

    public String GetImgUrl() {
        return this.mImgUrl;
    }

    public String GetContent() {
        return this.mContent;
    }
}
