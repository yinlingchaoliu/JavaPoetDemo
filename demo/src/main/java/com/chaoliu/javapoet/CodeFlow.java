package com.chaoliu.javapoet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.lang.model.element.Modifier;

public class CodeFlow {

 public static void main(String[] args){

     MethodSpec main = MethodSpec.methodBuilder("main")
             .addCode(""
                     + "int total = 0;\n"
                     + "for (int i = 0; i < 10; i++) {\n"
                     + "  total += i;\n"
                     + "}\n")
             .build();

     TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
             .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
             .addMethod(main)
             .build();

     JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
             .build();
     try {
         javaFile.writeTo(System.out);
     } catch (IOException e) {
         e.printStackTrace();
     }
 }



}
