package com.zhuoyou.plugin.selfupdate;

import com.tencent.stat.DeviceInfo;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class Header {
    public static final int HEADER_LENGTH = 28;
    public static final int TLV_HEADER_LENGTH = 84;
    public static final int XIP_NOTIFY = 3;
    public static final int XIP_REQUEST = 1;
    public static final int XIP_RESPONSE = 2;
    private byte basicVer = (byte) 1;
    private long firstTransaction;
    private int length = 0;
    private int messageCode;
    private short reserved = (short) 0;
    private long secondTransaction;
    private byte type = (byte) 1;

    public byte getBasicVer() {
        return this.basicVer;
    }

    public void setBasicVer(byte basicVer) {
        this.basicVer = basicVer;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte getType() {
        return this.type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public short getReserved() {
        return this.reserved;
    }

    public void setReserved(short reserved) {
        this.reserved = reserved;
    }

    public long getFirstTransaction() {
        return this.firstTransaction;
    }

    public void setFirstTransaction(long firstTransaction) {
        this.firstTransaction = firstTransaction;
    }

    public long getSecondTransaction() {
        return this.secondTransaction;
    }

    public void setSecondTransaction(long secondTransaction) {
        this.secondTransaction = secondTransaction;
    }

    public int getMessageCode() {
        return this.messageCode;
    }

    public void setMessageCode(int messageCode) {
        if (messageCode <= 0) {
            throw new RuntimeException("invalid message code.");
        }
        this.messageCode = messageCode;
    }

    public void setTransaction(UUID uuid) {
        this.firstTransaction = uuid.getMostSignificantBits();
        this.secondTransaction = uuid.getLeastSignificantBits();
    }

    public UUID getTransactionAsUUID() {
        return new UUID(this.firstTransaction, this.secondTransaction);
    }

    public void setHeaderType(byte header_type) {
        this.type = header_type;
    }

    public String toString() {
        JSONObject jsObject = new JSONObject();
        try {
            jsObject.put(DeviceInfo.TAG_VERSION, this.basicVer);
            jsObject.put("type", this.type);
            jsObject.put("msb", this.firstTransaction);
            jsObject.put("lsb", this.secondTransaction);
            jsObject.put("mcd", this.messageCode);
            return jsObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }
}
