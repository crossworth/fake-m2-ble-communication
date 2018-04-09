package com.zhuoyou.plugin.album;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.zhuoyou.plugin.running.HomePageItemFragment;
import com.zhuoyou.plugin.running.Tools;
import java.lang.ref.WeakReference;
import java.util.List;

public class AsyncLoadImageTask extends AsyncTask<Integer, Void, Bitmap> {
    private int album;
    private int height;
    private final WeakReference<ImageView> imageViewReference;
    private List<String> mLists = null;
    private String thumbnailPath = null;
    private String url = null;
    private int width;

    public AsyncLoadImageTask(ImageView imageview, List<String> path, int length, String thumbnailPath) {
        this.imageViewReference = new WeakReference(imageview);
        this.width = length;
        this.height = length;
        this.mLists = path;
        this.thumbnailPath = thumbnailPath;
        this.album = 1;
    }

    public AsyncLoadImageTask(ImageView imageview, String path, int w, int h, String thumbnailPath) {
        this.imageViewReference = new WeakReference(imageview);
        this.width = w;
        this.height = h;
        setUrl(path);
        this.thumbnailPath = thumbnailPath;
        this.album = 2;
    }

    public AsyncLoadImageTask(ImageView imageview, String path, int w, String thumbnailPath) {
        this.imageViewReference = new WeakReference(imageview);
        this.width = w;
        setUrl(path);
        this.thumbnailPath = thumbnailPath;
        this.album = 3;
    }

    protected Bitmap doInBackground(Integer... params) {
        if (this.album == 1) {
            setUrl((String) this.mLists.get(params[0].intValue()));
        }
        if (!Tools.fileIsExists(getUrl())) {
            return null;
        }
        Bitmap bitmap;
        if (this.album == 1) {
            bitmap = BitmapUtils.decodeSampledBitmapFromFd2(getUrl(), this.width, this.height, 3);
            SportsAlbum.gridviewBitmapCaches.put(this.mLists.get(params[0].intValue()), bitmap);
            return bitmap;
        } else if (this.album == 2) {
            return BitmapUtils.decodeSampledBitmapFromFd2(getUrl(), this.width, this.height, 1);
        } else {
            bitmap = BitmapUtils.decodeSampledBitmapFromFd(getUrl(), this.width, 1);
            HomePageItemFragment.gridviewBitmapCaches.put(this.thumbnailPath, bitmap);
            return bitmap;
        }
    }

    protected void onPostExecute(Bitmap resultBitmap) {
        if (isCancelled()) {
            resultBitmap = null;
        }
        if (resultBitmap != null) {
            if (this.imageViewReference != null) {
                ImageView imageview = (ImageView) this.imageViewReference.get();
                if (this == SportsAlbumAdapter.getAsyncLoadImageTask(imageview)) {
                    imageview.setImageBitmap(resultBitmap);
                    imageview.setScaleType(ScaleType.CENTER_INSIDE);
                }
            }
            super.onPostExecute(resultBitmap);
        }
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
