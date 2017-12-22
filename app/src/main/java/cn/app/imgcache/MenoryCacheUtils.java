package cn.app.imgcache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/23.
 */

public class MenoryCacheUtils {
    //强引用
    //private HashMap<String,Bitmap> mMemoryCache = new HashMap<>();//强引用，容易造成内存泄漏，所以考虑使用弱引用方法
   // private HashMap<String,SoftReference<Bitmap>> mMemoryCache = new HashMap<>();//软引用，在android2.3以后，系统会优先回收弱引用对象，所以考虑使用LruCache
    private  LruCache<String,Bitmap> mMemoryCache;

    public MenoryCacheUtils() {
        long maxMemory = Runtime.getRuntime().maxMemory()/8;//得到手机最大允许内容的1/8，超过指定内存就开始回收
        mMemoryCache = new LruCache<String,Bitmap>((int) maxMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();//计算每个图片的大小
            }
        };
    }

    public Bitmap getBitmapFromCache(String url){
        return mMemoryCache.get(url);//强引用和弱引用都是这样使用
//        SoftReference<Bitmap> softReference = mMemoryCache.get(url);
//        if(softReference!=null){
//            Log.d("MAIN","从内存读取图片");
//            return softReference.get();
//        }else{
//            return null;
//        }


    }

    /**
     * 往内存中存图片
     * @param url
     * @param bitmap
     */
    public void setBitmapToCache(String url,Bitmap bitmap){
        mMemoryCache.put(url,bitmap);//强引用和弱引用都是这样使用

       // mMemoryCache.put(url,new SoftReference<>(bitmap));
        Log.d("MAIN","已缓存图片到内存");

    }

}
