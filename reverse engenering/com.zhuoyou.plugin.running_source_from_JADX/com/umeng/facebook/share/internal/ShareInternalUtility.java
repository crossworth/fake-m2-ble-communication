package com.umeng.facebook.share.internal;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import com.umeng.facebook.CallbackManager;
import com.umeng.facebook.FacebookCallback;
import com.umeng.facebook.FacebookException;
import com.umeng.facebook.FacebookGraphResponseException;
import com.umeng.facebook.FacebookOperationCanceledException;
import com.umeng.facebook.FacebookSdk;
import com.umeng.facebook.GraphResponse;
import com.umeng.facebook.internal.AnalyticsEvents;
import com.umeng.facebook.internal.AppCall;
import com.umeng.facebook.internal.CallbackManagerImpl;
import com.umeng.facebook.internal.CallbackManagerImpl.Callback;
import com.umeng.facebook.internal.NativeAppCallAttachmentStore;
import com.umeng.facebook.internal.NativeAppCallAttachmentStore.Attachment;
import com.umeng.facebook.internal.NativeProtocol;
import com.umeng.facebook.internal.Utility;
import com.umeng.facebook.internal.Utility.Mapper;
import com.umeng.facebook.share.Sharer.Result;
import com.umeng.facebook.share.internal.OpenGraphJSONUtility.PhotoJSONProcessor;
import com.umeng.facebook.share.model.ShareOpenGraphAction;
import com.umeng.facebook.share.model.ShareOpenGraphContent;
import com.umeng.facebook.share.model.SharePhoto;
import com.umeng.facebook.share.model.SharePhotoContent;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ShareInternalUtility {
    private static final String CAPTION_PARAM = "caption";
    private static final String MY_ACTION_FORMAT = "me/%s";
    private static final String MY_FEED = "me/feed";
    private static final String MY_OBJECTS_FORMAT = "me/objects/%s";
    private static final String MY_PHOTOS = "me/photos";
    private static final String MY_STAGING_RESOURCES = "me/staging_resources";
    private static final String OBJECT_PARAM = "object";
    private static final String PICTURE_PARAM = "picture";
    private static final String STAGING_PARAM = "file";

    static class C15445 implements Mapper<Attachment, String> {
        C15445() {
        }

        public String apply(Attachment item) {
            return item.getAttachmentUrl();
        }
    }

    static class C15467 implements PhotoJSONProcessor {
        C15467() {
        }

        public JSONObject toJSONObject(SharePhoto photo) {
            Uri photoUri = photo.getImageUrl();
            JSONObject photoJSONObject = new JSONObject();
            try {
                photoJSONObject.put("url", photoUri.toString());
                return photoJSONObject;
            } catch (Throwable e) {
                throw new FacebookException("Unable to attach images", e);
            }
        }
    }

    public static void invokeCallbackWithError(FacebookCallback<Result> callback, String error) {
        invokeOnErrorCallback((FacebookCallback) callback, error);
    }

    public static String getNativeDialogCompletionGesture(Bundle result) {
        if (result.containsKey(NativeProtocol.RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY)) {
            return result.getString(NativeProtocol.RESULT_ARGS_DIALOG_COMPLETION_GESTURE_KEY);
        }
        return result.getString(NativeProtocol.EXTRA_DIALOG_COMPLETION_GESTURE_KEY);
    }

    public static String getShareDialogPostId(Bundle result) {
        if (result.containsKey(ShareConstants.RESULT_POST_ID)) {
            return result.getString(ShareConstants.RESULT_POST_ID);
        }
        if (result.containsKey(ShareConstants.EXTRA_RESULT_POST_ID)) {
            return result.getString(ShareConstants.EXTRA_RESULT_POST_ID);
        }
        return result.getString(ShareConstants.WEB_DIALOG_RESULT_PARAM_POST_ID);
    }

    public static boolean handleActivityResult(int requestCode, int resultCode, Intent data, ResultProcessor resultProcessor) {
        AppCall appCall = getAppCallFromActivityResult(requestCode, resultCode, data);
        if (appCall == null) {
            return false;
        }
        NativeAppCallAttachmentStore.cleanupAttachmentsForCall(appCall.getCallId());
        if (resultProcessor == null) {
            return true;
        }
        FacebookException exception = NativeProtocol.getExceptionFromErrorData(NativeProtocol.getErrorDataFromResultIntent(data));
        if (exception == null) {
            resultProcessor.onSuccess(appCall, NativeProtocol.getSuccessResultsFromIntent(data));
            return true;
        } else if (exception instanceof FacebookOperationCanceledException) {
            resultProcessor.onCancel(appCall);
            return true;
        } else {
            resultProcessor.onError(appCall, exception);
            return true;
        }
    }

    public static ResultProcessor getShareResultProcessor(final FacebookCallback<Result> callback) {
        return new ResultProcessor(callback) {
            public void onSuccess(AppCall appCall, Bundle results) {
                if (results != null) {
                    String gesture = ShareInternalUtility.getNativeDialogCompletionGesture(results);
                    if (gesture == null || "post".equalsIgnoreCase(gesture)) {
                        ShareInternalUtility.invokeOnSuccessCallback(callback, ShareInternalUtility.getShareDialogPostId(results));
                    } else if ("cancel".equalsIgnoreCase(gesture)) {
                        ShareInternalUtility.invokeOnCancelCallback(callback);
                    } else {
                        ShareInternalUtility.invokeOnErrorCallback(callback, new FacebookException(NativeProtocol.ERROR_UNKNOWN_ERROR));
                    }
                }
            }

            public void onCancel(AppCall appCall) {
                ShareInternalUtility.invokeOnCancelCallback(callback);
            }

            public void onError(AppCall appCall, FacebookException error) {
                ShareInternalUtility.invokeOnErrorCallback(callback, error);
            }
        };
    }

    private static AppCall getAppCallFromActivityResult(int requestCode, int resultCode, Intent data) {
        UUID callId = NativeProtocol.getCallIdFromIntent(data);
        if (callId == null) {
            return null;
        }
        return AppCall.finishPendingCall(callId, requestCode);
    }

    public static void registerStaticShareCallback(final int requestCode) {
        CallbackManagerImpl.registerStaticCallback(requestCode, new Callback() {
            public boolean onActivityResult(int resultCode, Intent data) {
                return ShareInternalUtility.handleActivityResult(requestCode, resultCode, data, ShareInternalUtility.getShareResultProcessor(null));
            }
        });
    }

    public static void registerSharerCallback(final int requestCode, CallbackManager callbackManager, final FacebookCallback<Result> callback) {
        if (callbackManager instanceof CallbackManagerImpl) {
            ((CallbackManagerImpl) callbackManager).registerCallback(requestCode, new Callback() {
                public boolean onActivityResult(int resultCode, Intent data) {
                    return ShareInternalUtility.handleActivityResult(requestCode, resultCode, data, ShareInternalUtility.getShareResultProcessor(callback));
                }
            });
            return;
        }
        throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
    }

    public static List<String> getPhotoUrls(SharePhotoContent photoContent, final UUID appCallId) {
        if (photoContent != null) {
            List<SharePhoto> photos = photoContent.getPhotos();
            if (photos != null) {
                List<Attachment> attachments = Utility.map(photos, new Mapper<SharePhoto, Attachment>() {
                    public Attachment apply(SharePhoto item) {
                        return ShareInternalUtility.getAttachment(appCallId, item);
                    }
                });
                List<String> attachmentUrls = Utility.map(attachments, new C15445());
                NativeAppCallAttachmentStore.addAttachments(attachments);
                return attachmentUrls;
            }
        }
        return null;
    }

    public static JSONObject toJSONObjectForCall(final UUID callId, ShareOpenGraphAction action) throws JSONException {
        final ArrayList<Attachment> attachments = new ArrayList();
        JSONObject actionJSON = OpenGraphJSONUtility.toJSONObject(action, new PhotoJSONProcessor() {
            public JSONObject toJSONObject(SharePhoto photo) {
                Attachment attachment = ShareInternalUtility.getAttachment(callId, photo);
                if (attachment == null) {
                    return null;
                }
                attachments.add(attachment);
                JSONObject photoJSONObject = new JSONObject();
                try {
                    photoJSONObject.put("url", attachment.getAttachmentUrl());
                    if (!photo.getUserGenerated()) {
                        return photoJSONObject;
                    }
                    photoJSONObject.put(NativeProtocol.IMAGE_USER_GENERATED_KEY, true);
                    return photoJSONObject;
                } catch (Throwable e) {
                    throw new FacebookException("Unable to attach images", e);
                }
            }
        });
        NativeAppCallAttachmentStore.addAttachments(attachments);
        return actionJSON;
    }

    public static JSONObject toJSONObjectForWeb(ShareOpenGraphContent shareOpenGraphContent) throws JSONException {
        return OpenGraphJSONUtility.toJSONObject(shareOpenGraphContent.getAction(), new C15467());
    }

    public static JSONArray removeNamespacesFromOGJsonArray(JSONArray jsonArray, boolean requireNamespace) throws JSONException {
        JSONArray newArray = new JSONArray();
        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONArray) {
                value = removeNamespacesFromOGJsonArray((JSONArray) value, requireNamespace);
            } else if (value instanceof JSONObject) {
                value = removeNamespacesFromOGJsonObject((JSONObject) value, requireNamespace);
            }
            newArray.put(value);
        }
        return newArray;
    }

    public static JSONObject removeNamespacesFromOGJsonObject(JSONObject jsonObject, boolean requireNamespace) {
        if (jsonObject == null) {
            return null;
        }
        try {
            JSONObject newJsonObject = new JSONObject();
            JSONObject data = new JSONObject();
            JSONArray names = jsonObject.names();
            for (int i = 0; i < names.length(); i++) {
                String key = names.getString(i);
                Object value = jsonObject.get(key);
                if (value instanceof JSONObject) {
                    value = removeNamespacesFromOGJsonObject((JSONObject) value, true);
                } else if (value instanceof JSONArray) {
                    value = removeNamespacesFromOGJsonArray((JSONArray) value, true);
                }
                Pair<String, String> fieldNameAndNamespace = getFieldNameAndNamespaceFromFullName(key);
                String namespace = fieldNameAndNamespace.first;
                String fieldName = fieldNameAndNamespace.second;
                if (requireNamespace) {
                    if (namespace != null && namespace.equals("fbsdk")) {
                        newJsonObject.put(key, value);
                    } else if (namespace == null || namespace.equals("og")) {
                        newJsonObject.put(fieldName, value);
                    } else {
                        data.put(fieldName, value);
                    }
                } else if (namespace == null || !namespace.equals("fb")) {
                    newJsonObject.put(fieldName, value);
                } else {
                    newJsonObject.put(key, value);
                }
            }
            if (data.length() <= 0) {
                return newJsonObject;
            }
            newJsonObject.put("data", data);
            return newJsonObject;
        } catch (JSONException e) {
            throw new FacebookException("Failed to create json object from share content");
        }
    }

    public static Pair<String, String> getFieldNameAndNamespaceFromFullName(String fullName) {
        String fieldName;
        String namespace = null;
        int index = fullName.indexOf(58);
        if (index == -1 || fullName.length() <= index + 1) {
            fieldName = fullName;
        } else {
            namespace = fullName.substring(0, index);
            fieldName = fullName.substring(index + 1);
        }
        return new Pair(namespace, fieldName);
    }

    private ShareInternalUtility() {
    }

    private static Attachment getAttachment(UUID callId, SharePhoto photo) {
        Bitmap bitmap = photo.getBitmap();
        Uri photoUri = photo.getImageUrl();
        if (bitmap != null) {
            return NativeAppCallAttachmentStore.createAttachment(callId, bitmap);
        }
        if (photoUri != null) {
            return NativeAppCallAttachmentStore.createAttachment(callId, photoUri);
        }
        return null;
    }

    static void invokeOnCancelCallback(FacebookCallback<Result> callback) {
        logShareResult(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_CANCELLED, null);
        if (callback != null) {
            callback.onCancel();
        }
    }

    static void invokeOnSuccessCallback(FacebookCallback<Result> callback, String postId) {
        logShareResult(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_SUCCEEDED, null);
        if (callback != null) {
            callback.onSuccess(new Result(postId));
        }
    }

    static void invokeOnErrorCallback(FacebookCallback<Result> callback, GraphResponse response, String message) {
        logShareResult("error", message);
        if (callback != null) {
            callback.onError(new FacebookGraphResponseException(response, message));
        }
    }

    static void invokeOnErrorCallback(FacebookCallback<Result> callback, String message) {
        logShareResult("error", message);
        if (callback != null) {
            callback.onError(new FacebookException(message));
        }
    }

    static void invokeOnErrorCallback(FacebookCallback<Result> callback, FacebookException ex) {
        logShareResult("error", ex.getMessage());
        if (callback != null) {
            callback.onError(ex);
        }
    }

    private static void logShareResult(String shareOutcome, String errorMessage) {
        Context context = FacebookSdk.getApplicationContext();
        Bundle parameters = new Bundle();
        parameters.putString(AnalyticsEvents.PARAMETER_SHARE_OUTCOME, shareOutcome);
        if (errorMessage != null) {
            parameters.putString(AnalyticsEvents.PARAMETER_SHARE_ERROR_MESSAGE, errorMessage);
        }
    }
}
