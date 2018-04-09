package com.zhuoyi.system.network.util;

import android.content.Context;
import com.zhuoyi.system.network.callback.NetworkCallback;
import com.zhuoyi.system.network.connection.HTTPConnection;
import com.zhuoyi.system.network.object.GameServerBto;
import com.zhuoyi.system.network.object.NetworkAddr;
import com.zhuoyi.system.network.object.ZoneServerBto;
import com.zhuoyi.system.network.protocol.GetZoneServerReq;
import com.zhuoyi.system.network.protocol.GetZoneServerResp;
import com.zhuoyi.system.network.serializer.AttributeUitl;
import com.zhuoyi.system.network.serializer.ZyCom_Message;
import com.zhuoyi.system.util.EncryptUtils;
import com.zhuoyi.system.util.Logger;
import com.zhuoyi.system.util.TerminalInfoUtil;
import com.zhuoyi.system.util.constant.Session;
import java.util.ArrayList;
import java.util.Iterator;

public class NetworkAddressUtil {
    private static NetworkAddressUtil mInstance = null;
    public static final Session session = Session.getInstance();
    private Context mContext;

    public interface InitServerAddrResponse {
        void onServerAddrResponseError();

        void onServerAddrResponseSuccess(Session session);
    }

    public static NetworkAddressUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NetworkAddressUtil(context);
        }
        return mInstance;
    }

    private NetworkAddressUtil(Context context) {
        this.mContext = context;
    }

    public synchronized void initNetworkAddress(final InitServerAddrResponse initServerAddrResponse) {
        if (session.isEmpty()) {
            Logger.debug(NetworkConstants.TAG, "session is null and initNetworkAddress");
            GetZoneServerReq req = new GetZoneServerReq();
            req.setTerminalInfo(TerminalInfoUtil.getTerminalInfoForZone(this.mContext));
            Logger.debug(NetworkConstants.TAG, req.toString());
            HTTPConnection.getInstance().sendRequest(getServerAddr(), req, new NetworkCallback() {
                public void onResponse(Boolean result, ZyCom_Message response) {
                    if (!result.booleanValue() || response == null) {
                        initServerAddrResponse.onServerAddrResponseError();
                        Logger.error(NetworkConstants.TAG, "Get ZoneServerResp  Error ");
                    } else if (response.head.code == AttributeUitl.getMessageCode(GetZoneServerResp.class)) {
                        GetZoneServerResp resp = response.message;
                        if (resp != null) {
                            Logger.debug(NetworkConstants.TAG, resp.toString());
                            if (resp.getErrorCode() == 0) {
                                initServerAddrResponse.onServerAddrResponseSuccess(NetworkAddressUtil.this.saveNetwrokAdd(((ZoneServerBto) resp.getZoneServerList().get(0)).getGameServerList()));
                                return;
                            }
                            initServerAddrResponse.onServerAddrResponseError();
                            Logger.error(NetworkConstants.TAG, "Get ZoneServerResp  Error Message =" + resp.getErrorMessage());
                            return;
                        }
                        Logger.error(NetworkConstants.TAG, "Get ZoneServerResp  Error ");
                        initServerAddrResponse.onServerAddrResponseError();
                    }
                }
            });
        } else {
            Logger.debug(NetworkConstants.TAG, "session is not null and session uuid=" + session.getUUId());
            initServerAddrResponse.onServerAddrResponseSuccess(session);
        }
    }

    private NetworkAddr getServerAddr() {
        return new NetworkAddr(EncryptUtils.getNetworkAddr());
    }

    private Session saveNetwrokAdd(ArrayList<GameServerBto> arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            GameServerBto bto = (GameServerBto) it.next();
            Logger.debug(NetworkConstants.TAG, bto.getHost() + ":" + bto.getPort() + "--" + bto.getModuleId());
            if (bto.getModuleId() == (short) 1) {
                session.setPromNetworkAddr(new NetworkAddr(bto.getHost() + ":" + bto.getPort()));
            } else if (bto.getModuleId() == (short) 2) {
                session.setStatisNetworkAddr(new NetworkAddr(bto.getHost() + ":" + bto.getPort()));
            } else if (bto.getModuleId() == (short) 3) {
                session.setUpdateNetworkAddr(new NetworkAddr(bto.getHost() + ":" + bto.getPort()));
            }
        }
        return session;
    }
}
