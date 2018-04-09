package com.umeng.facebook.login;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.umeng.facebook.AccessTokenSource;
import com.umeng.facebook.FacebookException;
import com.umeng.facebook.internal.NativeProtocol;
import com.umeng.facebook.internal.PlatformServiceClient.CompletedListener;
import com.umeng.facebook.internal.Utility;
import com.umeng.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.umeng.facebook.login.LoginClient.Request;
import com.umeng.facebook.login.LoginClient.Result;
import com.umeng.facebook.share.internal.ShareConstants;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class GetTokenLoginMethodHandler extends LoginMethodHandler {
    public static final Creator<GetTokenLoginMethodHandler> CREATOR = new C15273();
    private GetTokenClient getTokenClient;

    static class C15273 implements Creator {
        C15273() {
        }

        public GetTokenLoginMethodHandler createFromParcel(Parcel source) {
            return new GetTokenLoginMethodHandler(source);
        }

        public GetTokenLoginMethodHandler[] newArray(int size) {
            return new GetTokenLoginMethodHandler[size];
        }
    }

    GetTokenLoginMethodHandler(LoginClient loginClient) {
        super(loginClient);
    }

    String getNameForLogging() {
        return "get_token";
    }

    void cancel() {
        if (this.getTokenClient != null) {
            this.getTokenClient.cancel();
            this.getTokenClient = null;
        }
    }

    boolean tryAuthorize(final Request request) {
        this.getTokenClient = new GetTokenClient(this.loginClient.getActivity(), request.getApplicationId());
        if (!this.getTokenClient.start()) {
            return false;
        }
        this.loginClient.notifyBackgroundProcessingStart();
        this.getTokenClient.setCompletedListener(new CompletedListener() {
            public void completed(Bundle result) {
                GetTokenLoginMethodHandler.this.getTokenCompleted(request, result);
            }
        });
        return true;
    }

    void getTokenCompleted(Request request, Bundle result) {
        this.getTokenClient = null;
        this.loginClient.notifyBackgroundProcessingStop();
        if (result != null) {
            ArrayList<String> currentPermissions = result.getStringArrayList(NativeProtocol.EXTRA_PERMISSIONS);
            Set<String> permissions = request.getPermissions();
            if (currentPermissions == null || !(permissions == null || currentPermissions.containsAll(permissions))) {
                Set<String> newPermissions = new HashSet();
                for (String permission : permissions) {
                    if (!currentPermissions.contains(permission)) {
                        newPermissions.add(permission);
                    }
                }
                if (!newPermissions.isEmpty()) {
                    addLoggingExtra("new_permissions", TextUtils.join(",", newPermissions));
                }
                request.setPermissions(newPermissions);
            } else {
                complete(request, result);
                return;
            }
        }
        this.loginClient.tryNextHandler();
    }

    void onComplete(Request request, Bundle result) {
        this.loginClient.completeAndValidate(Result.createTokenResult(this.loginClient.getPendingRequest(), LoginMethodHandler.createAccessTokenFromNativeLogin(result, AccessTokenSource.FACEBOOK_APPLICATION_SERVICE, request.getApplicationId())));
    }

    @TargetApi(9)
    void complete(final Request request, final Bundle result) {
        String userId = result.getString(NativeProtocol.EXTRA_USER_ID);
        if (userId == null || userId.isEmpty()) {
            this.loginClient.notifyBackgroundProcessingStart();
            Utility.getGraphMeRequestWithCacheAsync(result.getString(NativeProtocol.EXTRA_ACCESS_TOKEN), new GraphMeRequestWithCacheCallback() {
                public void onSuccess(JSONObject userInfo) {
                    try {
                        result.putString(NativeProtocol.EXTRA_USER_ID, userInfo.getString(ShareConstants.WEB_DIALOG_PARAM_ID));
                        GetTokenLoginMethodHandler.this.onComplete(request, result);
                    } catch (JSONException ex) {
                        GetTokenLoginMethodHandler.this.loginClient.complete(Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", ex.getMessage()));
                    }
                }

                public void onFailure(FacebookException error) {
                    GetTokenLoginMethodHandler.this.loginClient.complete(Result.createErrorResult(GetTokenLoginMethodHandler.this.loginClient.getPendingRequest(), "Caught exception", error.getMessage()));
                }
            });
            return;
        }
        onComplete(request, result);
    }

    GetTokenLoginMethodHandler(Parcel source) {
        super(source);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
