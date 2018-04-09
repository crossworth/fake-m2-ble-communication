package com.droi.btlib.plugin;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.support.v4.widget.AutoScrollHelper;
import java.util.List;

public class CameraUtil {
    public static void initParams(Camera camera, int width, int height) {
        Parameters params = camera.getParameters();
        Size size = getCloselyPreSize(width, height, params.getSupportedPreviewSizes());
        params.setPreviewFormat(17);
        params.setPreviewSize(size.width, size.height);
        camera.setParameters(params);
    }

    private static Size getCloselyPreSize(int surfaceWidth, int surfaceHeight, List<Size> preSizeList) {
        int ReqTmpWidth;
        int ReqTmpHeight;
        if (surfaceWidth < surfaceHeight) {
            ReqTmpWidth = surfaceHeight;
            ReqTmpHeight = surfaceWidth;
        } else {
            ReqTmpWidth = surfaceWidth;
            ReqTmpHeight = surfaceHeight;
        }
        for (Size size : preSizeList) {
            if (size.width == ReqTmpWidth && size.height == ReqTmpHeight) {
                return size;
            }
        }
        float reqRatio = ((float) ReqTmpWidth) / ((float) ReqTmpHeight);
        float deltaRatioMin = AutoScrollHelper.NO_MAX;
        Size retSize = null;
        for (Size size2 : preSizeList) {
            float deltaRatio = Math.abs(reqRatio - (((float) size2.width) / ((float) size2.height)));
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size2;
            }
        }
        return retSize;
    }
}
