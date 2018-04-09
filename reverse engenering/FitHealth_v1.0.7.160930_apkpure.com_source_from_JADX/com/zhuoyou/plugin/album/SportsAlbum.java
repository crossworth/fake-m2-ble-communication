package com.zhuoyou.plugin.album;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class SportsAlbum extends Activity implements OnScrollListener {
    public static Map<String, Bitmap> gridviewBitmapCaches = new HashMap();
    private AlbumPopupWindow albumPW;
    private List<String> mList = null;
    private HashSet<String> set;
    private LinearLayout sportsAlbum;
    private TextView text;

    class C11391 implements OnClickListener {
        C11391() {
        }

        public void onClick(View v) {
            SportsAlbum.this.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sports_album);
        ((TextView) findViewById(R.id.title)).setText(R.string.sports_photo);
        ((RelativeLayout) findViewById(R.id.back)).setOnClickListener(new C11391());
        initDate();
        try {
            initView();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initDate() {
        this.mList = new ArrayList();
        this.set = imagePath();
        Iterator<String> iterator = this.set.iterator();
        while (iterator.hasNext()) {
            this.mList.add(iterator.next());
        }
    }

    private void initView() throws FileNotFoundException {
        final LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1.0f;
        this.sportsAlbum = (LinearLayout) findViewById(R.id.sports_album);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new SportsAlbumAdapter(this, this.mList, getWindowManager().getDefaultDisplay().getWidth() / 3));
        gridview.setSelector(new ColorDrawable(0));
        gridview.setOnItemClickListener(new OnItemClickListener() {

            class C11401 implements OnDismissListener {
                C11401() {
                }

                public void onDismiss() {
                    lp.alpha = 1.0f;
                    SportsAlbum.this.getWindow().setAttributes(lp);
                    SportsAlbum.this.albumPW = null;
                }
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (SportsAlbum.this.albumPW == null || !SportsAlbum.this.albumPW.isShowing()) {
                    SportsAlbum.this.albumPW = new AlbumPopupWindow(SportsAlbum.this, (String) SportsAlbum.this.mList.get(position));
                    SportsAlbum.this.albumPW.showAtLocation(SportsAlbum.this.sportsAlbum, 17, 0, 0);
                    SportsAlbum.this.getWindow().setAttributes(lp);
                    SportsAlbum.this.albumPW.setOnDismissListener(new C11401());
                }
            }
        });
        gridview.setOnScrollListener(this);
        this.text = (TextView) findViewById(R.id.text);
        if (this.mList == null || this.mList.isEmpty()) {
            gridview.setVisibility(8);
            this.text.setVisibility(0);
            return;
        }
        gridview.setVisibility(0);
        this.text.setVisibility(8);
    }

    private HashSet<String> imagePath() {
        Cursor c = getContentResolver().query(DataBaseContants.CONTENT_URI, new String[]{DataBaseContants.IMG_URI}, "type = ? AND statistics = ? ", new String[]{"3", "0"}, null);
        HashSet<String> hashSet = new LinkedHashSet();
        if (c.getCount() <= 0 || !c.moveToFirst()) {
            c.close();
            return hashSet;
        }
        do {
            String img_uri = c.getString(c.getColumnIndex(DataBaseContants.IMG_URI));
            if (!(img_uri == null || img_uri.equals(""))) {
                hashSet.add(img_uri);
            }
        } while (c.moveToNext());
        c.close();
        return hashSet;
    }

    private void recycleBitmapCaches(int fromPosition, int toPosition) {
        for (int del = fromPosition; del < toPosition; del++) {
            Bitmap delBitmap = (Bitmap) gridviewBitmapCaches.get(((String) this.mList.get(del)) + del);
            if (delBitmap != null) {
                Log.d("txhlog", "release position:" + del);
                gridviewBitmapCaches.remove(((String) this.mList.get(del)) + del);
                delBitmap.recycle();
                System.gc();
            }
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        recycleBitmapCaches(0, firstVisibleItem);
        recycleBitmapCaches(firstVisibleItem + visibleItemCount, totalItemCount);
    }
}
