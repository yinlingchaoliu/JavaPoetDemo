package com.chaoliu.javapoet.demo1;

import android.support.annotation.UiThread;
import android.view.View;

import com.chaoliu.api.templete.Unbinder;
import com.chaoliu.javapoet.MainActivity;
import com.chaoliu.javapoet.R;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY CHENTONG.
 */
public class Demo1Activity_ViewBinding implements Unbinder {
  private Demo1Activity target;

  @UiThread
  public Demo1Activity_ViewBinding(Demo1Activity target, View source) {
    this.target = target;
    target.helloTv = source.findViewById( R.id.helloTv );
  }

  @Override
  public void unbind() {
    target.helloTv = null;
  }
}
