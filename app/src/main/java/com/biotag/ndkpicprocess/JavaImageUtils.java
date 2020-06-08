package com.biotag.ndkpicprocess;

import android.graphics.Bitmap;
import android.graphics.Color;

public class JavaImageUtils {
    private static final float brightness = 0.2f;//亮度
    private static final float conparetion = 0.2f;//对比度

    public static Bitmap getJavaBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int a,r,g,b ;
        int bab = (int) (brightness*255);
        float cos = 1.f+0.2f;
        //循环遍历每个像素点
        for (int x = 0; x <width ; x++) {
            for (int y = 0; y < height; y++) {
                int color = bitmap.getPixel(x, y);
                a = Color.alpha(color);
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);
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
                result.setPixel(x,y,Color.argb(a,r,g,b));

            }

        }
        return result;
    }
}
