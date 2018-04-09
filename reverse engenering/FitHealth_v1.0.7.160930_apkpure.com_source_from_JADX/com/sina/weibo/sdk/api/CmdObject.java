package com.sina.weibo.sdk.api;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class CmdObject extends BaseMediaObject {
    public static final String CMD_HOME = "home";
    public static final Creator<CmdObject> CREATOR = new C06531();
    public String cmd;

    class C06531 implements Creator<CmdObject> {
        C06531() {
        }

        public CmdObject createFromParcel(Parcel in) {
            return new CmdObject(in);
        }

        public CmdObject[] newArray(int size) {
            return new CmdObject[size];
        }
    }

    public CmdObject(Parcel in) {
        this.cmd = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cmd);
    }

    public boolean checkArgs() {
        if (this.cmd == null || this.cmd.length() == 0 || this.cmd.length() > 1024) {
            return false;
        }
        return true;
    }

    public int getObjType() {
        return 7;
    }

    protected BaseMediaObject toExtraMediaObject(String str) {
        return this;
    }

    protected String toExtraMediaString() {
        return "";
    }
}
