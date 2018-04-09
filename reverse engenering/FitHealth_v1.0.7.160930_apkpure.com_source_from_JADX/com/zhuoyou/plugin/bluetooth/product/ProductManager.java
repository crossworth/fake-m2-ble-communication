package com.zhuoyou.plugin.bluetooth.product;

import android.content.Context;
import android.util.Log;
import com.zhuoyou.plugin.running.RunningApp;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.SAXException;

public class ProductManager {
    public static ProductManager mProductManager = null;
    private Context mCtx = RunningApp.getInstance().getApplicationContext();
    private Grandpa mLocalGrandPa = null;

    public static ProductManager getInstance() {
        if (mProductManager == null) {
            mProductManager = new ProductManager();
        }
        return mProductManager;
    }

    public ProductManager() {
        init();
    }

    private void init() {
        LoadingLocalProduct();
    }

    private void LoadingLocalProduct() {
        InputStream is = null;
        try {
            is = this.mCtx.getAssets().open("product.xml");
            Log.i("gchk", "open local product file successed ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (is == null) {
            Log.e("gchk", "open local product file failed , can not happen!");
            return;
        }
        this.mLocalGrandPa = parserXml(is);
        if (this.mLocalGrandPa == null) {
            Log.e("gchk", "parser local xml failed , can not happen!");
        }
    }

    private Grandpa parserXml(InputStream is) {
        ProductHandler handler = new ProductHandler();
        Grandpa pa = null;
        try {
            SAXParserFactory.newInstance().newSAXParser().parse(is, handler);
            is.close();
            pa = handler.getRoot();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return pa;
    }

    public Grandpa getCurrRoot() {
        return this.mLocalGrandPa;
    }

    public boolean isSupportPlugin(String product_name, String package_name) {
        Grandpa root = getCurrRoot();
        if (root == null) {
            return false;
        }
        for (int i = 0; i < root.getFather().size(); i++) {
            Father father = (Father) root.getFather().get(i);
            if (product_name.equals(father.getName())) {
                for (int j = 0; j < father.getSons().size(); j++) {
                    if (((Son) father.getSons().get(j)).getPackageName().equals(package_name)) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }

    public String getProductCategory(String product_name) {
        Grandpa root = getCurrRoot();
        if (root == null) {
            return null;
        }
        for (int i = 0; i < root.getFather().size(); i++) {
            Father father = (Father) root.getFather().get(i);
            if (product_name.equals(father.getName())) {
                return father.getCategory();
            }
        }
        return null;
    }
}
