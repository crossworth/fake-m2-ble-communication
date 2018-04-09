package com.zhuoyi.accoutn.dao;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.text.TextUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class UserInfo {
    public static boolean getUser(Context context) {
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.zhuoyou.account.android.samplesync");
        if (accounts == null || accounts.length < 1) {
            return false;
        }
        return true;
    }

    public static boolean getUserIsLogin(Context context) {
        Account[] accounts = AccountManager.get(context).getAccountsByType("com.zhuoyou.account.android.samplesync");
        if (accounts == null || accounts.length < 1) {
            return false;
        }
        return true;
    }

    public static JSONObject getUserInfo(Context context) {
        AccountManager mAccountManager = AccountManager.get(context);
        Account[] accounts = mAccountManager.getAccountsByType("com.zhuoyou.account.android.samplesync");
        if (accounts == null || accounts.length < 1) {
            return null;
        }
        String jsonString = mAccountManager.getUserData(accounts[0], "userInfo");
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        try {
            return new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String get_openid(Context context) {
        JSONObject jSONObject;
        JSONException e;
        String openid = null;
        String userInfo = null;
        try {
            AccountManager mAccountManager = AccountManager.get(context);
            Account[] accounts = mAccountManager.getAccountsByType("com.zhuoyou.account.android.samplesync");
            if (accounts != null && accounts.length >= 1) {
                userInfo = mAccountManager.getUserData(accounts[0], "userInfo");
            }
            if (!TextUtils.isEmpty(userInfo)) {
                JSONObject jsonObject = new JSONObject(userInfo);
                try {
                    openid = jsonObject.getString("openid");
                    jSONObject = jsonObject;
                } catch (JSONException e2) {
                    e = e2;
                    jSONObject = jsonObject;
                    e.printStackTrace();
                    return openid;
                }
            }
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            return openid;
        }
        return openid;
    }
}
