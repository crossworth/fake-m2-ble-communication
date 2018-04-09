package com.zhuoyi.account.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AuthenticationService extends Service {
    private Authenticator mAuthenticator;

    public void onCreate() {
        this.mAuthenticator = new Authenticator(this);
    }

    public void onDestroy() {
    }

    public IBinder onBind(Intent intent) {
        return this.mAuthenticator.getIBinder();
    }
}
