package com.droi.sdk.core.priv;

import android.app.Activity;
import android.content.Intent;
import com.droi.sdk.DroiCallback;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.OAuthProvider;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth.Req;
import com.tencent.mm.sdk.modelmsg.SendAuth.Resp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Map;

public class C0947r extends OAuthProvider {
    public static final String f3088a = "Weixin";
    private static final String f3089b = "com.tencent.mm.sdk.modelmsg.SendAuth";
    private DroiCallback<Boolean> f3090c;
    private String f3091d;
    private String f3092e;
    private String f3093f;
    private IWXAPIEventHandler f3094g = new C09461(this);
    private IWXAPI f3095h;

    class C09461 implements IWXAPIEventHandler {
        final /* synthetic */ C0947r f3087a;

        C09461(C0947r c0947r) {
            this.f3087a = c0947r;
        }

        public void onReq(BaseReq baseReq) {
        }

        public void onResp(BaseResp baseResp) {
            switch (baseResp.errCode) {
                case -4:
                    if (this.f3087a.f3090c != null) {
                        this.f3087a.f3090c.result(Boolean.valueOf(false), new DroiError(DroiError.USER_NOT_AUTHORIZED, null));
                        return;
                    }
                    return;
                case -2:
                    if (this.f3087a.f3090c != null) {
                        this.f3087a.f3090c.result(Boolean.valueOf(false), new DroiError(DroiError.USER_CANCELED, null));
                        return;
                    }
                    return;
                case 0:
                    if (baseResp instanceof Resp) {
                        Resp resp = (Resp) baseResp;
                        if (resp.state != null && resp.state.equals(this.f3087a.f3092e)) {
                            this.f3087a.f3091d = resp.code;
                            if (this.f3087a.f3090c != null) {
                                this.f3087a.f3090c.result(Boolean.valueOf(true), new DroiError());
                                return;
                            }
                            return;
                        } else if (this.f3087a.f3090c != null) {
                            this.f3087a.f3090c.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, "State mismatch."));
                            return;
                        } else {
                            return;
                        }
                    } else if (this.f3087a.f3090c != null) {
                        this.f3087a.f3090c.result(Boolean.valueOf(false), new DroiError(DroiError.ERROR, "Response type mismatch."));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    private static boolean m2808a() {
        try {
            return Class.forName(f3089b) != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public String getId() {
        return this.f3091d;
    }

    public String getOAuthProviderName() {
        return f3088a;
    }

    public String getToken() {
        return null;
    }

    public void handleActivityResult(int i, int i2, Intent intent) {
        if (this.f3095h != null) {
            this.f3095h.handleIntent(intent, this.f3094g);
        }
    }

    public boolean isTokenValid() {
        return true;
    }

    protected DroiError requestToken(Activity activity, DroiCallback<Boolean> droiCallback) {
        this.f3090c = droiCallback;
        if (!C0947r.m2808a()) {
            throw new RuntimeException("Weixin SDK not found.");
        } else if (!C0907i.m2679a().m2686c()) {
            return new DroiError(DroiError.ERROR, "Weixin app id handler is not ready.");
        } else {
            Map a = C0907i.m2679a().m2684a(f3088a);
            if (a == null || a.size() == 0) {
                return new DroiError(DroiError.ERROR, "Weixin app id is not found.");
            }
            Object obj = a.get("AppId");
            if (obj == null) {
                return new DroiError(DroiError.ERROR, "Weixin app id is not defined.");
            }
            this.f3093f = (String) obj;
            this.f3095h = WXAPIFactory.createWXAPI(activity.getApplicationContext(), this.f3093f, true);
            this.f3095h.registerApp(this.f3093f);
            this.f3095h.handleIntent(activity.getIntent(), this.f3094g);
            byte[] bArr = new byte[4];
            new SecureRandom().nextBytes(bArr);
            int i = ByteBuffer.wrap(bArr).getInt();
            BaseReq req = new Req();
            req.scope = "snsapi_userinfo";
            String valueOf = String.valueOf(i);
            req.state = valueOf;
            this.f3092e = valueOf;
            this.f3095h.sendReq(req);
            return new DroiError();
        }
    }
}
