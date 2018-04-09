package com.zhuoyou.plugin.cloud;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.PersonalConfig;
import com.zhuoyou.plugin.running.Tools;
import com.zhuoyou.plugin.selfupdate.TerminalInfo;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.HashMap;

public class CustomInfoSync {
    final int NO_VALUE = 100000;
    Handler hander;
    TerminalInfo info;
    private boolean is_debug = true;
    Context mContext;
    PersonalConfig mPersonalConfig;

    public CustomInfoSync(Context context, Handler hander) {
        this.mContext = context;
        this.mPersonalConfig = Tools.getPersonalConfig();
        this.info = TerminalInfo.generateTerminalInfo(this.mContext);
        this.hander = hander;
    }

    public void postCustomInfo() {
        HashMap<String, String> params = new HashMap();
        String openid = Tools.getOpenId(this.mContext);
        String username = Tools.getUsrName(this.mContext);
        if (username.equals("")) {
            username = Tools.getLoginName(this.mContext);
        }
        int headId = Tools.getHead(this.mContext);
        int sex = this.mPersonalConfig.getSex();
        int birthday = this.mPersonalConfig.getYear();
        String signature = Tools.getSignature(this.mContext);
        String likeSportIndex = Tools.getLikeSportsIndex(this.mContext);
        String weight = this.mPersonalConfig.getWeight();
        int height = this.mPersonalConfig.getHeight();
        int step = Tools.getPersonalGoal().mGoalSteps;
        int calorie = Tools.getPersonalGoal().mGoalCalories;
        String qq = "";
        String weibo = "";
        String phone = Tools.getPhoneNum(this.mContext);
        String email = Tools.getEmail(this.mContext);
        String uploadBuffer = "";
        if (headId == 10000) {
            uploadBuffer = creatHeadData("/Running/download/custom");
        } else if (headId == 1000) {
            uploadBuffer = creatHeadData("/Running/download/logo");
        }
        int proviceIndex = Tools.getProviceIndex(this.mContext);
        int countyIndex = Tools.getCityIndex(this.mContext);
        String consigneeName = Tools.getConsigneeName(this.mContext);
        String consigneePhone = Tools.getConsigneePhone(this.mContext);
        String consigneeLocation = Tools.getConsigneeAddress(this.mContext);
        params.put("account", openid);
        params.put("imsi", this.info.getImsi());
        params.put("name", username);
        params.put("headimgId", Integer.toString(headId));
        params.put(DataBaseContants.CONF_SEX, Integer.toString(sex));
        params.put(SocializeProtocolConstants.PROTOCOL_KEY_BIRTHDAY, Integer.toString(birthday));
        params.put("signature", signature);
        params.put("favoriteSport", likeSportIndex);
        params.put(DataBaseContants.CONF_WEIGHT, weight.split("\\.")[0]);
        if (weight.split("\\.").length < 2) {
            params.put("weightN", "0");
        } else {
            params.put("weightN", weight.split("\\.")[1]);
        }
        params.put(DataBaseContants.CONF_HEIGHT, Integer.toString(height));
        params.put("step", Integer.toString(step));
        params.put(DataBaseContants.GPS_CALORIE, Integer.toString(calorie));
        params.put("qq", qq);
        params.put("weibo", weibo);
        params.put("phone", phone);
        params.put("email", email);
        params.put("location", Integer.toString(proviceIndex));
        params.put("county", Integer.toString(countyIndex));
        params.put("headImg", uploadBuffer);
        params.put("consigneeName", consigneeName);
        params.put("consigneePhone", consigneePhone);
        params.put("consigneeLocation", consigneeLocation);
        new GetDataFromNet(NetMsgCode.postCustomInfo, this.hander, params, this.mContext).execute(new Object[]{NetMsgCode.URL});
        if (this.is_debug) {
            Log.d("txhlog", "postCustomInfo ");
        }
    }

    private String creatHeadData(String path) {
        Exception e;
        String uploadBuffer = "";
        try {
            FileInputStream fis = new FileInputStream(Tools.getSDPath() + path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            while (true) {
                int count = fis.read(buffer);
                if (count >= 0) {
                    baos.write(buffer, 0, count);
                } else {
                    String uploadBuffer2 = new String(Base64.encode(baos.toByteArray(), 0));
                    try {
                        fis.close();
                        return uploadBuffer2;
                    } catch (Exception e2) {
                        e = e2;
                        uploadBuffer = uploadBuffer2;
                        e.printStackTrace();
                        return uploadBuffer;
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
            e.printStackTrace();
            return uploadBuffer;
        }
    }

    public void getCustomInfo() {
        HashMap<String, String> params = new HashMap();
        params.put("account", Tools.getOpenId(this.mContext));
        params.put("imsi", this.info.getImsi());
        new GetDataFromNet(NetMsgCode.getCustomInfo, this.hander, params, this.mContext).execute(new Object[]{NetMsgCode.URL});
        if (this.is_debug) {
            Log.d("txhlog", "getCustomInfo ");
        }
    }
}
