package com.zhuoyi.system.network.callback;

import com.zhuoyi.system.network.serializer.ZyCom_Message;

public interface NetworkCallback {
    void onResponse(Boolean bool, ZyCom_Message zyCom_Message);
}
