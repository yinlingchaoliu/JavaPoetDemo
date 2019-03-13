package com.chaoliu.api.core;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.view.View;

import com.chaoliu.api.templete.Unbinder;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 精简的butterknife框架
 * @author chentong
 */
public class ButterKnife {

    private static final Map<Class<?>, Constructor<? extends Unbinder>> BINDINGS = new LinkedHashMap<>();

    private ButterKnife() {
        throw new AssertionError( "No instances." );
    }

    @NonNull
    @UiThread
    public static Unbinder bind(@NonNull Activity target) {
        View sourceView = target.getWindow().getDecorView();
        return createBinding( target, sourceView );
    }

    @NonNull
    @UiThread
    public static Unbinder bind(@NonNull Object target, @NonNull View source) {
        return createBinding( target, source );
    }

    private static Unbinder createBinding(Object target, View source) {

        Class<?> targetClass = target.getClass();
        Constructor<? extends Unbinder> constructor = findBindingConstructorForClass( targetClass );
        if (constructor == null) {
            return Unbinder.EMPTY;
        }

        //实例化
        try {
            return constructor.newInstance( target, source);
        }catch (Exception e){
            throw new RuntimeException("Unable to invoke " + constructor, e);
        }
    }

    private static Constructor<? extends Unbinder> findBindingConstructorForClass(Class<?> targetClass) {
        //查缓存
        Constructor<? extends Unbinder> binderConstructor = BINDINGS.get( targetClass );
        if (binderConstructor != null) {
            return binderConstructor;
        }

        String clazzName = targetClass.getName();
        if (clazzName.startsWith( "android." ) || clazzName.startsWith( "java." )) {
            //系统方法错误
            return null;
        }

        try {
            //反射类加载
            Class<?> bindingClass = targetClass.getClassLoader().loadClass( clazzName + "_ViewBinding" );
            binderConstructor = (Constructor<? extends Unbinder>) bindingClass.getConstructor( targetClass, View.class );
        }catch (ClassNotFoundException e){
            binderConstructor = findBindingConstructorForClass(targetClass.getSuperclass());
        }catch (Exception e){
            return null;
        }

        if (binderConstructor!=null){
            BINDINGS.put( targetClass, binderConstructor );
        }
        return binderConstructor;
    }

    @NonNull
    @UiThread
    public static void unbind(Unbinder target) {
        if (target != null && target != Unbinder.EMPTY) {
            target.unbind();
        }
    }

}