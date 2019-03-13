package com.chaoliu.processor;

import com.chaoliu.utils.Consts;
import com.chaoliu.utils.Logger;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.chaoliu.utils.Consts.KEY_MODULE_NAME;
import static com.chaoliu.utils.Consts.NO_MODULE_NAME_TIPS;

public abstract class BaseProcessor extends AbstractProcessor {

    protected Filer mFiler; //输出位置
    protected Logger logger;
    protected Types types;
    protected Elements elementUtils;

    // Module name, maybe its 'app' or others
    protected String moduleName = null;
    protected Map<String, String> options = null;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init( processingEnv );
        mFiler = processingEnv.getFiler();
        types = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        logger = new Logger( processingEnv.getMessager() );
        options = processingEnv.getOptions();
        if (MapUtils.isNotEmpty( options )) {
            moduleName = options.get( KEY_MODULE_NAME );
        }
        if (StringUtils.isNotEmpty( moduleName )) {
            logger.info( "The user has configuration the module name, it was [" + moduleName + "]" );
        } else {
            logger.error( NO_MODULE_NAME_TIPS );
        }
    }

    //判断当前是不是activity类
    public boolean isActivity(TypeElement typeElement){
        TypeMirror activityTm = elementUtils.getTypeElement(Consts.ACTIVITY).asType();
        if (types.isSubtype(typeElement.asType(),activityTm )) return true;
        return false;
    }

    //判断当前类是fragment
    public boolean isFragment(TypeElement typeElement){
        TypeMirror fragmentTm = elementUtils.getTypeElement(Consts.FRAGMENT).asType();
        TypeMirror fragmentTmV4 = elementUtils.getTypeElement(Consts.FRAGMENT_V4).asType();
        if (types.isSubtype(typeElement.asType(),fragmentTm )
                || types.isSubtype(typeElement.asType(),fragmentTmV4 )){
            return true;
        }
        return false;
    }

    @Override
    public Set<String> getSupportedOptions() {
        return new HashSet<String>() {{
            this.add( KEY_MODULE_NAME );
        }};
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
