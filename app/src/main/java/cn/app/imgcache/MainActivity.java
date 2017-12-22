package cn.app.imgcache;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView iv;
    String url="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2546506461,4214746746&fm=11&gp=0.jpg";
    MenoryCacheUtils menoryCacheUtils = new MenoryCacheUtils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
       // LocalCacheUtils localCacheUtils = new LocalCacheUtils();
       // Bitmap bitmap = localCacheUtils.getImgFromLocal(url);

    }

    @Override
    protected void onResume() {
        super.onResume();

        Bitmap bitmap = menoryCacheUtils.getBitmapFromCache(url);//内存取
        if(bitmap == null){
            //本地取
            LocalCacheUtils localCacheUtils = new LocalCacheUtils();
            Bitmap bitmap1 = localCacheUtils.getImgFromLocal(url);
            if(bitmap1 == null){
                //网络取
                NetCacheUtils netCacheUtils = new NetCacheUtils();
                netCacheUtils.getImg(iv,url);
            }else{
                iv.setImageBitmap(bitmap1);
            }

        }else{
            iv.setImageBitmap(bitmap);
        }
    }
}
