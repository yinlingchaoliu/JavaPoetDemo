package com.chaoliu.api.templete;

import android.support.annotation.UiThread;

public interface Unbinder {
    @UiThread
    void unbind();

    //空方法
    Unbinder EMPTY = new Unbinder() {
        @Override public void unbind() { }
    };
}