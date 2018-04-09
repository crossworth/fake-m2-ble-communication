package com.baidu.platform.comjni.map.basemap;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.VersionInfo;
import com.baidu.mapapi.common.BaiduMapSDKException;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.platform.comapi.AssetsLoadUtil;
import com.baidu.platform.comapi.NativeLoader;
import com.baidu.platform.comapi.map.C0626M;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class C0673a {
    private static final String f2229a = C0673a.class.getSimpleName();
    private static List<JNIBaseMap> f2230d = new ArrayList();
    private long f2231b;
    private JNIBaseMap f2232c;

    static {
        if (VersionInfo.getApiVersion().equals(C0626M.m1925a())) {
            C0673a.m2192a(BMapManager.getContext());
            NativeLoader.getInstance().loadLibrary(C0626M.m1926b());
            return;
        }
        throw new BaiduMapSDKException("the version of map is not match with base");
    }

    public C0673a() {
        this.f2231b = 0;
        this.f2232c = null;
        this.f2232c = new JNIBaseMap();
    }

    public static int m2191a(long j, int i, int i2, int i3) {
        return JNIBaseMap.MapProc(j, i, i2, i3);
    }

    private static void m2192a(Context context) {
        int i = 0;
        if (context != null) {
            try {
                File file = new File(SysOSUtil.getModuleFileName());
                if (!file.exists()) {
                    file.mkdirs();
                }
                context.getAssets();
                String[] strArr = new String[]{"cfg/a/ResPack.cfg", "cfg/idrres/baseindoormap.sty", "cfg/idrres/DVIndoor.cfg", "cfg/idrres/ResPack.cfg", "cfg/h/DVHotcity.cfg", "cfg/l/DVHotcity.cfg", "cfg/h/DVHotMap.cfg", "cfg/l/DVHotMap.cfg", "cfg/l/DVDirectory.cfg", "cfg/l/DVVersion.cfg", "cfg/h/DVDirectory.cfg", "cfg/h/DVVersion.cfg", "cfg/a/mapstyle.sty", "cfg/a/satellitestyle.sty", "cfg/a/trafficstyle.sty", "cfg/a/CustomIndex"};
                String[] strArr2 = new String[]{"cfg/a/CustomIndex"};
                String[] strArr3 = new String[]{"cfg/a/ResPack.rs", "cfg/idrres/baseindoormap.sty", "cfg/idrres/DVIndoor.cfg", "cfg/idrres/ResPackIndoorMap.rs", "cfg/h/DVHotcity.cfg", "cfg/l/DVHotcity.cfg", "cfg/h/DVHotMap.cfg", "cfg/l/DVHotMap.cfg", "cfg/l/DVDirectory.cfg", "cfg/l/DVVersion.cfg", "cfg/h/DVDirectory.cfg", "cfg/h/DVVersion.cfg", "cfg/a/mapstyle.sty", "cfg/a/satellitestyle.sty", "cfg/a/trafficstyle.sty", "cfg/a/CustomIndex"};
                String[] strArr4 = new String[]{"cfg/a/CustomIndex"};
                try {
                    int i2;
                    FileOutputStream fileOutputStream;
                    File file2;
                    int i3;
                    File file3 = new File(SysOSUtil.getModuleFileName() + "/ver.dat");
                    byte[] bArr = new byte[]{(byte) 4, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
                    if (file3.exists()) {
                        FileInputStream fileInputStream = new FileInputStream(file3);
                        byte[] bArr2 = new byte[fileInputStream.available()];
                        fileInputStream.read(bArr2);
                        fileInputStream.close();
                        if (Arrays.equals(bArr2, bArr)) {
                            file = new File(SysOSUtil.getModuleFileName() + "/cfg/a/mapstyle.sty");
                            if (file.exists() && file.length() > 0) {
                                i2 = 0;
                                if (i2 != 0) {
                                    if (file3.exists()) {
                                        file3.delete();
                                    }
                                    file3.createNewFile();
                                    fileOutputStream = new FileOutputStream(file3);
                                    fileOutputStream.write(bArr);
                                    fileOutputStream.close();
                                    file2 = new File(SysOSUtil.getModuleFileName() + "/cfg/a");
                                    if (!file2.exists()) {
                                        file2.mkdirs();
                                    }
                                    file2 = new File(SysOSUtil.getModuleFileName() + "/cfg/h");
                                    if (!file2.exists()) {
                                        file2.mkdirs();
                                    }
                                    file2 = new File(SysOSUtil.getModuleFileName() + "/cfg/l");
                                    if (!file2.exists()) {
                                        file2.mkdirs();
                                    }
                                    file2 = new File(SysOSUtil.getModuleFileName() + "/cfg/idrres");
                                    if (!file2.exists()) {
                                        file2.mkdirs();
                                    }
                                }
                                for (i3 = 0; i3 < strArr4.length; i3++) {
                                    AssetsLoadUtil.copyFileFromAsset(strArr2[i3], strArr4[i3], context);
                                }
                                if (i2 != 0) {
                                    while (i < strArr3.length) {
                                        AssetsLoadUtil.copyFileFromAsset(strArr[i], strArr3[i], context);
                                        i++;
                                    }
                                }
                            }
                        }
                    }
                    i2 = 1;
                    if (i2 != 0) {
                        if (file3.exists()) {
                            file3.delete();
                        }
                        file3.createNewFile();
                        fileOutputStream = new FileOutputStream(file3);
                        fileOutputStream.write(bArr);
                        fileOutputStream.close();
                        file2 = new File(SysOSUtil.getModuleFileName() + "/cfg/a");
                        if (file2.exists()) {
                            file2.mkdirs();
                        }
                        file2 = new File(SysOSUtil.getModuleFileName() + "/cfg/h");
                        if (file2.exists()) {
                            file2.mkdirs();
                        }
                        file2 = new File(SysOSUtil.getModuleFileName() + "/cfg/l");
                        if (file2.exists()) {
                            file2.mkdirs();
                        }
                        file2 = new File(SysOSUtil.getModuleFileName() + "/cfg/idrres");
                        if (file2.exists()) {
                            file2.mkdirs();
                        }
                    }
                    for (i3 = 0; i3 < strArr4.length; i3++) {
                        AssetsLoadUtil.copyFileFromAsset(strArr2[i3], strArr4[i3], context);
                    }
                    if (i2 != 0) {
                        while (i < strArr3.length) {
                            AssetsLoadUtil.copyFileFromAsset(strArr[i], strArr3[i], context);
                            i++;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void m2193b(long j, boolean z) {
        JNIBaseMap.SetMapCustomEnable(j, z);
    }

    public static List<JNIBaseMap> m2194d() {
        return f2230d;
    }

    public int m2195a(int i) {
        return this.f2232c.SetMapControlMode(this.f2231b, i);
    }

    public long m2196a(int i, int i2, String str) {
        return this.f2232c.AddLayer(this.f2231b, i, i2, str);
    }

    public String m2197a(int i, int i2) {
        return this.f2232c.ScrPtToGeoPoint(this.f2231b, i, i2);
    }

    public String m2198a(int i, int i2, int i3, int i4) {
        return this.f2232c.GetNearlyObjID(this.f2231b, (long) i, i2, i3, i4);
    }

    public String m2199a(String str) {
        return this.f2232c.OnSchcityGet(this.f2231b, str);
    }

    public void m2200a(long j, boolean z) {
        this.f2232c.ShowLayers(this.f2231b, j, z);
    }

    public void m2201a(Bundle bundle) {
        this.f2232c.SetMapStatus(this.f2231b, bundle);
    }

    public void m2202a(String str, Bundle bundle) {
        this.f2232c.SaveScreenToLocal(this.f2231b, str, bundle);
    }

    public void m2203a(boolean z) {
        this.f2232c.ShowSatelliteMap(this.f2231b, z);
    }

    public void m2204a(Bundle[] bundleArr) {
        this.f2232c.addOverlayItems(this.f2231b, bundleArr, bundleArr.length);
    }

    public boolean m2205a() {
        if (f2230d.size() == 0) {
            this.f2231b = this.f2232c.Create();
        } else {
            this.f2231b = this.f2232c.CreateDuplicate(((JNIBaseMap) f2230d.get(0)).f2228a);
        }
        this.f2232c.f2228a = this.f2231b;
        f2230d.add(this.f2232c);
        this.f2232c.SetCallback(this.f2231b, null);
        return true;
    }

    public boolean m2206a(int i, boolean z) {
        return this.f2232c.OnRecordReload(this.f2231b, i, z);
    }

    public boolean m2207a(int i, boolean z, int i2) {
        return this.f2232c.OnRecordStart(this.f2231b, i, z, i2);
    }

    public boolean m2208a(long j) {
        return this.f2232c.LayersIsShow(this.f2231b, j);
    }

    public boolean m2209a(String str, String str2) {
        return this.f2232c.SwitchBaseIndoorMapFloor(this.f2231b, str, str2);
    }

    public boolean m2210a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, int i, int i2, int i3, int i4, int i5, int i6, int i7) {
        return this.f2232c.Init(this.f2231b, str, str2, str3, str4, str5, str6, str7, str8, i, i2, i3, i4, i5, i6, i7);
    }

    public boolean m2211a(boolean z, boolean z2) {
        return this.f2232c.OnRecordImport(this.f2231b, z, z2);
    }

    public int[] m2212a(int[] iArr, int i, int i2) {
        return this.f2232c.GetScreenBuf(this.f2231b, iArr, i, i2);
    }

    public String m2213b(int i, int i2) {
        return this.f2232c.GeoPtToScrPoint(this.f2231b, i, i2);
    }

    public void m2214b(long j) {
        this.f2232c.UpdateLayers(this.f2231b, j);
    }

    public void m2215b(Bundle bundle) {
        this.f2232c.setMapStatusLimits(this.f2231b, bundle);
    }

    public void m2216b(boolean z) {
        this.f2232c.ShowHotMap(this.f2231b, z);
    }

    public boolean m2217b() {
        this.f2232c.Release(this.f2231b);
        f2230d.remove(this.f2232c);
        return true;
    }

    public boolean m2218b(int i) {
        return this.f2232c.OnRecordAdd(this.f2231b, i);
    }

    public boolean m2219b(int i, boolean z) {
        return this.f2232c.OnRecordRemove(this.f2231b, i, z);
    }

    public boolean m2220b(int i, boolean z, int i2) {
        return this.f2232c.OnRecordSuspend(this.f2231b, i, z, i2);
    }

    public float m2221c(Bundle bundle) {
        return this.f2232c.GetZoomToBound(this.f2231b, bundle);
    }

    public long m2222c() {
        return this.f2231b;
    }

    public String m2223c(int i) {
        return this.f2232c.OnRecordGetAt(this.f2231b, i);
    }

    public void m2224c(boolean z) {
        this.f2232c.ShowTrafficMap(this.f2231b, z);
    }

    public boolean m2225c(long j) {
        return this.f2232c.cleanSDKTileDataCache(this.f2231b, j);
    }

    public void m2226d(long j) {
        this.f2232c.ClearLayer(this.f2231b, j);
    }

    public void m2227d(boolean z) {
        this.f2232c.enableDrawHouseHeight(this.f2231b, z);
    }

    public boolean m2228d(Bundle bundle) {
        return this.f2232c.updateSDKTile(this.f2231b, bundle);
    }

    public String m2229e(long j) {
        return this.f2232c.getCompassPosition(this.f2231b, j);
    }

    public void m2230e() {
        this.f2232c.OnPause(this.f2231b);
    }

    public void m2231e(boolean z) {
        this.f2232c.ShowBaseIndoorMap(this.f2231b, z);
    }

    public boolean m2232e(Bundle bundle) {
        return this.f2232c.addtileOverlay(this.f2231b, bundle);
    }

    public void m2233f() {
        this.f2232c.OnResume(this.f2231b);
    }

    public void m2234f(Bundle bundle) {
        this.f2232c.addOneOverlayItem(this.f2231b, bundle);
    }

    public void m2235g() {
        this.f2232c.OnBackground(this.f2231b);
    }

    public void m2236g(Bundle bundle) {
        this.f2232c.updateOneOverlayItem(this.f2231b, bundle);
    }

    public void m2237h() {
        this.f2232c.OnForeground(this.f2231b);
    }

    public void m2238h(Bundle bundle) {
        this.f2232c.removeOneOverlayItem(this.f2231b, bundle);
    }

    public void m2239i() {
        this.f2232c.ResetImageRes(this.f2231b);
    }

    public Bundle m2240j() {
        return this.f2232c.GetMapStatus(this.f2231b);
    }

    public Bundle m2241k() {
        Bundle mapStatusLimits = this.f2232c.getMapStatusLimits(this.f2231b);
        Log.d("test", "GetMapStatusLimits, maddr: " + this.f2231b + "bundle: " + mapStatusLimits);
        return mapStatusLimits;
    }

    public Bundle m2242l() {
        return this.f2232c.getDrawingMapStatus(this.f2231b);
    }

    public boolean m2243m() {
        return this.f2232c.GetBaiduHotMapCityInfo(this.f2231b);
    }

    public String m2244n() {
        return this.f2232c.OnRecordGetAll(this.f2231b);
    }

    public String m2245o() {
        return this.f2232c.OnHotcityGet(this.f2231b);
    }

    public void m2246p() {
        this.f2232c.PostStatInfo(this.f2231b);
    }

    public boolean m2247q() {
        return this.f2232c.isDrawHouseHeightEnable(this.f2231b);
    }

    public void m2248r() {
        this.f2232c.clearHeatMapLayerCache(this.f2231b);
    }

    public MapBaseIndoorMapInfo m2249s() {
        JSONException e;
        String str = this.f2232c.getfocusedBaseIndoorMapInfo(this.f2231b);
        if (str == null) {
            return null;
        }
        String str2 = "";
        String str3 = new String();
        ArrayList arrayList = new ArrayList(1);
        try {
            JSONObject jSONObject = new JSONObject(str);
            str2 = jSONObject.optString("focusindoorid");
            str = jSONObject.optString("curfloor");
            try {
                JSONArray optJSONArray = jSONObject.optJSONArray("floorlist");
                if (optJSONArray == null) {
                    return null;
                }
                for (int i = 0; i < optJSONArray.length(); i++) {
                    arrayList.add(optJSONArray.get(i).toString());
                }
                return new MapBaseIndoorMapInfo(str2, str, arrayList);
            } catch (JSONException e2) {
                e = e2;
                e.printStackTrace();
                return new MapBaseIndoorMapInfo(str2, str, arrayList);
            }
        } catch (JSONException e3) {
            e = e3;
            str = str3;
            e.printStackTrace();
            return new MapBaseIndoorMapInfo(str2, str, arrayList);
        }
    }

    public boolean m2250t() {
        return this.f2232c.IsBaseIndoorMapMode(this.f2231b);
    }
}
