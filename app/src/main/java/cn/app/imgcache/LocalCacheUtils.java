package cn.app.imgcache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Administrator on 2017/12/22.
 */

public class LocalCacheUtils {

    private final String CACHE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Img";

    /**
     * 缓存图片到本地
     * @param url
     * @param bitmap
     */
    public void setImgToLocal(String url, Bitmap bitmap){
        String filename = MD5Encoder.encode(url);
        File fileDic = new File(CACHE_PATH);
        //通过得到文件的父文件
        if(!fileDic.exists()){
            fileDic.mkdirs();
        }
        File file = new File(fileDic.getAbsolutePath(),filename);
        //把图片保存在本地
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
            Log.d("MAIN","已缓存图片到本地");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取本地缓存的图片
     * @param url
     * @return
     */
    public Bitmap getImgFromLocal(String url){
        String filename = MD5Encoder.encode(url);
        File file = new File(CACHE_PATH,filename);
        try {
            Log.d("MAIN","从本地读取图片");
            return BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;


    }
}
