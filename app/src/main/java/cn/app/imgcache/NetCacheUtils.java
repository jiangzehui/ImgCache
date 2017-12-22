package cn.app.imgcache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/20.
 */

public class NetCacheUtils {

    LocalCacheUtils localCacheUtils;
    MenoryCacheUtils menoryCacheUtils;
    public NetCacheUtils() {
        localCacheUtils = new LocalCacheUtils();
        menoryCacheUtils = new MenoryCacheUtils();
    }

    public void getImg(ImageView iv, String url){
        new ImgTask().execute(iv,url);//启动多线程下载图片
    }

    /**
     * AsyncTask就是对handler和线程池的封装
     * 第一个泛型:参数类型
     * 第二个泛型:更新进度的泛型
     * 第三个泛型:onPostExecute的返回结果
     */
    class ImgTask extends AsyncTask<Object,Void,Bitmap>{

        private ImageView iv;
        private String url;

        /**
         * 耗时方法结束后执行该方法,主线程中
         * @param bitmap
         */
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            iv.setImageBitmap(bitmap);
            Log.d("MAIN","从网络加载图片");
            localCacheUtils.setImgToLocal(url,bitmap);
            menoryCacheUtils.setBitmapToCache(url,bitmap);
        }

        /**
         * 更新进度在主线程
         * @param values
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        /**
         * 后台耗时操作，在子线程
         * @param objects
         * @return
         */
        @Override
        protected Bitmap doInBackground(Object... objects) {
            iv = (ImageView) objects[0];
            url = (String) objects[1];
            return domnLoadBitmap(url);
        }

        /**
         * 下载图片
         * @param url
         * @return
         */
        private Bitmap domnLoadBitmap(String url){
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");
                int code = connection.getResponseCode();
                if(code == 200){
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;//宽高压缩为原来的1/2
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream(),null,options);
                    return bitmap;
                }


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            return null;

        }

    }
}
