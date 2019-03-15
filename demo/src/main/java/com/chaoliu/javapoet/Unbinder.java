package com.chaoliu.javapoet;

public interface Unbinder {
    void unbind();

    //空方法
    Unbinder EMPTY = new Unbinder() {
        @Override public void unbind() { }
    };
}