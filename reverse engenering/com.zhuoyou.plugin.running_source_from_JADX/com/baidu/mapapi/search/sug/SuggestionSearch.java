package com.baidu.mapapi.search.sug;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.search.core.C0520i;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.baidu.platform.comapi.search.C0518b;
import com.baidu.platform.comapi.search.C0658d;

public class SuggestionSearch extends C0520i {
    private C0658d f1817a;
    private OnGetSuggestionResultListener f1818b;
    private boolean f1819c;

    private class C0581a implements C0518b {
        final /* synthetic */ SuggestionSearch f1816a;

        private C0581a(SuggestionSearch suggestionSearch) {
            this.f1816a = suggestionSearch;
        }

        public void mo1801a(int i) {
            if (!this.f1816a.f1819c && this.f1816a.f1818b != null) {
                ERRORNO errorno = null;
                switch (i) {
                    case 2:
                        errorno = ERRORNO.NETWORK_ERROR;
                        break;
                    case 3:
                        errorno = ERRORNO.RESULT_NOT_FOUND;
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
                    this.f1816a.f1818b.onGetSuggestionResult(new SuggestionResult(errorno));
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
            if (!this.f1816a.f1819c && str != null && str.length() > 0 && this.f1816a.f1818b != null) {
                this.f1816a.f1818b.onGetSuggestionResult(C0584c.m1776a(str));
            }
        }

        public void mo1816o(String str) {
        }

        public void mo1817p(String str) {
        }
    }

    SuggestionSearch() {
        this.f1817a = null;
        this.f1818b = null;
        this.f1819c = false;
        this.f1817a = new C0658d();
        this.f1817a.m2112a(new C0581a());
    }

    public static SuggestionSearch newInstance() {
        BMapManager.init();
        return new SuggestionSearch();
    }

    public void destroy() {
        if (!this.f1819c) {
            this.f1819c = true;
            this.f1818b = null;
            this.f1817a.m2110a();
            this.f1817a = null;
            BMapManager.destroy();
        }
    }

    public boolean requestSuggestion(SuggestionSearchOption suggestionSearchOption) {
        if (this.f1817a == null) {
            throw new IllegalStateException("searcher has been destroyed");
        } else if (suggestionSearchOption != null && suggestionSearchOption.f1821b != null && suggestionSearchOption.f1820a != null) {
            return this.f1817a.m2124a(suggestionSearchOption.f1821b, 0, suggestionSearchOption.f1820a, null, 12, CoordUtil.ll2point(suggestionSearchOption.f1822c));
        } else {
            throw new IllegalArgumentException("option or keyword or city can not be null");
        }
    }

    public void setOnGetSuggestionResultListener(OnGetSuggestionResultListener onGetSuggestionResultListener) {
        this.f1818b = onGetSuggestionResultListener;
    }
}
