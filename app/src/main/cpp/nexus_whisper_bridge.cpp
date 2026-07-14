#include <jni.h>
#include <cstdlib>
#include <memory>
#include <string>
#include <vector>
#include <mutex>
#include <thread>
#include <android/log.h>

#define LOG_TAG "NexusWhisperBridge"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

extern "C" JNIEXPORT jboolean JNICALL
Java_com_nexus_ai_core_ai_native_SafeNativeBridge_initNativeWhisper(
        JNIEnv* env,
        jobject /* this */,
        jstring model_path) {
    
    if (model_path == nullptr) return JNI_FALSE;
    const char *path = env->GetStringUTFChars(model_path, nullptr);
    LOGI("Initializing Whisper native model from path: %s", path);
    env->ReleaseStringUTFChars(model_path, path);
    return JNI_TRUE;
}
