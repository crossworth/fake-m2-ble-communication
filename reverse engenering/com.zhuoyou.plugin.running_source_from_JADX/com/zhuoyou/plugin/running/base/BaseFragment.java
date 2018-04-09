package com.zhuoyou.plugin.running.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.droi.sdk.analytics.DroiAnalytics;
import com.zhuoyou.plugin.running.bean.EventLogout;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public abstract class BaseFragment extends Fragment {
    protected boolean isPrepared;
    protected boolean isVisible;
    protected boolean isVisibled;

    protected abstract void initView(View view);

    public final void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        onViewInited();
        this.isPrepared = true;
        if (this.isVisible) {
            this.isVisibled = true;
            onVisible();
        }
    }

    @Subscribe
    public void onEventMainThread(EventLogout event) {
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public final void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisible = isVisibleToUser;
        if (!this.isPrepared) {
            return;
        }
        if (!isVisibleToUser || this.isVisibled) {
            this.isVisibled = false;
            onInVisible();
            return;
        }
        this.isVisibled = true;
        onVisible();
    }

    public void onResume() {
        super.onResume();
        if (this.isVisible && this.isPrepared && !this.isVisibled) {
            this.isVisibled = true;
            onVisible();
        }
        DroiAnalytics.onFragmentStart(getContext(), getClass().getSimpleName());
    }

    public void onPause() {
        super.onPause();
        if (this.isVisible && this.isPrepared) {
            this.isVisibled = false;
            onInVisible();
        }
        DroiAnalytics.onFragmentEnd(getContext(), getClass().getSimpleName());
    }

    protected void onViewInited() {
    }

    protected void onVisible() {
    }

    protected void onInVisible() {
    }
}
