package com.umeng.facebook.internal;

import com.umeng.facebook.FacebookException;
import com.umeng.facebook.share.internal.ShareConstants;
import com.zhuoyou.plugin.running.tools.Tools;
import org.json.JSONArray;
import org.json.JSONObject;

public class GraphUtil {
    private static final String[] dateFormats = new String[]{"yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss", Tools.BIRTH_FORMAT};

    public static JSONObject createOpenGraphObjectForPost(String type, String title, String imageUrl, String url, String description, JSONObject objectProperties, String id) {
        JSONObject openGraphObject = new JSONObject();
        if (type != null) {
            try {
                openGraphObject.put("type", type);
            } catch (Throwable e) {
                throw new FacebookException("An error occurred while setting up the graph object", e);
            }
        }
        openGraphObject.put("title", title);
        if (imageUrl != null) {
            JSONObject imageUrlObject = new JSONObject();
            imageUrlObject.put("url", imageUrl);
            JSONArray imageUrls = new JSONArray();
            imageUrls.put(imageUrlObject);
            openGraphObject.put("image", imageUrls);
        }
        openGraphObject.put("url", url);
        openGraphObject.put("description", description);
        openGraphObject.put(NativeProtocol.OPEN_GRAPH_CREATE_OBJECT_KEY, true);
        if (objectProperties != null) {
            openGraphObject.put("data", objectProperties);
        }
        if (id != null) {
            openGraphObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, id);
        }
        return openGraphObject;
    }
}
