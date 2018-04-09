package com.zhuoyi.system.network.serializer;

public class ZyCom_MessageHead {
    @ByteField(index = 7)
    public int code;
    @ByteField(index = 5)
    public long firstTransaction;
    @ByteField(index = 2)
    public int length;
    @ByteField(index = 4)
    public short reserved;
    @ByteField(index = 6)
    public long secondTransaction;
    @ByteField(index = 3)
    public byte type;
    @ByteField(index = 1)
    public byte version;
}
