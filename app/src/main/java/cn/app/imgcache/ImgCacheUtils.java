package cn.app.imgcache;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by jiangzehui on 17/12/23.
 */

public class ImgCacheUtils {

    private NetCacheUtils netCacheUtils;
    private LocalCacheUtils localCacheUtils;
    private LruCacheUtils lruCacheUtils;

    public ImgCacheUtils(){
        lruCacheUtils = new LruCacheUtils();
        localCacheUtils = new LocalCacheUtils(lruCacheUtils);
        netCacheUtils = new NetCacheUtils(localCacheUtils,lruCacheUtils);
    }


    public void loadImg(ImageView iv, String url, ProgressBar pb){
        //从内存获取图片
        Bitmap bitmap = lruCacheUtils.getImgFromMemory(url);
        if(bitmap!=null){
            iv.setImageBitmap(bitmap);
            Log.d("main", "图片从内存加载成功");
            return;
        }

        //从本地获取图片
        bitmap = localCacheUtils.getImgFromLocal(url);
        if(bitmap!=null){
            iv.setImageBitmap(bitmap);
            Log.d("main", "图片从本地加载成功");
            return;
        }

        //从网络获取图片
        netCacheUtils.getImgFromNet(iv,url,pb);

    }





}
