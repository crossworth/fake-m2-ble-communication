package com.autonavi.amap.mapcore;

import com.umeng.socialize.common.SocializeConstants;
import java.util.ArrayList;
import javax.microedition.khronos.opengles.GL10;

public abstract class BaseMapCallImplement implements IBaseMapCallback, IMapCallback {
    private ArrayList<MapSourceGridData> bldReqMapGrids = new ArrayList();
    ConnectionManager connectionManager = null;
    private ArrayList<MapSourceGridData> curBldMapGrids = new ArrayList();
    private ArrayList<MapSourceGridData> curIndoorMapGirds = new ArrayList();
    private ArrayList<MapSourceGridData> curPoiMapGrids = new ArrayList();
    private ArrayList<MapSourceGridData> curRegionMapGrids = new ArrayList();
    private ArrayList<MapSourceGridData> curRoadMapGrids = new ArrayList();
    private ArrayList<MapSourceGridData> curScreenGirds = new ArrayList();
    private ArrayList<MapSourceGridData> curStiMapGirds = new ArrayList();
    private ArrayList<MapSourceGridData> curVectmcMapGirds = new ArrayList();
    private ArrayList<MapSourceGridData> indoorMapGrids = new ArrayList();
    Object mapGridFillLock = new Object();
    private ArrayList<MapSourceGridData> poiReqMapGrids = new ArrayList();
    private ArrayList<MapSourceGridData> regionReqMapGrids = new ArrayList();
    private ArrayList<MapSourceGridData> roadReqMapGrids = new ArrayList();
    private ArrayList<MapSourceGridData> stiReqMapGirds = new ArrayList();
    TextTextureGenerator textTextureGenerator = null;
    C0480d tileProcessCtrl = null;
    private ArrayList<MapSourceGridData> vectmcReqMapGirds = new ArrayList();
    private ArrayList<MapSourceGridData> versionMapGrids = new ArrayList();

