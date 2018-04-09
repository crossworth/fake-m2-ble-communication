package com.zhuoyou.plugin.album;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.running.Tools;
import java.lang.ref.WeakReference;
import java.util.List;

public class SportsAlbumAdapter extends BaseAdapter {
    private int itemLength = 0;
    private Context mContext = null;
    private LayoutInflater mLayoutInflater = null;
    private List<String> mLists = null;

    public static class LoadedDrawable extends ColorDrawable {
        private final WeakReference<AsyncLoadImageTask> loadImageTaskReference;

        public LoadedDrawable(AsyncLoadImageTask loadImageTask) {
            super(0);
            this.loadImageTaskReference = new WeakReference(loadImageTask);
        }

        public AsyncLoadImageTask getLoadImageTask() {
            return (AsyncLoadImageTask) this.loadImageTaskReference.get();
        }
    }

    public static class MyGridViewHolder {
        public ImageView imageview_thumbnail;
    }

    public SportsAlbumAdapter(Context c, List<String> path, int with) {
        this.mContext = c;
        this.mLists = path;
        this.itemLength = with;
        this.mLayoutInflater = LayoutInflater.from(c);
    }

    public int getCount() {
        return this.mLists.size();
    }

    public Object getItem(int position) {
        return this.mLists.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    @SuppressLint({"NewApi"})
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = this.mLayoutInflater.inflate(R.layout.layout_album_gridview_item, null);
            viewHolder.imageview_thumbnail = (ImageView) convertView.findViewById(R.id.imageview_thumbnail);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        ImageView img = viewHolder.imageview_thumbnail;
        String url = (String) this.mLists.get(position);
        if (!(url == null || url.equals(""))) {
            Bitmap bitmap = (Bitmap) SportsAlbum.gridviewBitmapCaches.get(url + position);
            if (bitmap != null) {
                img.setImageBitmap(bitmap);
            } else {
                bitmap = BitmapUtils.getScaleBitmap(Tools.getSDPath() + "/Running/.albumthumbnail/" + url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
                if (bitmap != null) {
                    img.setImageBitmap(bitmap);
                    SportsAlbum.gridviewBitmapCaches.put(url + position, bitmap);
                } else if (cancelPotentialLoad(url, img)) {
                    AsyncLoadImageTask task = new AsyncLoadImageTask(img, this.mLists, this.itemLength, url + position);
                    img.setImageDrawable(new LoadedDrawable(task));
                    task.execute(new Integer[]{Integer.valueOf(position)});
                }
            }
        }
        return convertView;
    }

    public static boolean cancelPotentialLoad(String url, ImageView imageview) {
        AsyncLoadImageTask loadImageTask = getAsyncLoadImageTask(imageview);
        if (loadImageTask == null) {
            return true;
        }
        String bitmapUrl = loadImageTask.getUrl();
        if (bitmapUrl != null && bitmapUrl.equals(url)) {
            return false;
        }
        loadImageTask.cancel(true);
        return true;
    }

    public static AsyncLoadImageTask getAsyncLoadImageTask(ImageView imageview) {
        if (imageview != null) {
            Drawable drawable = imageview.getDrawable();
            if (drawable instanceof LoadedDrawable) {
                return ((LoadedDrawable) drawable).getLoadImageTask();
            }
        }
        return null;
    }
}
