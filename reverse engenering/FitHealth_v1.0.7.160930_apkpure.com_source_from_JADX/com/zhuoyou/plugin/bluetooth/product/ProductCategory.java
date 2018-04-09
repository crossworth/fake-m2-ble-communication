package com.zhuoyou.plugin.bluetooth.product;

public class ProductCategory {
    public boolean mEnable = false;
    public String mNickName = null;
    public String mRemoteName = "";

    public ProductCategory(String nickname) {
        this.mNickName = nickname;
    }

    public void enableProductItem(boolean enable) {
        this.mEnable = enable;
    }
}
