package com.baidu.mapapi.search.busline;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.core.C0520i;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.platform.comapi.search.C0518b;
import com.baidu.platform.comapi.search.C0658d;

public class BusLineSearch extends C0520i {
    private C0658d f1516a;
    private OnGetBusLineSearchResultListener f1517b;
    private boolean f1518c;

    private class C0519a implements C0518b {
        final /* synthetic */ BusLineSearch f1515a;

        private C0519a(BusLineSearch busLineSearch) {
            this.f1515a = busLineSearch;
        }

        public void mo1801a(int i) {
            if (!this.f1515a.f1518c && this.f1515a.f1517b != null) {
                ERRORNO errorno = null;
                switch (i) {
                    case 2:
                        errorno = ERRORNO.NETWORK_ERROR;
                        break;
                    case 8:
                        errorno = ERRORNO.NETWORK_TIME_OUT;
                        break;
                    case 11:
                        errorno = ERRORNO.RESULT_NOT_FOUND;
                        break;
                    case 107:
                        errorno = ERRORNO.PERMISSION_UNFINISHED;
                        break;
                    case 500:
                        errorno = ERRORNO.KEY_ERROR;
                        break;
                }
                if (errorno != null) {
                    this.f1515a.f1517b.onGetBusLineResult(new BusLineResult(errorno));
                }
            }
        }

        public void mo1802a(String str) {
        }

        public void mo1803b(String str) {
        }

        public void mo1804c(String str) {
        }

        public void mo1805d(String str) {
        }

        public void mo1806e(String str) {
        }

        public void mo1807f(String str) {
        }

        public void mo1808g(String str) {
        }

        public void mo1809h(String str) {
        }

        public void mo1810i(String str) {
        }

        public void mo1811j(String str) {
        }

        public void mo1812k(String str) {
        }

        public void mo1813l(String str) {
        }

        public void mo1814m(String str) {
            if (!this.f1515a.f1518c && str != null && str.length() > 0 && this.f1515a.f1517b != null) {
                this.f1515a.f1517b.onGetBusLineResult(C0522b.m1432a(str));
            }
        }

        public void mo1815n(String str) {
        }

        public void mo1816o(String str) {
        }

        public void mo1817p(String str) {
        }
    }

    BusLineSearch() {
        this.f1516a = null;
        this.f1517b = null;
        this.f1518c = false;
        this.f1516a = new C0658d();
        this.f1516a.m2112a(new C0519a());
    }

    public static BusLineSearch newInstance() {
        BMapManager.init();
        return new BusLineSearch();
    }

    public void destroy() {
        if (!this.f1518c) {
            this.f1518c = true;
            this.f1517b = null;
            this.f1516a.m2110a();
            this.f1516a = null;
            BMapManager.destroy();
        }
    }

    public boolean searchBusLine(BusLineSearchOption busLineSearchOption) {
        if (this.f1516a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (busLineSearchOption != null && busLineSearchOption.f1520b != null && busLineSearchOption.f1519a != null) {
            return this.f1516a.m2125a(busLineSearchOption.f1520b, busLineSearchOption.f1519a);
        } else {
            throw new IllegalArgumentException("option or city or uid can not be null");
        }
    }

    public void setOnGetBusLineSearchResultListener(OnGetBusLineSearchResultListener onGetBusLineSearchResultListener) {
        this.f1517b = onGetBusLineSearchResultListener;
    }
}
