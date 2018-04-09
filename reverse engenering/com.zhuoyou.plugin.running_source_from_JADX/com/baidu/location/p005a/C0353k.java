package com.baidu.location.p005a;

class C0353k implements Runnable {
    final /* synthetic */ C0352j f284a;

    C0353k(C0352j c0352j) {
        this.f284a = c0352j;
    }

    public void run() {
        if (this.f284a.f274c != null) {
            this.f284a.f274c.unregisterListener(C0352j.f271d, this.f284a.f274c.getDefaultSensor(6));
        }
    }
}
