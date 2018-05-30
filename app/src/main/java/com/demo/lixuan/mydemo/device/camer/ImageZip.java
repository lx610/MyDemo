package com.demo.lixuan.mydemo.device.camer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

/**
 * Created by Administrator on 2018/5/26.
 */
public class ImageZip {
    private static final String TAG = "ImageZip";

    /**获取缩放的图片文件
     * @param absolutePath
     * @param mWidth
     * @param mHeight
     * @return
     */
    public static Bitmap decodeSampledBitmapFromFile(String absolutePath, int mWidth, int mHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
       BitmapFactory.decodeFile(absolutePath, options);
        Bitmap relSrc =options.inBitmap;
        if (relSrc!=null)
        Log.d(TAG, "decodeSampledBitmapFromFile:relSrc ====================  " + relSrc.getByteCount());
        options.inSampleSize =calculateInSampleSize(options, mWidth, mHeight);
        options.inJustDecodeBounds =false;
        Bitmap src = BitmapFactory.decodeFile(absolutePath, options);
        Log.d(TAG, "decodeSampledBitmapFromFile:  options.inSampleSize ===============  " +  options.inSampleSize);
        Log.d(TAG, "decodeSampledBitmapFromFile:src = =============== " + src.getByteCount());
        return src;
    }



    private static int calculateInSampleSize(BitmapFactory.Options options, int mWidth, int mHeight) {
        int relHight = options.outHeight;
        int relWidth = options.outWidth;

        int sampleSize =1;
        if (relHight >relHight||relWidth >mHeight){
            int halfHight = relHight / 2;
            int helfWidth = relWidth / 2;;
            while((halfHight / sampleSize) > mHeight&& (helfWidth / sampleSize) >mWidth){
                sampleSize *= 2;
            }

        }
        return sampleSize;
    }
}
