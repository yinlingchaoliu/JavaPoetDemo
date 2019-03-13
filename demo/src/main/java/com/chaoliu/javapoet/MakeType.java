package com.chaoliu.javapoet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Date;

import javax.lang.model.element.Modifier;

public class MakeType {

    public static void main(String[] args) {

        MethodSpec today = MethodSpec.methodBuilder( "today" )
                .returns( Date.class )
                .addStatement( "return new $T()", Date.class )
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC, Modifier.FINAL )
                .addMethod( today )
                .build();

        JavaFile javaFile = JavaFile.builder( "com.example.helloworld", helloWorld )
                .build();
        try {
            javaFile.writeTo( System.out );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
