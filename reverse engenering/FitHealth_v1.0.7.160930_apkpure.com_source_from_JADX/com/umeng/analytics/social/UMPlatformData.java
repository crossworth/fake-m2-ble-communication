package com.umeng.analytics.social;

import android.text.TextUtils;
import com.tencent.connect.common.Constants;
import com.umeng.analytics.C0919a;
import com.umeng.socialize.PlatformConfig.Renren;
import java.util.Locale;

public class UMPlatformData {
    private UMedia f3174a;
    private String f3175b = "";
    private String f3176c = "";
    private String f3177d;
    private GENDER f3178e;

    public enum GENDER {
        MALE(0) {
            public String toString() {
                return String.format(Locale.US, "Male:%d", new Object[]{Integer.valueOf(this.value)});
            }
        },
        FEMALE(1) {
            public String toString() {
                return String.format(Locale.US, "Female:%d", new Object[]{Integer.valueOf(this.value)});
            }
        };
        
        public int value;

        private GENDER(int i) {
            this.value = i;
        }
    }

    public enum UMedia {
        SINA_WEIBO {
            public String toString() {
                return "sina";
            }
        },
        TENCENT_WEIBO {
            public String toString() {
                return "tencent";
            }
        },
        TENCENT_QZONE {
            public String toString() {
                return Constants.SOURCE_QZONE;
            }
        },
        TENCENT_QQ {
            public String toString() {
                return "qq";
            }
        },
        WEIXIN_FRIENDS {
            public String toString() {
                return "wxsesion";
            }
        },
        WEIXIN_CIRCLE {
            public String toString() {
                return "wxtimeline";
            }
        },
        RENREN {
            public String toString() {
                return Renren.Name;
            }
        },
        DOUBAN {
            public String toString() {
                return "douban";
            }
        }
    }

    public UMPlatformData(UMedia uMedia, String str) {
        if (uMedia == null || TextUtils.isEmpty(str)) {
            C0939b.m3139b(C0919a.f3107d, "parameter is not valid");
            return;
        }
        this.f3174a = uMedia;
        this.f3175b = str;
    }

    public String getWeiboId() {
        return this.f3176c;
    }

    public void setWeiboId(String str) {
        this.f3176c = str;
    }

    public UMedia getMeida() {
        return this.f3174a;
    }

    public String getUsid() {
        return this.f3175b;
    }

    public String getName() {
        return this.f3177d;
    }

    public void setName(String str) {
        this.f3177d = str;
    }

    public GENDER getGender() {
        return this.f3178e;
    }

    public void setGender(GENDER gender) {
        this.f3178e = gender;
    }

    public boolean isValid() {
        if (this.f3174a == null || TextUtils.isEmpty(this.f3175b)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "UMPlatformData [meida=" + this.f3174a + ", usid=" + this.f3175b + ", weiboId=" + this.f3176c + ", name=" + this.f3177d + ", gender=" + this.f3178e + "]";
    }
}
