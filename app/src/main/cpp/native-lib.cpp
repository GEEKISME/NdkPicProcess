#include <jni.h>
#include <string>
#include <stdlib.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_biotag_ndkpicprocess_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jintArray JNICALL
Java_com_biotag_ndkpicprocess_NdkImageUtils_getNdkImage(JNIEnv *env, jclass clazz, jintArray buffer,
                                                        jint width, jint height) {
    // TODO: implement getNdkImage()
    jint  * source = (*env).GetIntArrayElements( buffer,0);
    float brightness = 0.2f;//亮度
    float conparetion = 0.2f;//对比度
    int a,r,g,b;
    int bab = (int) (brightness*255);
    float cos = 1.f+0.2f;
    //循环遍历每个像素点
    int newSize = width*height;
    int x;
    int y;

    for (int x = 0; x <width ; x++) {
        for (y = 0; y < height; y++) {
           int  color = source[y*width+x];
            a = color >> 24;

            r = (color >> 16) & 0xff;
            g = (color >> 8) & 0xff;
            b = color & 0xff;
            //美白
            int ri = r + bab;
            int gi = g + bab;
            int bi = b + bab;
            //边界检测
            r = ri>255?255:(ri<0?0:ri);
            g = gi>255?255:(gi<0?0:gi);
            b = bi>255?255:(bi<0?0:bi);
            //扩大对比对
            ri = r-128;
            gi = g-128;
            bi = b-128;

            ri =(int) (ri * cos);
            gi =(int) (gi * cos);
            bi =(int) (bi * cos);

            ri = ri+128;
            gi = gi+128;
            bi = bi+128;


            r = ri>255?255:(ri<0?0:ri);
            g = gi>255?255:(gi<0?0:gi);
            b = bi>255?255:(bi<0?0:bi);

            source[y*width+x] = (a << 24) | (r << 16) | (g << 8) | b ;
        }
    }
    //第一步：保存  第二： 资源释放
    jintArray result = (*env).NewIntArray(newSize);
    (*env).SetIntArrayRegion(result,0,newSize,source);
    (*env).ReleaseIntArrayElements(buffer,source,0);
    return result;
}