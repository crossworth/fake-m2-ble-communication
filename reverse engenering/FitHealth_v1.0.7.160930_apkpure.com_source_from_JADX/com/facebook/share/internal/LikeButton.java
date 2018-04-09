package com.facebook.share.internal;

import android.content.Context;
import android.util.AttributeSet;
import com.facebook.FacebookButtonBase;
import com.facebook.internal.AnalyticsEvents;
import com.umeng.socialize.common.ResContainer;

public class LikeButton extends FacebookButtonBase {
    public LikeButton(Context context, boolean isLiked) {
        super(context, null, 0, 0, AnalyticsEvents.EVENT_LIKE_BUTTON_CREATE, 0);
        setSelected(isLiked);
    }

    public void setSelected(boolean selected) {
        super.setSelected(selected);
        updateForLikeStatus();
    }

    protected void configureButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super.configureButton(context, attrs, defStyleAttr, defStyleRes);
        updateForLikeStatus();
    }

    protected int getDefaultStyleResource() {
        return ResContainer.get(getContext()).style("com_facebook_button_like");
    }

    private void updateForLikeStatus() {
        if (isSelected()) {
            setCompoundDrawablesWithIntrinsicBounds(ResContainer.get(getContext()).drawable("com_facebook_button_like_icon_selected"), 0, 0, 0);
            setText(ResContainer.getString(getContext(), "com_facebook_like_button_liked"));
            return;
        }
        setCompoundDrawablesWithIntrinsicBounds(ResContainer.get(getContext()).drawable("com_facebook_button_icon"), 0, 0, 0);
        setText(ResContainer.getString(getContext(), "com_facebook_like_button_not_liked"));
    }
}
