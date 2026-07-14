#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_nexus_ai_core_ai_native_NativeBridges_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Llama Native Bridge Initialized";
    return env->NewStringUTF(hello.c_str());
}
