package com.demo.lixuan.mydemo.device.camer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;

/**
 * Created by Administrator on 2018/5/25.
 */

public class CamerActivity extends BaseActivity {
    private static final String TAG = "CamerActivity";

    @BindView(R.id.surface_view)
    SurfaceView mSurfaceView;
    Camera mCamera;
    @BindView(R.id.tv_take_photo)
    TextView mTvTakePhoto;
    private SurfaceHolder mSurfaceHolder;

    public String[] permissions={
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE };

    @Override
    public int getLayoutResId() {
        return R.layout.activity_camera;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            mCamera = getCameraInstance();
            mSurfaceHolder = mSurfaceView.getHolder();
            mSurfaceHolder.addCallback(mSurfaceCallBack);
        }
    }

    @Override
    public void initView() {
        if (checkCameraHardware(this)) {
            //如果没有注册静态相机权限，这里的camera会永远是null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions,1);
            }else {
                mCamera = getCameraInstance();
                mSurfaceHolder = mSurfaceView.getHolder();
                mSurfaceHolder.addCallback(mSurfaceCallBack);
            }


        }

    }

    // 判断相机是否支持
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    // 获取相机
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
            c.setDisplayOrientation(90);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {
        mTvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCamera.takePicture(null, null, mPictureCallback);
            }
        });
    }
    private android.hardware.Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {
            File pictureDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            if (pictureDir == null) {
                Log.d(TAG,
                        "Error creating media file, check storage permissions!");
                return;
            }

            try {
                String pictureName ="tmp"
                        + ".png";
                FileOutputStream fos = new FileOutputStream(pictureDir
                        + File.separator + pictureName);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e) {
                Log.d(TAG, "Error accessing file: " + e.getMessage());
            }
        }
    };



    private SurfaceHolder.Callback mSurfaceCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                mCamera.setPreviewDisplay(surfaceHolder);
                mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            if (mSurfaceHolder.getSurface() == null) {
                return;
            }
            try {
                mCamera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    // 释放相机
    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }
}
