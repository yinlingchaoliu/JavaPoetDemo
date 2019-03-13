package com.chaoliu.processor;

import android.support.annotation.UiThread;

import com.chaoliu.annotation.ViewId;
import com.chaoliu.utils.Consts;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static com.chaoliu.utils.Consts.SUFFIX;
import static com.chaoliu.utils.Consts.WARNING_TIPS;

/**
 *
 * 查看javapoet编写java语法
 * @see 'https://github.com/square/javapoet'
 *
 */
@AutoService(Processor.class)
public class ViewIdProcessor extends BaseProcessor {

    private Map<TypeElement, List<Element>> parentAndChild = new HashMap<>();  //包含父类的注解

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init( processingEnv );
        logger.info(">>> ViewIdProcessor init. <<<");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        if (CollectionUtils.isNotEmpty( annotations )){

            Set<? extends Element> viewIdElements = roundEnv.getElementsAnnotatedWith(ViewId.class);
            try {
                categories( viewIdElements );
                gennerateHelper();
            } catch (Exception e) {
                logger.error( e );
            }

            return true;
        }
        return false;
    }

    //自动生成代码
    private void gennerateHelper() throws IOException {
        //根据类路径获得类型
        TypeElement type_unbinder = elementUtils.getTypeElement(Consts.UNBINDER);

        //用于判断当前类类型
        TypeMirror viewTm =  elementUtils.getTypeElement( Consts.VIEW ).asType();

        //ClassName.get(element.asType())

        if (MapUtils.isNotEmpty( parentAndChild )){

            for (Map.Entry<TypeElement, List<Element>> entry : parentAndChild.entrySet()){
                TypeElement typeElement = entry.getKey();
                List<Element> elementList = entry.getValue();

                //类全路径
                String qualifiedName = typeElement.getQualifiedName().toString();
                //包名
                String packageName = qualifiedName.substring(0, qualifiedName.lastIndexOf("."));
                //类文件
                String fileName = typeElement.getSimpleName() + SUFFIX;

                //新建类Target_ViewBinding
                TypeSpec.Builder targetClassType = TypeSpec.classBuilder( fileName )
                        .addModifiers( Modifier.PUBLIC)
                        .addJavadoc( WARNING_TIPS )
                        .addSuperinterface( ClassName.get(type_unbinder.asType()) ); //实现接口

                //新建field target字段
                FieldSpec targetField = FieldSpec.builder(TypeName.get( typeElement.asType() ),"target",Modifier.PRIVATE)
                        .build();

                //target类增加一行field
                targetClassType.addField( targetField );


                //新建构造方法Target_ViewBinding(Target target,View source)
                MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                        .addAnnotation( UiThread.class )
                        .addModifiers( Modifier.PUBLIC )
                        .addParameter( TypeName.get( typeElement.asType() ),"target" )
                        .addParameter( TypeName.get( viewTm ),"source" )
                        .addStatement( "this.target = target" );

                for (Element element :elementList){

                    //获取控件ID
                    ViewId viewIdAnnotation = element.getAnnotation( ViewId.class );
                    int viewId = viewIdAnnotation.value();

                    //获取当前field字段
                    String fieldName = element.getSimpleName().toString();
                    constructorBuilder.addStatement( "target."+fieldName + " = source.findViewById( $L )" ,viewId);
                }

                //创建构造方法
                MethodSpec  constructor = constructorBuilder.build();

                //target类增加构造方法
                targetClassType.addMethod( constructor );

                //新建方法 unbind
                MethodSpec.Builder unbindBuilder = MethodSpec.methodBuilder("unbind")
                        .addAnnotation( Override.class )
                        .addModifiers( Modifier.PUBLIC )
                        .returns( void.class );

                for (Element element :elementList){
                    String fieldName = element.getSimpleName().toString();
                    unbindBuilder.addStatement( "target."+fieldName+" = null" );
                }
                //创建释放方法
                MethodSpec unbinder = unbindBuilder.build();

                //target类增加释放方法
                targetClassType.addMethod( unbinder );

                //创建target类
                TypeSpec targetType = targetClassType.build();

                //写类
                JavaFile.builder(packageName, targetType).build().writeTo(mFiler);
                //打印
                JavaFile.builder(packageName, targetType).build().writeTo(System.out);
            }

        }
    }

    /**
     * 将父类的ID找出来
     * @param elements
     * @throws IllegalAccessException
     */
    private void categories(Set<? extends Element> elements) throws IllegalAccessException {
        if (CollectionUtils.isNotEmpty(elements)) {
            for (Element element : elements) {
                TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

                if (element.getModifiers().contains(Modifier.PRIVATE)) {
                    throw new IllegalAccessException("The inject fields CAN NOT BE 'private'!!! please check field ["
                            + element.getSimpleName() + "] in class [" + enclosingElement.getQualifiedName() + "]");
                }

                if (parentAndChild.containsKey(enclosingElement)) { // Has categries
                    parentAndChild.get(enclosingElement).add(element);
                } else {
                    List<Element> childs = new ArrayList<>();
                    childs.add(element);
                    parentAndChild.put(enclosingElement, childs);
                }
            }

            logger.info("categories finished.");
        }
    }


    //建议这种写法,减少改字符串
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return new HashSet<String>() {{
            this.add( ViewId.class.getName() );
        }};
    }

}
