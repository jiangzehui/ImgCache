package cn.app.imgcache;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ImageView iv;
    ProgressBar pb;
    String url = "http://img0.pcauto.com.cn/pcauto/1607/04/8409974_406.png";
    ImgCacheUtils imgCacheUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv =  findViewById(R.id.tv);
        iv =  findViewById(R.id.iv);
        pb =  findViewById(R.id.pb);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
            }
        });
        imgCacheUtils = new ImgCacheUtils();


    }

    @Override
    protected void onResume() {
        super.onResume();
        imgCacheUtils.loadImg(iv,url,pb);
    }






}
