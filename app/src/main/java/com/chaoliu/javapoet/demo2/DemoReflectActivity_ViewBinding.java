package com.chaoliu.javapoet.demo2;

import android.support.annotation.UiThread;
import android.view.View;

import com.chaoliu.api.templete.Unbinder;
import com.chaoliu.javapoet.R;

public class DemoReflectActivity_ViewBinding implements Unbinder {
  private DemoReflectActivity target;

  @UiThread
  public DemoReflectActivity_ViewBinding(DemoReflectActivity target, View source) {
    this.target = target;
    target.helloTv = source.findViewById( R.id.helloTv );
  }

  @Override
  public void unbind() {
    target.helloTv = null;
  }
}
