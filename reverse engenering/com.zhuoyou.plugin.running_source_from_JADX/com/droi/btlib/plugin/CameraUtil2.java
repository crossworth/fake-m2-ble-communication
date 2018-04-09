package com.droi.btlib.plugin;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CameraUtil2 {

    static class C06901 implements Comparator<Size> {
        C06901() {
        }

        public int compare(Size lhs, Size rhs) {
            if (lhs.width > rhs.width) {
                return 1;
            }
            if (lhs.width != rhs.width) {
                return -1;
            }
            if (lhs.height <= rhs.height) {
                return -1;
            }
            return 1;
        }
    }

    public static void initParams(Camera camera, Context context) {
        Parameters params = camera.getParameters();
        List<Size> sizes = getSupportedPreviewSizes(camera);
        List<Size> sizes2 = getSupportedPictureSizes(camera);
        if (sizes != null && sizes2 != null) {
            Size previewSize = null;
            float width = (float) context.getResources().getDisplayMetrics().widthPixels;
            float height = (float) context.getResources().getDisplayMetrics().heightPixels;
            for (Size size : sizes) {
                if (((float) size.width) / ((float) size.height) == height / width && (size.width > 1000 || size.height > 1000)) {
                    previewSize = size;
                    break;
                }
            }
            if (previewSize == null) {
                previewSize = (Size) sizes.get(sizes.size() - 1);
            }
            params.setPreviewFormat(17);
            params.setPreviewSize(previewSize.width, previewSize.height);
            params.setPictureSize(((Size) sizes2.get(sizes2.size() - 1)).width, ((Size) sizes2.get(sizes2.size() - 1)).height);
            camera.setParameters(params);
        }
    }

    private static List<Size> getSupportedPreviewSizes(Camera mCamera) {
        List<Size> support = mCamera.getParameters().getSupportedPreviewSizes();
        if (support == null) {
            return null;
        }
        sortList(support);
        return support;
    }

    private static List<Size> getSupportedPictureSizes(Camera mCamera) {
        List<Size> support = mCamera.getParameters().getSupportedPictureSizes();
        if (support == null) {
            return null;
        }
        sortList(support);
        return support;
    }

    private static void sortList(List<Size> list) {
        Collections.sort(list, new C06901());
    }
}
