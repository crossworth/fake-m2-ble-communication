package com.zhuoyou.plugin.running.bean;

import android.os.Bundle;
import com.umeng.facebook.share.internal.ShareConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Notice {
    private int action;
    private Bundle bundle = new Bundle();
    private boolean cancel;
    private boolean clean;
    private String content = "";
    private int id;
    private boolean light;
    private boolean sound;
    private String ticker = "";
    private String title = "";
    private boolean vibrate;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public boolean isSound() {
        return this.sound;
    }

    public void setSound(boolean sound) {
        this.sound = sound;
    }

    public boolean isVibrate() {
        return this.vibrate;
    }

    public void setVibrate(boolean vibrate) {
        this.vibrate = vibrate;
    }

    public boolean isLight() {
        return this.light;
    }

    public void setLight(boolean light) {
        this.light = light;
    }

    public boolean isClean() {
        return this.clean;
    }

    public void setClean(boolean clean) {
        this.clean = clean;
    }

    public boolean isCancel() {
        return this.cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public int getAction() {
        return this.action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public Bundle getBundle() {
        return this.bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public static Notice getNoticeByJson(String json) {
        boolean z = true;
        try {
            boolean z2;
            JSONObject JSON = new JSONObject(json);
            Notice notice = new Notice();
            notice.setId(JSON.optInt(ShareConstants.WEB_DIALOG_PARAM_ID));
            notice.setTitle(JSON.optString("title"));
            notice.setContent(JSON.optString("content"));
            notice.setTicker(JSON.optString("ticker"));
            if (JSON.optInt("sound") == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            notice.setSound(z2);
            if (JSON.optInt("vibrate") == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            notice.setVibrate(z2);
            if (JSON.optInt("light") == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            notice.setLight(z2);
            if (JSON.optInt("clean") == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            notice.setClean(z2);
            if (JSON.optInt("cancel") != 1) {
                z = false;
            }
            notice.setCancel(z);
            notice.setAction(JSON.optInt("action"));
            JSONArray array = JSON.optJSONArray("bundle");
            if (array == null) {
                return notice;
            }
            Bundle bundle = new Bundle();
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.optJSONObject(i);
                bundle.putString(object.getString("key"), object.getString("value"));
            }
            notice.setBundle(bundle);
            return notice;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return "Notice{id='" + this.id + '\'' + ", title='" + this.title + '\'' + ", content='" + this.content + '\'' + ", ticker='" + this.ticker + '\'' + ", sound=" + this.sound + ", vibrate=" + this.vibrate + ", light=" + this.light + ", clean=" + this.clean + ", action=" + this.action + ", bundle=" + this.bundle + '}';
    }
}
