package com.amap.api.mapcore.util;

/* compiled from: IDownloadListener */
public interface ch {

    /* compiled from: IDownloadListener */
    public enum C0231a {
        amap_exception(-1),
        network_exception(-1),
        file_io_exception(0),
        success_no_exception(1),
        cancel_no_exception(2);
        
        private int f377f;

        private C0231a(int i) {
            this.f377f = i;
        }
    }

    void mo2989a(long j, long j2);

    void mo2990a(C0231a c0231a);

    void mo2993m();

    void mo2994n();

    void mo2995o();
}
