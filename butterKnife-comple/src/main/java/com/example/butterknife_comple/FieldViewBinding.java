package com.example.butterknife_comple;

import javax.lang.model.type.TypeMirror;

/**
 * className: FieldViewBinding
 * description:java类作用描述
 * author：lix
 * email：lixuan_1@163.com
 * date: 2020/7/31 17:29
 */
class FieldViewBinding {

    String fieldName;
    TypeMirror fieldType;
    int viewId;

    public FieldViewBinding(String fieldName, TypeMirror fieldType, int viewId) {

        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.viewId = viewId;
    }

    public String getFieldName() {

        return fieldName;
    }

    public void setFieldName(String fieldName) {

        this.fieldName = fieldName;
    }

    public TypeMirror getFieldType() {

        return fieldType;
    }

    public void setFieldType(TypeMirror fieldType) {

        this.fieldType = fieldType;
    }

    public int getViewId() {

        return viewId;
    }

    public void setViewId(int viewId) {

        this.viewId = viewId;
    }
}
