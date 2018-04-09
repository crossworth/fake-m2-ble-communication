package com.umeng.facebook.share.widget;

import android.app.Activity;
import android.os.Bundle;
import com.umeng.facebook.FacebookCallback;
import com.umeng.facebook.internal.AppCall;
import com.umeng.facebook.internal.CallbackManagerImpl;
import com.umeng.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.umeng.facebook.internal.DialogFeature;
import com.umeng.facebook.internal.DialogPresenter;
import com.umeng.facebook.internal.DialogPresenter.ParameterProvider;
import com.umeng.facebook.internal.FacebookDialogBase;
import com.umeng.facebook.share.Sharer;
import com.umeng.facebook.share.Sharer.Result;
import com.umeng.facebook.share.internal.LegacyNativeDialogParameters;
import com.umeng.facebook.share.internal.MessageDialogFeature;
import com.umeng.facebook.share.internal.NativeDialogParameters;
import com.umeng.facebook.share.internal.OpenGraphMessageDialogFeature;
import com.umeng.facebook.share.internal.ShareContentValidation;
import com.umeng.facebook.share.internal.ShareInternalUtility;
import com.umeng.facebook.share.model.ShareContent;
import com.umeng.facebook.share.model.ShareLinkContent;
import com.umeng.facebook.share.model.ShareOpenGraphContent;
import com.umeng.facebook.share.model.SharePhotoContent;
import com.umeng.facebook.share.model.ShareVideoContent;
import java.util.ArrayList;
import java.util.List;

public final class MessageDialog extends FacebookDialogBase<ShareContent, Result> implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.Message.toRequestCode();
    private boolean shouldFailOnDataError = false;

    private class NativeHandler extends ModeHandler {
        private NativeHandler() {
            super();
        }

        public boolean canShow(ShareContent shareContent) {
            return shareContent != null && MessageDialog.canShow(shareContent.getClass());
        }

        public AppCall createAppCall(final ShareContent content) {
            ShareContentValidation.validateForMessage(content);
            final AppCall appCall = MessageDialog.this.createBaseAppCall();
            final boolean shouldFailOnDataError = MessageDialog.this.getShouldFailOnDataError();
            Activity activity = MessageDialog.this.getActivityContext();
            DialogPresenter.setupAppCallForNativeDialog(appCall, new ParameterProvider() {
                public Bundle getParameters() {
                    return NativeDialogParameters.create(appCall.getCallId(), content, shouldFailOnDataError);
                }

                public Bundle getLegacyParameters() {
                    return LegacyNativeDialogParameters.create(appCall.getCallId(), content, shouldFailOnDataError);
                }
            }, MessageDialog.getFeature(content.getClass()));
            return appCall;
        }
    }

    public static void show(Activity activity, ShareContent shareContent) {
        new MessageDialog(activity).show(shareContent);
    }

    public static boolean canShow(Class<? extends ShareContent> contentType) {
        DialogFeature feature = getFeature(contentType);
        return feature != null && DialogPresenter.canPresentNativeDialogWithFeature(feature);
    }

    public MessageDialog(Activity activity) {
        super(activity, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    MessageDialog(Activity activity, int requestCode) {
        super(activity, requestCode);
        ShareInternalUtility.registerStaticShareCallback(requestCode);
    }

    protected void registerCallbackImpl(CallbackManagerImpl callbackManager, FacebookCallback<Result> callback) {
        ShareInternalUtility.registerSharerCallback(getRequestCode(), callbackManager, callback);
    }

    public boolean getShouldFailOnDataError() {
        return this.shouldFailOnDataError;
    }

    public void setShouldFailOnDataError(boolean shouldFailOnDataError) {
        this.shouldFailOnDataError = shouldFailOnDataError;
    }

    protected AppCall createBaseAppCall() {
        return new AppCall(getRequestCode());
    }

    protected List<ModeHandler> getOrderedModeHandlers() {
        ArrayList<ModeHandler> handlers = new ArrayList();
        handlers.add(new NativeHandler());
        return handlers;
    }

    private static DialogFeature getFeature(Class<? extends ShareContent> contentType) {
        if (ShareLinkContent.class.isAssignableFrom(contentType)) {
            return MessageDialogFeature.MESSAGE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom(contentType)) {
            return MessageDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom(contentType)) {
            return MessageDialogFeature.VIDEO;
        }
        if (ShareOpenGraphContent.class.isAssignableFrom(contentType)) {
            return OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG;
        }
        return null;
    }
}
