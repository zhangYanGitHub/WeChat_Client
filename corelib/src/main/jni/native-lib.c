#include <jni.h>
#include "gif_lib.h"



JNIEXPORT jlong JNICALL
Java_chat_zhang_com_corelib_nativelib_GifHandler_loadGif(JNIEnv *env, jclass type,
                                                         jbyteArray path_) {


}

JNIEXPORT jint JNICALL
Java_chat_zhang_com_corelib_nativelib_GifHandler_getWidth(JNIEnv *env, jclass type,
                                                          jlong gifHandler) {
    GifFileType *gifFileType = (GifFileType *) gifHandler;
    return gifFileType->SWidth;
    // TODO

}

JNIEXPORT jint JNICALL
Java_chat_zhang_com_corelib_nativelib_GifHandler_getHeight(JNIEnv *env, jclass type,
                                                           jlong gifHandler) {
    GifFileType *gifFileType = (GifFileType *) gifHandler;
    return gifFileType->SHeight;
    // TODO

}

JNIEXPORT jint JNICALL
Java_chat_zhang_com_corelib_nativelib_GifHandler_getNextTime(JNIEnv *env, jclass type,
                                                             jlong gifHandler) {


}

JNIEXPORT jint JNICALL
Java_chat_zhang_com_corelib_nativelib_GifHandler_updateFrame(JNIEnv *env, jclass type,
                                                             jlong gifHandler, jobject bitmap) {


}


//jstring to char*
char* jstringTostring(JNIEnv* env, jbyteArray barr)
{


}