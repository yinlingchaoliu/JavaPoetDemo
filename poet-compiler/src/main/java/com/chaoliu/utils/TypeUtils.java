package com.chaoliu.utils;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import java.lang.reflect.Type;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

public class TypeUtils {

//    println(logger,enclosingElement);
//    println(logger,enclosingElement.asType());
//    println(logger,TypeName.get( enclosingElement.asType() ));
//    println(logger,enclosingElement.asType());

    public static void println(Logger logger, TypeElement element){
        logger.info( element.getQualifiedName().toString() );
    }

    public static void println(Logger logger, TypeMirror typeMirror){
        logger.info( typeMirror.toString() );
    }

    public static void println(Logger logger, TypeName typeName){
        logger.info( typeName.toString() );
    }

    public static void println(Logger logger, ClassName className){
        logger.info(className.toString());
    }

    public static void println(Logger logger, Type type){
        logger.info(TypeName.get( type ).toString());
    }
}
