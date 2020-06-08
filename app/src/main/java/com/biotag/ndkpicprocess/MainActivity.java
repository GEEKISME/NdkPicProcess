package com.biotag.ndkpicprocess;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private Bitmap bitmap;
    private ImageView iv_pic;
    private Button btn_origin;
    private Button btn_java;
    private Button btn_c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        // Example of a call to a native method
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    private void initView() {
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        btn_origin = (Button) findViewById(R.id.btn_origin);
        btn_java = (Button) findViewById(R.id.btn_java);
        btn_c = (Button) findViewById(R.id.btn_c);

        btn_origin.setOnClickListener(this);
        btn_java.setOnClickListener(this);
        btn_c.setOnClickListener(this);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.pic4);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_origin:

                iv_pic.setImageBitmap(bitmap);
                break;
            case R.id.btn_java:
                long start = System.currentTimeMillis();
                Bitmap bt = JavaImageUtils.getJavaBitmap(bitmap);
                iv_pic.setImageBitmap(bt);
                long end = System.currentTimeMillis();
                Toast.makeText(this, "java pic process:"+(end-start)+"", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_c:
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int[] buffer = new int[width * height];
                bitmap.getPixels(buffer,0,width,1,1,width-1,height-1);
                long start_1 = System.currentTimeMillis();
                //ndk处理
                int[] ndkImage = NdkImageUtils.getNdkImage(buffer, width, height);
                long end_1 = System.currentTimeMillis();
                Bitmap rebitmap = Bitmap.createBitmap(ndkImage, width, height,
                        Bitmap.Config.ARGB_8888);
                iv_pic.setImageBitmap(rebitmap);
                Toast.makeText(this, "c process :"+(end_1-start_1), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
