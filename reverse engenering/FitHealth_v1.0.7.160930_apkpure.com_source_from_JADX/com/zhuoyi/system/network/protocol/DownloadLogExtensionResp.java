package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;

@SignalCode(encrypt = true, messageCode = 214007)
public class DownloadLogExtensionResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = -134526114838813854L;
}
