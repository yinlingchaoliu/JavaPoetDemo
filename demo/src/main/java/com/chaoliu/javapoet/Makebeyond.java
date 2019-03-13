package com.chaoliu.javapoet;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.lang.model.element.Modifier;

public class Makebeyond {

    public static void main(String[] args) {

        ClassName hoverboard = ClassName.get( "com.mattel", "Hoverboard" );
        ClassName list = ClassName.get( "java.util", "List" );
        TypeName listOfHoverboards = ParameterizedTypeName.get( list, hoverboard );

        MethodSpec beyond = MethodSpec.methodBuilder( "beyond" )
                .addCode( "List<Hoverboard> result = new ArrayList<>();\n" +
                        "result.add(new Hoverboard());\n" +
                        "result.add(new Hoverboard());\n" +
                        "result.add(new Hoverboard());\n"
                        + "return result;\n" )
                .returns( listOfHoverboards )
                .build();

        TypeSpec helloWorld = TypeSpec.classBuilder( "HelloWorld" )
                .addModifiers( Modifier.PUBLIC, Modifier.FINAL )
                .addMethod( beyond )
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
