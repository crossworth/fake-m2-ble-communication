package com.umeng.facebook.login;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.umeng.facebook.login.LoginClient.OnCompletedListener;
import com.umeng.facebook.login.LoginClient.Request;
import com.umeng.facebook.login.LoginClient.Result;
import com.umeng.socialize.common.ResContainer;

public class LoginFragment extends Fragment {
    private static final String EXTRA_REQUEST = "request";
    private static final String NULL_CALLING_PKG_ERROR_MSG = "Cannot call LoginFragment with a null calling package. This can occur if the launchMode of the caller is singleInstance.";
    static final String RESULT_KEY = "com.facebook.LoginFragment:Result";
    private static final String SAVED_LOGIN_CLIENT = "loginClient";
    private static final String TAG = "LoginFragment";
    private ResContainer f4875R;
    private String callingPackage;
    private LoginClient loginClient;
    private Request request;

    class C15321 implements OnCompletedListener {
        C15321() {
        }

        public void onCompleted(Result outcome) {
            LoginFragment.this.onLoginClientCompleted(outcome);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.f4875R = ResContainer.get(getActivity());
        if (savedInstanceState != null) {
            this.loginClient = (LoginClient) savedInstanceState.getParcelable(SAVED_LOGIN_CLIENT);
            this.loginClient.setFragment(this);
        } else {
            this.loginClient = new LoginClient((Fragment) this);
        }
        this.loginClient.setOnCompletedListener(new C15321());
        Activity activity = getActivity();
        if (activity != null) {
            initializeCallingPackage(activity);
            if (activity.getIntent() != null) {
                this.request = (Request) activity.getIntent().getParcelableExtra("request");
            }
        }
    }

    public void onDestroy() {
        this.loginClient.cancelCurrentHandler();
        super.onDestroy();
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(this.f4875R.layout("com_facebook_login_fragment"), container, false);
        this.loginClient.setBackgroundProcessingListener(new BackgroundProcessingListener() {
            public void onBackgroundProcessingStarted() {
                view.findViewById(LoginFragment.this.f4875R.id("com_facebook_login_activity_progress_bar")).setVisibility(0);
            }

            public void onBackgroundProcessingStopped() {
                view.findViewById(LoginFragment.this.f4875R.id("com_facebook_login_activity_progress_bar")).setVisibility(8);
            }
        });
        return view;
    }

    private void onLoginClientCompleted(Result outcome) {
        this.request = null;
        int resultCode = outcome.code == Code.CANCEL ? 0 : -1;
        Bundle bundle = new Bundle();
        bundle.putParcelable(RESULT_KEY, outcome);
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        if (isAdded()) {
            getActivity().setResult(resultCode, resultIntent);
            getActivity().finish();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.callingPackage == null) {
            Log.e(TAG, NULL_CALLING_PKG_ERROR_MSG);
            getActivity().finish();
            return;
        }
        this.loginClient.startOrContinueAuth(this.request);
    }

    public void onPause() {
        super.onPause();
        getActivity().findViewById(this.f4875R.id("com_facebook_login_activity_progress_bar")).setVisibility(8);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.loginClient.onActivityResult(requestCode, resultCode, data);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_LOGIN_CLIENT, this.loginClient);
    }

    private void initializeCallingPackage(Activity activity) {
        ComponentName componentName = activity.getCallingActivity();
        if (componentName != null) {
            this.callingPackage = componentName.getPackageName();
        }
    }

    static Bundle populateIntentExtras(Request request) {
        Bundle extras = new Bundle();
        extras.putParcelable("request", request);
        return extras;
    }
}