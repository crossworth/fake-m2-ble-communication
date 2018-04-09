package com.zhuoyi.system.network.protocol;

import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.serializer.SignalCode;
import com.zhuoyi.system.network.serializer.ZyCom_ResponseBody;

@SignalCode(encrypt = true, messageCode = 214005)
public class GetTokenResp extends ZyCom_ResponseBody {
    private static final long serialVersionUID = 7689245671364819728L;
    @ByteField(index = 2)
    private String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
