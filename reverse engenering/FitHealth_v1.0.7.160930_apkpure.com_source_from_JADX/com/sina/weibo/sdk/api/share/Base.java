package com.sina.weibo.sdk.api.share;

import android.content.Context;
import android.os.Bundle;

public abstract class Base {
    public String transaction;

    abstract boolean check(Context context, VersionCheckHandler versionCheckHandler);

    public abstract void fromBundle(Bundle bundle);

    public abstract int getType();

    public abstract void toBundle(Bundle bundle);
}
