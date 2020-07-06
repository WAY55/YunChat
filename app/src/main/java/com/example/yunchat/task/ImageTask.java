package com.example.yunchat.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.yunchat.configs.IpConfig;
import com.example.yunchat.utils.HttpUtils;

/**
 * 头像Task
 * @author 曾健育
 */
public class ImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView thisImage;
    private final String urlPath = "http://" + IpConfig.getAddress() + "/avatar/";
    public ImageTask(ImageView thisImage) {
        this.thisImage = thisImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmap = null;
        try {
            bitmap = HttpUtils.getUrlImage(urlPath + strings[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        thisImage.setImageBitmap(bitmap);
        super.onPostExecute(bitmap);
    }
}
