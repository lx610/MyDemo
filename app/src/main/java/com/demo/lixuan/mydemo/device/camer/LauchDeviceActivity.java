package com.demo.lixuan.mydemo.device.camer;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import androidx.core.content.FileProvider;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.demo.lixuan.mydemo.base.publicLayout.LinearActivity;
import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.Utils.UiUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Administrator on 2018/5/25.
 */

public class LauchDeviceActivity extends LinearActivity {

    private static final int INTENT_FOR_CAMERA = 690;
    private static final int INTENT_FOR_GALLARY = 750;
    private static final int PERSMISION_REQUEST= 870;
    private static final int CROP_PHOTO = 950;

    private ImageView mImageView;

    private File tempFile;
    private boolean enablehandlerPic =false;

    //裁剪后的图片Uri路径，uritempFile为Uri类变量
    Uri uriCropTempFile = Uri.parse("file://" + "/" + Environment.getDownloadCacheDirectory().getPath() + "/" + "croptemp.jpg");

    @Override
    public void initView(Bundle savedInstanceState) {
        mImageView = new ImageView(this);
        handlerPermission();

        //创建临时存放
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            tempFile = new File(Environment.getDownloadCacheDirectory(),
                    "tempFile");
        } else {
            Toast.makeText(this, "请插入sd卡", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            tempFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }



        mLlContainer.addView(generateTextButton("lauch camera", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//
//                Intent intent =new Intent();
//                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));//没有这一句，就会在onResult返回data

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Android 7.0
                    //兼容android7.0 使用共享文件的形式
                    ContentValues contentValues = new ContentValues(1);
                    contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
                    Uri mCameraUri = FileProvider.getUriForFile(UiUtils.getContext(), UiUtils.getString(R.string.app_authorities), tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mCameraUri);
                }else {
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                }
                startActivityForResult(intent,INTENT_FOR_CAMERA);

            }
        }));

        mLlContainer.addView(generateTextButton("lauch picture", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT,null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intent,INTENT_FOR_GALLARY);
            }
        }));

        mLlContainer.addView(mImageView);
    }

    /**
     * 不动态请求写入方法，BitMap会无法解析图片
     */
    private void handlerPermission() {
        String[] permissons = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissons,PERSMISION_REQUEST);
        }else {
            enablehandlerPic = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERSMISION_REQUEST){
            enablehandlerPic = true;
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!= RESULT_OK){
            return;
        }

        if (!enablehandlerPic){
            UiUtils.makeText("未获得6.0 权限");
        }
        switch (requestCode){
            case INTENT_FOR_CAMERA:
                if (data!=null){
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap pBitmap = (Bitmap) bundle.get("data");
                        if (pBitmap != null) {
                            mImageView.setImageBitmap(pBitmap);
                        }

                    }
               }else {
                    int mWidth =360;
                    int mHeight=150;
                    Bitmap bitmap = ImageZip.decodeSampledBitmapFromFile(tempFile.getAbsolutePath(),
                            mWidth, mHeight);
                    mImageView.setImageBitmap(bitmap);
                }
                break;

            case INTENT_FOR_GALLARY :
               String imagePath= setPhotoForMiuiSystem(data);

//                String filePath = getRealPathFromURI(data.getData());
//                int mWidth =360;
//                int mHeight=150;
//                Bitmap bitmap = ImageZip.decodeSampledBitmapFromFile(filePath, mWidth, mHeight);
//                mImageView.setImageBitmap(bitmap);
                startPhotoCrop(data,imagePath);
                break;

            case CROP_PHOTO://处理裁剪返回结果
                try {
                    Bitmap bitmap;
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriCropTempFile));
                    mImageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = managedQuery(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private String setPhotoForMiuiSystem(Intent data) {
        Uri localUri = data.getData();
        String scheme = localUri.getScheme();
        String imagePath = "";
        if ("content".equals(scheme)) {
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(localUri, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            imagePath = c.getString(columnIndex);
            c.close();
        } else if ("file".equals(scheme)) {//小米4选择云相册中的图片是根据此方法获得路径
            imagePath = localUri.getPath();
        }
        int mWidth =360;
        int mHeight=150;
        Bitmap bitmap = ImageZip.decodeSampledBitmapFromFile(imagePath, mWidth, mHeight);
        mImageView.setImageBitmap(bitmap);
        return imagePath;
    }

    /**
     * 开启裁剪相片
     * @param data
     * @param imagePath
     */
    public void startPhotoCrop(Intent data, String imagePath) {
        //创建file文件，用于存储剪裁后的照片
//        File cropImage = new File(Environment.getExternalStorageDirectory(), "crop_image.jpg");
        File cropImage = new File(getExternalCacheDir(), "crop_image.jpg");
        try {
            if (cropImage.exists()) {
                cropImage.delete();
            }
            cropImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri cropImgUri = Uri.fromFile(cropImage);
        Intent intent = new Intent("com.android.camera.action.CROP");

        //设置源地址uri
//        Uri uriScr = FileProvider.getUriForFile(this, getPackageName() + ".provider", new File(path));
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            intent.setDataAndType(data.getData(), "image/*");
        }else {
            Uri uri= Uri.parse("file://" + imagePath);
            intent.setDataAndType(uri, "image/*");
        }

        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        intent.putExtra("scale", true);
        //设置目的地址uri
//        Uri uri = FileProvider.getUriForFile(UiUtils.getContext(), UiUtils.getString(R.string.app_authorities), iamgeFile);
        Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", cropImage);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        //设置图片格式
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        /**
         * 此方法返回的图片只能是小图片（sumsang测试为高宽160px的图片）
         * 故只保存图片Uri，调用时将Uri转换为Bitmap，此方法还可解决miui系统不能return data的问题
         */
        //intent.putExtra("return-data", true);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriCropTempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection

        startActivityForResult(intent, CROP_PHOTO);
    }





}
