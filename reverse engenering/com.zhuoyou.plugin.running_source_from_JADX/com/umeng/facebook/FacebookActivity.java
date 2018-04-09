package com.umeng.facebook;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.umeng.facebook.internal.FacebookDialogFragment;
import com.umeng.facebook.internal.NativeProtocol;
import com.umeng.facebook.login.LoginFragment;
import com.umeng.socialize.common.ResContainer;

public class FacebookActivity extends FragmentActivity {
    private static String FRAGMENT_TAG = "SingleFragment";
    public static String PASS_THROUGH_CANCEL_ACTION = "PassThrough";
    private ResContainer f4874R = null;
    private Fragment singleFragment;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.f4874R = ResContainer.get(this);
        setContentView(this.f4874R.layout("com_facebook_activity_layout"));
        Intent intent = getIntent();
        if (PASS_THROUGH_CANCEL_ACTION.equals(intent.getAction())) {
            handlePassThroughError();
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag(FRAGMENT_TAG);
        if (fragment == null) {
            if (FacebookDialogFragment.TAG.equals(intent.getAction())) {
                Fragment dialogFragment = new FacebookDialogFragment();
                dialogFragment.setRetainInstance(true);
                dialogFragment.show(manager, FRAGMENT_TAG);
                fragment = dialogFragment;
            } else {
                fragment = new LoginFragment();
                fragment.setRetainInstance(true);
                manager.beginTransaction().add(this.f4874R.id("com_facebook_fragment_container"), fragment, FRAGMENT_TAG).commit();
            }
        }
        this.singleFragment = fragment;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.singleFragment != null) {
            this.singleFragment.onConfigurationChanged(newConfig);
        }
    }

    private void handlePassThroughError() {
        Intent requestIntent = getIntent();
        setResult(0, NativeProtocol.createProtocolResultIntent(requestIntent, null, NativeProtocol.getExceptionFromErrorData(NativeProtocol.getMethodArgumentsFromIntent(requestIntent))));
        finish();
    }
}