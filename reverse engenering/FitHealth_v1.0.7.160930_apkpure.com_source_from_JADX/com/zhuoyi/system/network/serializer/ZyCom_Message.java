package com.zhuoyi.system.network.serializer;

public class ZyCom_Message {
    @ByteField(index = 1)
    public ZyCom_MessageHead head;
    @ByteField(index = 2)
    public Object message;
    public int retryCount = 0;
}
