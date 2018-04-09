package com.zhuoyi.system.network.serializer;

import java.io.Serializable;

public class ZyCom_ResponseBody implements Serializable {
    private static final long serialVersionUID = 1;
    @ByteField(description = "错误代码", index = 0)
    private int errorCode;
    @ByteField(description = "提示消息内容", index = 1)
    private String errorMessage;

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return "ZyCom_ResponseBody [errorCode=" + this.errorCode + ", errorMessage=" + this.errorMessage + "]";
    }
}
