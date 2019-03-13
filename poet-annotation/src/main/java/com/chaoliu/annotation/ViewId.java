package com.chaoliu.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.CLASS;

//FIELD字段  TYPE类
@Retention(CLASS) @Target(FIELD)
public @interface ViewId {
    /** View ID to which the field will be bound. */
    @IdRes int value();
}