package com.tencent.weiyun;

import android.content.Context;
import android.os.Bundle;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.BaseApi;
import com.tencent.connect.common.BaseApi.TempRequestListener;
import com.tencent.tauth.IRequestListener;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.UiError;
import com.tencent.utils.HttpUtils;
import com.tencent.utils.Util;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: ProGuard */
public class RecordManager extends BaseApi {
    public RecordManager(Context context, QQAuth qQAuth, QQToken qQToken) {
        super(context, qQAuth, qQToken);
    }

    public RecordManager(Context context, QQToken qQToken) {
        super(context, qQToken);
    }

    public void createRecord(String str, String str2, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager f4728b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        iUiListener.onComplete("");
                    } else {
                        iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                    }
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        composeCGIParams.putByteArray("value", Util.toHexString(str2).getBytes());
        HttpUtils.requestAsync(this.mToken, this.mContext, "https://graph.qq.com/weiyun/create_record", composeCGIParams, "POST", tempRequestListener);
    }

    public void deleteRecord(String str, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager f4730b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        iUiListener.onComplete("");
                    } else {
                        iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                    }
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        HttpUtils.requestAsync(this.mToken, this.mContext, "https://graph.qq.com/weiyun/delete_record", composeCGIParams, "GET", tempRequestListener);
    }

    public void getRecord(String str, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager f4732b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        iUiListener.onComplete(Util.hexToString(jSONObject.getJSONObject("data").getString("value")));
                        return;
                    }
                    iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        HttpUtils.requestAsync(this.mToken, this.mContext, "https://graph.qq.com/weiyun/get_record", composeCGIParams, "GET", tempRequestListener);
    }

    public void modifyRecord(String str, String str2, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager f4734b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        iUiListener.onComplete("");
                    } else {
                        iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                    }
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        composeCGIParams.putByteArray("value", Util.toHexString(str2).getBytes());
        HttpUtils.requestAsync(this.mToken, this.mContext, "https://graph.qq.com/weiyun/modify_record", composeCGIParams, "POST", tempRequestListener);
    }

    public void queryAllRecord(final IUiListener iUiListener) {
        HttpUtils.requestAsync(this.mToken, this.mContext, "https://graph.qq.com/weiyun/query_all_record", composeCGIParams(), "GET", new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager f4736b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                JSONObject jSONObject = (JSONObject) obj;
                try {
                    if (jSONObject.getInt("ret") == 0) {
                        List arrayList = new ArrayList();
                        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                        if (!jSONObject2.isNull("keys")) {
                            JSONArray jSONArray = jSONObject2.getJSONArray("keys");
                            for (int i = 0; i < jSONArray.length(); i++) {
                                arrayList.add(Util.hexToString(jSONArray.getJSONObject(i).getString("key")));
                            }
                        }
                        iUiListener.onComplete(arrayList);
                        return;
                    }
                    iUiListener.onError(new UiError(-4, jSONObject.toString(), null));
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        }));
    }

    public void checkRecord(String str, final IUiListener iUiListener) {
        Bundle composeCGIParams = composeCGIParams();
        IRequestListener tempRequestListener = new TempRequestListener(new IUiListener(this) {
            final /* synthetic */ RecordManager f4738b;

            public void onError(UiError uiError) {
                iUiListener.onError(uiError);
            }

            public void onComplete(Object obj) {
                try {
                    if (((JSONObject) obj).getInt("ret") == 0) {
                        iUiListener.onComplete(Boolean.TRUE);
                    } else {
                        iUiListener.onComplete(Boolean.FALSE);
                    }
                } catch (JSONException e) {
                    iUiListener.onError(new UiError(-4, e.getMessage(), null));
                }
            }

            public void onCancel() {
                iUiListener.onCancel();
            }
        });
        composeCGIParams.putString("key", Util.toHexString(str));
        HttpUtils.requestAsync(this.mToken, this.mContext, "https://graph.qq.com/weiyun/check_record", composeCGIParams, "GET", tempRequestListener);
    }
}
