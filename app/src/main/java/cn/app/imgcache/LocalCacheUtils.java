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
 * Created by jiangzehui on 17/12/23.
 */

public class LocalCacheUtils {
    private LruCacheUtils lruCacheUtils;
    private final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Img";

    public LocalCacheUtils(LruCacheUtils lruCacheUtils){
        this.lruCacheUtils = lruCacheUtils;
    }

    //本地缓存
    public void setImgToLocal(String url, Bitmap bitmap) {

        File fileDic = new File(path);
        if (!fileDic.exists()) {
            fileDic.mkdirs();
        }
        String filename = MD5Util.MD5(url);
        File file = new File(fileDic.getAbsolutePath(), filename);
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(file));
            Log.d("main", "图片缓存到本地成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImgFromLocal(String url) {
        String filename = MD5Util.MD5(url);
        File file = new File(path, filename);

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
            if(bitmap != null){
                lruCacheUtils.setImgToMemory(url,bitmap);
            }
            return bitmap;
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            return null;
        }
    }

}
