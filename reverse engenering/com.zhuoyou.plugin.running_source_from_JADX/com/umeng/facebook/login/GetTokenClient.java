package com.umeng.facebook.login;

import android.content.Context;
import android.os.Bundle;
import com.umeng.facebook.internal.NativeProtocol;
import com.umeng.facebook.internal.PlatformServiceClient;

final class GetTokenClient extends PlatformServiceClient {
    GetTokenClient(Context context, String applicationId) {
        super(context, 65536, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY, NativeProtocol.PROTOCOL_VERSION_20121101, applicationId);
    }

    protected void populateRequestBundle(Bundle data) {
    }
}
