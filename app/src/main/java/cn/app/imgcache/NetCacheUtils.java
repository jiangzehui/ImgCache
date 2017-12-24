package cn.app.imgcache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jiangzehui on 17/12/19.
 */

public class NetCacheUtils {

    private LocalCacheUtils localCacheUtils;
    private LruCacheUtils lruCacheUtils;


    public NetCacheUtils(LocalCacheUtils localCacheUtils, LruCacheUtils lruCacheUtils){
        this.localCacheUtils = localCacheUtils;
        this.lruCacheUtils = lruCacheUtils;
    }

    public void getImgFromNet(ImageView iv,String url,ProgressBar pb){
        new task().execute(iv,url,pb);
    }



    class task extends AsyncTask<Object, Object, Bitmap> {
        private ImageView iv;
        private String url;
        private ProgressBar pb;


        @Override
        protected Bitmap doInBackground(Object... params) {
            iv = (ImageView) params[0];
            url = (String) params[1];
            pb = (ProgressBar) params[2];
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(20000);
                connection.setConnectTimeout(20000);
                int code = connection.getResponseCode();
                if (code == 200) {

                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] b =new byte[1024];
                    int len;//当前读取图片的长度
                    int totalLength = connection.getContentLength();//获取内容总长度
                    while ((len=inputStream.read(b))!=-1) {//不等于-1表示还没有读完
                        bos.write(b,0,len);
                        int progress = bos.size()*100/totalLength;
                        publishProgress(progress);
                        Log.d("MAIN","totalLength="+totalLength+", bos.size="+bos.size()+",progress="+progress);
                    }
                    b = bos.toByteArray();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 2;//压缩为图片的2分之一
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    //Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream(), null, options);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length,options);
                    Log.d("main", "图片从网络加载成功");
                    return bitmap;


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;


        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            iv.setImageBitmap(s);
            localCacheUtils.setImgToLocal(url, s);
            lruCacheUtils.setImgToMemory(url, s);

        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            int progress = (int) values[0];
            if(pb!=null){
                pb.setProgress(progress);
            }
        }
    }



}
