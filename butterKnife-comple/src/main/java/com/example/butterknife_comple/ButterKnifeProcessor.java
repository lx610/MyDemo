package com.example.butterknife_comple;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.lang.model.element.Element;


/**
 * className: ButterKnifeProcessor
 * description:
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/8/5 16:28
 */
public class ButterKnifeProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {

        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    /**
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {

        Set<String> set = new LinkedHashSet<>();
        set.add(BindView.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<TypeElement, List<FieldViewBinding>> targetMap = getTargetMap(roundEnv);
        createJavaFile(targetMap.entrySet());
        return false;
    }

    private void createJavaFile(Set<Map.Entry<TypeElement, List<FieldViewBinding>>> entrySet) {

        for (Map.Entry<TypeElement, List<FieldViewBinding>> typeElementListEntry : entrySet) {
            TypeElement typeElement = typeElementListEntry.getKey();
            List<FieldViewBinding> list = typeElementListEntry.getValue();
            if(list == null || list.size()==0){
                continue;
            }

            String packageName = elementUtils.getPackageOf(typeElement).getQualifiedName().toString();
            String className = typeElement.getQualifiedName().toString().substring(packageName.length() +1);
            String newClassName = className + "_ViewBinding";

            MethodSpec.Builder methodBuilder = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.bestGuess(className),"target");

            for (FieldViewBinding fieldViewBinding : list) {
                String packageNameString = fieldViewBinding.getFieldType().toString();
                ClassName viewClass = ClassName.bestGuess(packageNameString);
                methodBuilder.addStatement("target.$L=($T)target.findViewById($L)", fieldViewBinding.getFieldName()
                        , viewClass, fieldViewBinding.getViewId());
            }

            TypeSpec typeBuilder = TypeSpec.classBuilder(newClassName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(methodBuilder.build())
                    .build();

            JavaFile javaFile = JavaFile.builder(packageName, typeBuilder)
                    .addFileComment("Generate code from Butter Knife. Do not modify!")
                    .build();

            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private Map<TypeElement, List<FieldViewBinding>> getTargetMap(RoundEnvironment roundEnv) {

        Map<TypeElement, List<FieldViewBinding>> targetMap = new HashMap<>();
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(BindView.class);

        for (Element annotatedElement : annotatedElements) {
            String fieldName = annotatedElement.getSimpleName().toString();
            System.out.println("ButterKnifeProcessor fieldName:"  + fieldName);
            TypeMirror fieldType = annotatedElement.asType();
            int viewId = annotatedElement.getAnnotation(BindView.class).value();
            TypeElement typeElement = (TypeElement) annotatedElement.getEnclosingElement();
            List<FieldViewBinding> list = targetMap.get(typeElement);
            if (list == null) {
                list = new ArrayList<>();
                targetMap.put(typeElement, list);
            }
            list.add(new FieldViewBinding(fieldName, fieldType, viewId));
        }
        return targetMap;
    }
}
