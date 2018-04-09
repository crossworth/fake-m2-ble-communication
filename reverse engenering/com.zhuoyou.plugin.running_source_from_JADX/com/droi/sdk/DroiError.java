package com.droi.sdk;

import android.util.SparseArray;

public class DroiError {
    public static final int BANDWIDTH_LIMIT_EXCEED = 1070019;
    public static final int CORE_NOT_INITIALIZED = 1070017;
    public static final int ERROR = 1070001;
    public static final int EXCEED_MAX_SIZE = 1070015;
    public static final int FILE_NOT_READY = 1070016;
    public static final int HTTP_SERVER_ERROR = 1070008;
    public static final int INTERNAL_SERVER_ERROR = 1070011;
    public static final int INVALID_PARAMETER = 1070012;
    public static final int NETWORK_NOT_AVAILABLE = 1070005;
    public static final int NO_PERMISSION = 1070013;
    public static final int OK = 0;
    public static final int SERVER_NOT_REACHABLE = 1070007;
    public static final int SERVICE_NOT_ALLOWED = 1070009;
    public static final int SERVICE_NOT_FOUND = 1070010;
    public static final int UNKNOWN_ERROR = 1070000;
    public static final int USER_ALREADY_EXISTS = 1070004;
    public static final int USER_CANCELED = 1070018;
    public static final int USER_DISABLE = 1070014;
    public static final int USER_NOT_AUTHORIZED = 1070006;
    public static final int USER_NOT_EXISTS = 1070002;
    public static final int USER_PASSWORD_INCORRECT = 1070003;
    private static SparseArray<String> f2258a = new SparseArray();
    private volatile int f2259b;
    private volatile String f2260c;
    private volatile String f2261d;

    static {
        f2258a.append(0, "OK");
        f2258a.append(UNKNOWN_ERROR, "Unknown error.");
        f2258a.append(ERROR, "Error.");
        f2258a.append(USER_NOT_EXISTS, "User is not exists.");
        f2258a.append(USER_PASSWORD_INCORRECT, "Password is not correct.");
        f2258a.append(USER_ALREADY_EXISTS, "User is already exists.");
        f2258a.append(NETWORK_NOT_AVAILABLE, "Network is not available.");
        f2258a.append(USER_NOT_AUTHORIZED, "User is not authorized.");
        f2258a.append(SERVER_NOT_REACHABLE, "Server is not reachable.");
        f2258a.append(HTTP_SERVER_ERROR, "Error happened in Server side.");
        f2258a.append(SERVICE_NOT_ALLOWED, "Service is not allowed.");
        f2258a.append(SERVICE_NOT_FOUND, "Service is not found.");
        f2258a.append(INTERNAL_SERVER_ERROR, "Internal server error.");
        f2258a.append(INVALID_PARAMETER, "Invalid parameters.");
        f2258a.append(NO_PERMISSION, "No needed permission.");
        f2258a.append(USER_DISABLE, "User is in disable state.");
        f2258a.append(EXCEED_MAX_SIZE, "Exceed max size.");
        f2258a.append(FILE_NOT_READY, "File is not ready.");
        f2258a.append(CORE_NOT_INITIALIZED, "DroiBaaS SDK is not initialized.");
        f2258a.append(USER_CANCELED, "User is canceled.");
        f2258a.append(BANDWIDTH_LIMIT_EXCEED, "Bandwidth limit exceed.");
    }

    public DroiError() {
        this(0, null, null);
    }

    public DroiError(int i, String str) {
        this(i, str, null);
    }

    public DroiError(int i, String str, String str2) {
        this.f2259b = i;
        this.f2261d = str;
        this.f2260c = str2;
    }

    public void copy(DroiError droiError) {
        if (droiError != null) {
            this.f2259b = droiError.f2259b;
            this.f2261d = droiError.f2261d;
            this.f2260c = droiError.f2260c;
        }
    }

    public String getAppendedMessage() {
        return this.f2261d;
    }

    public int getCode() {
        return this.f2259b;
    }

    public boolean isOk() {
        return this.f2259b == 0;
    }

    public void setAppendedMessage(String str) {
        this.f2261d = str;
    }

    public void setCode(int i) {
        this.f2259b = i;
    }

    public void setTicket(String str) {
        this.f2260c = str;
    }

    public String toString() {
        String str = (String) f2258a.get(this.f2259b, String.format("Error code: %d", new Object[]{Integer.valueOf(this.f2259b)}));
        if (!(this.f2259b == 0 || this.f2260c == null)) {
            str = str + " Ticket: " + this.f2260c;
        }
        return this.f2261d != null ? str + " " + this.f2261d : str;
    }
}
