package cn.app.imgcache;

import android.graphics.Bitmap;
import android.os.AsyncTask;

/**
 * Created by Administrator on 2017/12/20.
 */

public class NetCacheUtils {

    /**
     * AsyncTask就是对handler和线程池的封装
     * 第一个泛型:参数类型
     * 第二个泛型:更新进度的泛型
     * 第三个泛型:onPostExecute的返回结果
     */
    class ImgTask extends AsyncTask<Object,Void,Bitmap>{

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(Object... objects) {
            return null;
        }
    }
}
