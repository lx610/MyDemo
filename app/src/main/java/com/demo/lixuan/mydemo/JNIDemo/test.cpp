//test.cpp
#include "com_demo_lixuan_mydemo_JNIDemo_JniTest.h"
#include<stdio.h>

JNIEXPORT jstring JNICALL Java_com_demo_lixuan_mydemo_JNIDemo_JniTest_get(JNIEnv *env, jobject thisz){
    print("invoke get in c++\n");
    return (*env)->NewStringUTF(env,"Hello from JNI !")
}

JNIEXPORT void JNICALL  Java_com_demo_lixuan_mydemo_JNIDemo_JniTest_set(JNIEnv *env, jobject thisz,jstring string){
    printf("invoke set from C++\n");
    char*str = (char*)env->GetStringUTFChars(env,string,NULL);
    printf("%s\n",str);
    env->ReleaseStringUTFChars(env,string,str);
}