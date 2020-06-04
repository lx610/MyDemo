package com.demo.lixuan.mydemo.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;


import java.io.File;

/**
 * author：yehexing
 * email：418198972@163.com
 * date: 2018/5/2 18:40
 */

public class SafetyUtils {

    /** install sucess */
    protected static final int SUCCESS = 0;
    /** SIGNATURES_INVALIDATE */
    protected static final int SIGNATURES_INVALIDATE = 3;
    /** SIGNATURES_NOT_SAME */
    protected static final int VERIFY_SIGNATURES_FAIL = 4;
    /** is needcheck */
    private static final boolean NEED_VERIFY_CERT = true;

    /**
     * checkPagakgeSigns.
     */
    public static int checkPagakgeSign(Context context, String srcPluginFile) {

        PackageInfo PackageInfo = context.getPackageManager().getPackageArchiveInfo(srcPluginFile, 0);
        //Signature[] pluginSignatures = PackageInfo.signatures;
        Signature[] pluginSignatures = null;
        if(Build.VERSION.SDK_INT>=21)
        {
            pluginSignatures = InstallUtil.showUninstallAPKSignaturesAfter21(srcPluginFile);
        }else{
            pluginSignatures = InstallUtil.showUninstallAPKSignatures(srcPluginFile);
        }
        boolean isDebugable = (0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE));
        if (pluginSignatures == null) {
//            MontLog.e("签名验证失败", srcPluginFile);
            new File(srcPluginFile).delete();
            return SIGNATURES_INVALIDATE;
//        } else if (NEED_VERIFY_CERT && !isDebugable) {
        } else if (NEED_VERIFY_CERT) {
            //可选步骤，验证APK证书是否和现在程序证书相同。
            Signature[] mainSignatures = null;
            try {
                PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(
                        context.getPackageName(), PackageManager.GET_SIGNATURES);
                mainSignatures = pkgInfo.signatures;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (!InstallUtil.isSignaturesSame(mainSignatures, pluginSignatures)) {
//                MontLog.e("升级包证书和旧版本证书不一致", srcPluginFile);
                new File(srcPluginFile).delete();
                return VERIFY_SIGNATURES_FAIL;
            }
        }
        return SUCCESS;
    }

    /**
     * checkPagakgeName
     * @param context
     * @param srcNewFile
     * @return
     */
    public static boolean checkPagakgeName (Context context, String srcNewFile) {
        PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(srcNewFile, PackageManager.GET_ACTIVITIES);

        if (packageInfo != null) {

            return TextUtils.equals(context.getPackageName(), packageInfo.packageName);
        }

        return false;
    }

    /**
     * checkFile
     *
     * @param aPath
     *            文件路径
     * @param context
     *            context
     */
    public static boolean checkFile(String aPath, Context context) {
//        File aFile = new File(aPath);
        if (!FileUtil.fileIsExists(aPath)) {
//            ToolToast.showToast(context, context.getString(R.string.install_package_delete));
            Toast.makeText(context,"安装包已经删除",Toast.LENGTH_SHORT);
            return false;
        }
        if  (context == null)  {
//            ToolToast.showToast(context, context.getString(R.string.install_package_exception));
            Toast.makeText(context,"安装出错",Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }


}
