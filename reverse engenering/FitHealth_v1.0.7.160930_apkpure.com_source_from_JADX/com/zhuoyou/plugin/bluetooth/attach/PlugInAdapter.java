package com.zhuoyou.plugin.bluetooth.attach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import java.util.ArrayList;
import java.util.List;

public class PlugInAdapter extends BaseAdapter {
    private static final int MAX_H_NUM = 3;
    private Context mCtx;
    private List<PlugBean> mPlugLists = new ArrayList();

    public PlugInAdapter(Context ctx, List<PlugBean> lists) {
        this.mCtx = ctx;
        this.mPlugLists = lists;
    }

    public int getCount() {
        return (this.mPlugLists.size() + 2) / 3;
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int arg0, View arg1, ViewGroup arg2) {
        View root = LayoutInflater.from(this.mCtx).inflate(R.layout.main_plug_gv_item, null);
        RelativeLayout[] roots = new RelativeLayout[3];
        ImageView[] icons = new ImageView[3];
        ImageView[] status = new ImageView[3];
        TextView[] names = new TextView[3];
        ImageView[] uninstalls = new ImageView[3];
        findViews(root, roots, icons, status, names, uninstalls);
        int max = 3;
        if (getCount() == arg0 + 1) {
            max = this.mPlugLists.size() % 3;
            if (max == 0) {
                max = 3;
            }
        }
        for (int i = 0; i < max; i++) {
            final int index = (arg0 * 3) + i;
            PlugBean item = (PlugBean) this.mPlugLists.get(index);
            if (!(!item.isPreInstall || item.isSystem || item.isInstalled)) {
                status[i].setVisibility(0);
            }
            if (item.isSystem) {
                item.setBitmapId(icons[i]);
            } else {
                item.setBitmap(icons[i]);
            }
            item.setTitle(names[i]);
            if (!BTPluginActivity.isEditMode) {
                uninstalls[i].setVisibility(8);
            } else if (item.isPreInstall && !item.isSystem && item.isInstalled) {
                uninstalls[i].setVisibility(0);
            }
            uninstalls[i].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    BTPluginActivity.onUninstallClick(index);
                }
            });
            roots[i].setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    BTPluginActivity.onClick(index);
                }
            });
        }
        return root;
    }

    private void findViews(View root, RelativeLayout[] roots, ImageView[] icons, ImageView[] status, TextView[] names, ImageView[] uninstalls) {
        roots[0] = (RelativeLayout) root.findViewById(R.id.gv_item_root1);
        icons[0] = (ImageView) root.findViewById(R.id.gv_item_icon1);
        status[0] = (ImageView) root.findViewById(R.id.gv_item_staus1);
        status[0].setVisibility(8);
        names[0] = (TextView) root.findViewById(R.id.gv_item_name1);
        int i = 0 + 1;
        uninstalls[0] = (ImageView) root.findViewById(R.id.gv_item_delete1);
        roots[i] = (RelativeLayout) root.findViewById(R.id.gv_item_root2);
        icons[i] = (ImageView) root.findViewById(R.id.gv_item_icon2);
        status[i] = (ImageView) root.findViewById(R.id.gv_item_staus2);
        status[i].setVisibility(8);
        names[i] = (TextView) root.findViewById(R.id.gv_item_name2);
        int i2 = i + 1;
        uninstalls[i] = (ImageView) root.findViewById(R.id.gv_item_delete2);
        roots[i2] = (RelativeLayout) root.findViewById(R.id.gv_item_root3);
        icons[i2] = (ImageView) root.findViewById(R.id.gv_item_icon3);
        status[i2] = (ImageView) root.findViewById(R.id.gv_item_staus3);
        status[i2].setVisibility(8);
        names[i2] = (TextView) root.findViewById(R.id.gv_item_name3);
        i = i2 + 1;
        uninstalls[i2] = (ImageView) root.findViewById(R.id.gv_item_delete3);
    }

    public void notify(List<PlugBean> lists) {
        this.mPlugLists = lists;
        notifyDataSetChanged();
    }
}
