package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;

@SignalCode(encrypt = true, messageCode = 214001)
public class GetSaleStatisticsResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = -5054597735021386362L;
}
