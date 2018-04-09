package com.droi.sdk.core;

public class DroiReferenceObject {
    private Object f2670a = null;

    public Object droiObject() {
        return this.f2670a;
    }

    public void setDroiObject(Object obj) {
        if (obj instanceof DroiObject) {
            this.f2670a = obj;
        }
    }
}