    public void OnMapDataRequired(MapCore mapCore, int i, String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            ArrayList reqGridList = getReqGridList(i);
            if (reqGridList != null) {
                reqGridList.clear();
                for (String mapSourceGridData : strArr) {
                    reqGridList.add(new MapSourceGridData(mapSourceGridData, i));
                }
                if (i != 5) {
                    proccessRequiredData(mapCore, reqGridList, i);
                }
            }
        }
    }

    public void OnMapProcessEvent(MapCore mapCore) {
    }

    public ArrayList<MapSourceGridData> getReqGridList(int i) {
        if (i == 0) {
            return this.roadReqMapGrids;
        }
        if (i == 1) {
            return this.bldReqMapGrids;
        }
        if (i == 7) {
            return this.regionReqMapGrids;
        }
        if (i == 8) {
            return this.poiReqMapGrids;
        }
        if (i == 4) {
            return this.vectmcReqMapGirds;
        }
        if (i == 5) {
            return this.curScreenGirds;
        }
        if (i == 3) {
            return this.stiReqMapGirds;
        }
        if (i == 9) {
            return this.versionMapGrids;
        }
        if (i == 10) {
            return this.indoorMapGrids;
        }
        return null;
    }

    public void OnMapSurfaceRenderer(GL10 gl10, MapCore mapCore, int i) {
        if (i == 11) {
            synchronized (this.mapGridFillLock) {
                mapCore.fillCurGridListWithDataType(this.curPoiMapGrids, 8);
                mapCore.fillCurGridListWithDataType(this.curRoadMapGrids, 0);
                mapCore.fillCurGridListWithDataType(this.curRegionMapGrids, 7);
                mapCore.fillCurGridListWithDataType(this.curBldMapGrids, 1);
                mapCore.fillCurGridListWithDataType(this.curVectmcMapGirds, 4);
                mapCore.fillCurGridListWithDataType(this.curStiMapGirds, 3);
                mapCore.fillCurGridListWithDataType(this.curIndoorMapGirds, 10);
            }
        }
    }

    public ArrayList<MapSourceGridData> getCurGridList(int i) {
        if (i == 0) {
            return this.curRoadMapGrids;
        }
        if (i == 1) {
            return this.curBldMapGrids;
        }
        if (i == 7) {
            return this.curRegionMapGrids;
        }
        if (i == 8) {
            return this.curPoiMapGrids;
        }
        if (i == 4) {
            return this.curVectmcMapGirds;
        }
        if (i == 5) {
            return this.curScreenGirds;
        }
        if (i == 10) {
            return this.curIndoorMapGirds;
        }
        if (i == 3) {
            return this.curStiMapGirds;
        }
        return null;
    }

    public boolean isGridsInScreen(ArrayList<MapSourceGridData> arrayList, int i) {
        try {
            if (arrayList.size() == 0) {
                return false;
            }
            if (!isMapEngineValid()) {
                return false;
            }
            synchronized (this.mapGridFillLock) {
                ArrayList curGridList = getCurGridList(i);
                if (curGridList == null) {
                    return true;
                }
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (isGridInList(((MapSourceGridData) arrayList.get(i2)).getGridName(), curGridList)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean isGridInScreen(int i, String str) {
        try {
            if (!isMapEngineValid()) {
                return false;
            }
            synchronized (this.mapGridFillLock) {
                if (isGridInList(str, getCurGridList(i))) {
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    private boolean isGridInList(String str, ArrayList<MapSourceGridData> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (((MapSourceGridData) arrayList.get(i)).getGridName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIndoorGridsInScreen(ArrayList<MapSourceGridData> arrayList, int i) {
        try {
            if (arrayList.size() == 0) {
                return false;
            }
            if (!isMapEngineValid()) {
                return false;
            }
            synchronized (this.mapGridFillLock) {
                ArrayList curGridList = getCurGridList(i);
                if (curGridList == null) {
                    return true;
                }
                for (int i2 = 0; i2 < arrayList.size(); i2++) {
                    if (isIndoorGridInList(((MapSourceGridData) arrayList.get(i2)).getKeyGridName(), curGridList)) {
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean isIndoorGridInScreen(int i, String str, short s) {
        try {
            if (!isMapEngineValid()) {
                return false;
            }
            synchronized (this.mapGridFillLock) {
                if (isIndoorGridInList(i + SocializeConstants.OP_DIVIDER_MINUS + str + SocializeConstants.OP_DIVIDER_MINUS + s, getCurGridList(i))) {
                    return true;
                }
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    private boolean isIndoorGridInList(String str, ArrayList<MapSourceGridData> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (((MapSourceGridData) arrayList.get(i)).getKeyGridName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    protected void proccessRequiredData(MapCore mapCore, ArrayList<MapSourceGridData> arrayList, int i) {
        int i2 = 0;
        ArrayList arrayList2 = new ArrayList();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            MapSourceGridData mapSourceGridData = (MapSourceGridData) arrayList.get(i3);
            if (this.tileProcessCtrl == null || !this.tileProcessCtrl.m2081b(mapSourceGridData.getKeyGridName())) {
                if (i == 4) {
                    VTMCDataCache instance = VTMCDataCache.getInstance();
                    C0482f data = instance.getData(mapSourceGridData.getGridName(), true);
                    C0482f data2 = instance.getData(mapSourceGridData.getGridName(), false);
                    if (data != null) {
                        mapSourceGridData.obj = data.f2048d;
                    }
                    if (data2 == null || data2.f2045a == null || data2.f2045a.length <= 0) {
                        arrayList2.add(mapSourceGridData);
                    } else {
                        mapCore.putMapData(data2.f2045a, 0, data2.f2045a.length, i, data2.f2047c);
                    }
                } else {
                    try {
                        String str = mapSourceGridData.gridName;
                        if (i == 10) {
                            str = mapSourceGridData.gridName + SocializeConstants.OP_DIVIDER_MINUS + mapSourceGridData.mIndoorIndex;
                        }
                        C0481e recoder = VMapDataCache.getInstance().getRecoder(str, i);
                        if (recoder == null || recoder.f2041a == null || recoder.f2041a.length() > 0) {
                            arrayList2.add(mapSourceGridData);
                        } else {
                            arrayList2.add(mapSourceGridData);
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        if (arrayList2.size() > 0) {
            BaseMapLoader indoorMapLoader;
            if (i == 10) {
                indoorMapLoader = new IndoorMapLoader(mapCore, this, i);
            } else if (i == 11) {
                indoorMapLoader = null;
            } else {
                indoorMapLoader = new NormalMapLoader(mapCore, this, i);
            }
            while (i2 < arrayList2.size()) {
                mapSourceGridData = (MapSourceGridData) arrayList2.get(i2);
                this.tileProcessCtrl.m2082c(mapSourceGridData.getKeyGridName());
                indoorMapLoader.addReuqestTiles(mapSourceGridData);
                i2++;
            }
            if (this.connectionManager != null) {
                this.connectionManager.insertConntionTask(indoorMapLoader);
            }
        }
    }

    public void OnMapSurfaceCreate(MapCore mapCore) {
    }

    public synchronized void onPause() {
        try {
            if (this.connectionManager != null) {
                this.connectionManager.threadFlag = false;
                if (this.connectionManager.isAlive()) {
                    this.connectionManager.interrupt();
                    this.connectionManager.shutDown();
                    this.connectionManager = null;
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public synchronized void onResume(MapCore mapCore) {
        try {
            this.connectionManager = new ConnectionManager(mapCore);
            this.tileProcessCtrl = new C0480d();
            this.connectionManager.start();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public void OnMapDestory(MapCore mapCore) {
        try {
            destoryMap(mapCore);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public byte[] OnMapCharsWidthsRequired(MapCore mapCore, int[] iArr, int i, int i2) {
        if (this.textTextureGenerator == null) {
            this.textTextureGenerator = new TextTextureGenerator();
        }
        return this.textTextureGenerator.getCharsWidths(iArr);
    }

    public void OnMapLabelsRequired(MapCore mapCore, int[] iArr, int i) {
        if (iArr != null && i > 0) {
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = iArr[i2];
                this.textTextureGenerator = new TextTextureGenerator();
                byte[] textPixelBuffer = this.textTextureGenerator.getTextPixelBuffer(i3);
                if (textPixelBuffer != null) {
                    mapCore.putCharbitmap(i3, textPixelBuffer);
                }
            }
        }
    }

    public synchronized void newMap(MapCore mapCore) {
        this.connectionManager = new ConnectionManager(mapCore);
        this.tileProcessCtrl = new C0480d();
        this.connectionManager.start();
    }

    public synchronized void destoryMap(MapCore mapCore) {
        if (this.connectionManager != null) {
            this.connectionManager.threadFlag = false;
            if (this.connectionManager.isAlive()) {
                try {
                    this.connectionManager.interrupt();
                    this.connectionManager.shutDown();
                    this.connectionManager = null;
                } catch (Throwable th) {
                    this.connectionManager.shutDown();
                    this.connectionManager = null;
                }
            }
        }
        if (this.tileProcessCtrl != null) {
            this.tileProcessCtrl.m2078a();
        }
    }
}
