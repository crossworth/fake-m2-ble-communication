package com.baidu.mapapi.search.district;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.search.core.C0520i;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.platform.comapi.search.C0518b;
import com.baidu.platform.comapi.search.C0658d;

public class DistrictSearch extends C0520i {
    private C0658d f1570a;
    private boolean f1571b;
    private OnGetDistricSearchResultListener f1572c;

    private class C0538a implements C0518b {
        final /* synthetic */ DistrictSearch f1569a;

        private C0538a(DistrictSearch districtSearch) {
            this.f1569a = districtSearch;
        }

        public void mo1801a(int i) {
            if (!this.f1569a.f1571b && this.f1569a.f1572c != null) {
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
                    this.f1569a.f1572c.onGetDistrictResult(new DistrictResult(errorno));
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
        }

        public void mo1815n(String str) {
        }

        public void mo1816o(String str) {
            if (!this.f1569a.f1571b && str != null && str.length() > 0 && this.f1569a.f1572c != null) {
                this.f1569a.f1572c.onGetDistrictResult(C0540b.m1490a(str));
            }
        }

        public void mo1817p(String str) {
        }
    }

    DistrictSearch() {
        this.f1570a = null;
        this.f1571b = false;
        this.f1570a = new C0658d();
        this.f1570a.m2112a(new C0538a());
    }

    public static DistrictSearch newInstance() {
        BMapManager.init();
        return new DistrictSearch();
    }

    public void destroy() {
        if (!this.f1571b) {
            this.f1571b = true;
            this.f1572c = null;
            this.f1570a.m2110a();
            this.f1570a = null;
            BMapManager.destroy();
        }
    }

    public boolean searchDistrict(DistrictSearchOption districtSearchOption) {
        if (this.f1570a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (districtSearchOption != null && districtSearchOption.f1573a != null) {
            return this.f1570a.m2133c(districtSearchOption.f1573a, districtSearchOption.f1574b);
        } else {
            throw new IllegalArgumentException("option or city name can not be null");
        }
    }

    public void setOnDistrictSearchListener(OnGetDistricSearchResultListener onGetDistricSearchResultListener) {
        this.f1572c = onGetDistricSearchResultListener;
    }
}
