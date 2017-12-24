package cn.app.imgcache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by jiangzehui on 17/12/23.
 */

public class LruCacheUtils {
    LruCache<String, Bitmap> lruCache;
    public LruCacheUtils(){
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;
        lruCache = new LruCache<String, Bitmap>((int) maxMemory) {

            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    //内存缓存
    public void setImgToMemory(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
        Log.d("main", "图片缓存到内存成功");
    }

    public Bitmap getImgFromMemory(String url) {
        return lruCache.get(url);
    }
}
