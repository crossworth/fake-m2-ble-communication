package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.sina.weibo.sdk.utils.LogUtil;

public class TextObject extends BaseMediaObject {
    public static final Creator<TextObject> CREATOR = new C06561();
    public String text;

    class C06561 implements Creator<TextObject> {
        C06561() {
        }

        public TextObject createFromParcel(Parcel in) {
            return new TextObject(in);
        }

        public TextObject[] newArray(int size) {
            return new TextObject[size];
        }
    }

    public TextObject(Parcel in) {
        this.text = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
    }

    public boolean checkArgs() {
        if (this.text != null && this.text.length() != 0 && this.text.length() <= 1024) {
            return true;
        }
        LogUtil.m2215e("Weibo.TextObject", "checkArgs fail, text is invalid");
        return false;
    }

    public int getObjType() {
        return 1;
    }

    protected BaseMediaObject toExtraMediaObject(String str) {
        return this;
    }

    protected String toExtraMediaString() {
        return "";
    }
}
