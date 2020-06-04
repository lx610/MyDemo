package com.demo.lixuan.mydemo.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import android.util.DisplayMetrics;
import android.widget.Toast;


import com.demo.lixuan.mydemo.BuildConfig;
import com.demo.lixuan.mydemo.R;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;

/**
 * author: WangGaozhuo
 * email:wanggaozhuo@yeah.net
 * date: 2017/8/8 18:00
 */

public class InstallUtil {

    private final static String TAG = "InstallUtil";

    public static void installApp(Context context, String filePath) {

        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                //兼容7.0
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri contentUri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileProvider", file);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                    //兼容8.0
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && Build.VERSION.SDK_INT < 28) {
//                        boolean hasInstallPermission = context.getPackageManager().canRequestPackageInstalls();
//                        if (!hasInstallPermission) {
//                            ToolToast.showToast(context,context.getString(R.string.string_install_unknow_apk_note));
//                            startInstallPermissionSettingActivity(context);
//                            return;
//                        }
//                    }
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                if (context.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
                    context.startActivity(intent);
                }
            } catch (Throwable e) {
                e.printStackTrace();
//                ToolToast.showToast(context,e.getMessage());
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT);
            }

//            Intent install = new Intent(Intent.ACTION_VIEW);
//            Uri downloadFileUri = Uri.fromFile(file);
//            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(install);
        }
    }

    /**
     * 跳转到设置-允许安装未知来源-页面
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private static void startInstallPermissionSettingActivity(Context context) {
        //注意这个是8.0新API
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void installApK(Context context, final String path) {

        if (!SafetyUtils.checkFile(path, context)) {
            return;
        }

        if (!SafetyUtils.checkPagakgeName(context, path)) {
//            ToolToast.showToast(context, context.getResources().getString(R.string.install_package_error));
            Toast.makeText(context,context.getResources().getString(R.string.install_package_error),Toast.LENGTH_SHORT);
            if(FileUtil.fileIsExists(path)){
                File f = new File(path);
                f.delete();
            }
            return;
        }

        switch (SafetyUtils.checkPagakgeSign(context, path)) {

            case SafetyUtils.SUCCESS:
                installApp(context,path);
                break;
            case SafetyUtils.SIGNATURES_INVALIDATE:
//                ToolToast.showToast(context, context.getString(R.string.install_signatures_error));
                Toast.makeText(context,context.getResources().getString(R.string.install_signatures_error),Toast.LENGTH_SHORT);
                break;
            case SafetyUtils.VERIFY_SIGNATURES_FAIL:
//                ToolToast.showToast(context, context.getString(R.string.install_verify__signatures_error));
                Toast.makeText(context,context.getResources().getString(R.string.install_verify__signatures_error),Toast.LENGTH_SHORT);
                break;
            default:
                break;
        }

    }

    public static Signature[] showUninstallAPKSignatures(String apkPath) {
        String PATH_PackageParser = "android.content.pm.PackageParser";
        try {
            // apk包的文件路径
            // 这是一个Package 解释器, 是隐藏的
            // 构造函数的参数只有一个, apk文件的路径
            // PackageParser packageParser = new PackageParser(apkPath);
            Class pkgParserCls = Class.forName(PATH_PackageParser);
            Class[] typeArgs = new Class[1];
            typeArgs[0] = String.class;
            Constructor pkgParserCt = pkgParserCls.getConstructor(typeArgs);
            Object[] valueArgs = new Object[1];
            valueArgs[0] = apkPath;
            Object pkgParser = pkgParserCt.newInstance(valueArgs);
            MontLog.i(TAG, "pkgParser:" + pkgParser.toString());
            // 这个是与显示有关的, 里面涉及到一些像素显示等等, 我们使用默认的情况
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            // PackageParser.Package mPkgInfo = packageParser.parsePackage(new
            // File(apkPath), apkPath,
            // metrics, 0);
            typeArgs = new Class[4];
            typeArgs[0] = File.class;
            typeArgs[1] = String.class;
            typeArgs[2] = DisplayMetrics.class;
            typeArgs[3] = Integer.TYPE;
            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
                    "parsePackage", typeArgs);
            valueArgs = new Object[4];
            valueArgs[0] = new File(apkPath);
            valueArgs[1] = apkPath;
            valueArgs[2] = metrics;
            valueArgs[3] = PackageManager.GET_SIGNATURES;
            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
                    valueArgs);

            typeArgs = new Class[2];
            typeArgs[0] = pkgParserPkg.getClass();
            typeArgs[1] = Integer.TYPE;
            Method pkgParser_collectCertificatesMtd = pkgParserCls
                    .getDeclaredMethod("collectCertificates", typeArgs);
            valueArgs = new Object[2];
            valueArgs[0] = pkgParserPkg;
            valueArgs[1] = PackageManager.GET_SIGNATURES;
            pkgParser_collectCertificatesMtd.invoke(pkgParser, valueArgs);
            // 应用程序信息包, 这个公开的, 不过有些函数, 变量没公开
            Field packageInfoFld = pkgParserPkg.getClass().getDeclaredField(
                    "mSignatures");
            Signature[] info = (Signature[]) packageInfoFld.get(pkgParserPkg);

            MontLog.i(TAG, info[0].toCharsString());
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Signature[]  showUninstallAPKSignaturesAfter21(String apkPath) {
        String PATH_PackageParser = "android.content.pm.PackageParser";
        try {
            // PackageParser packageParser = new PackageParser();

            Class pkgParserCls = Class.forName(PATH_PackageParser);
            Constructor pkgParserCt = pkgParserCls.getConstructor( new Class[]{});
            Object pkgParser = pkgParserCt.newInstance(new Object[]{});
            MontLog.i(TAG, "pkgParser:" + pkgParser.toString());
            // 这个是与显示有关的, 里面涉及到一些像素显示等等, 我们使用默认的情况
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            //方法原型： public Package parsePackage(File packageFile, int flags) throws PackageParserException

            Method pkgParser_parsePackageMtd = pkgParserCls.getDeclaredMethod(
                    "parsePackage",  new Class[]{File.class,int.class});

            Object pkgParserPkg = pkgParser_parsePackageMtd.invoke(pkgParser,
                    new Object[]{new File(apkPath),PackageManager.GET_SIGNATURES});

            if (Build.VERSION.SDK_INT >= 28) {

                Method pkgParser_collectCertificatesMtd = pkgParserCls.getDeclaredMethod("collectCertificates", pkgParserPkg.getClass(), Boolean.TYPE);
                pkgParser_collectCertificatesMtd.invoke(pkgParser, pkgParserPkg, Build.VERSION.SDK_INT>28);
                Method pkgParser_collect  = pkgParserCls.getDeclaredMethod("collectCertificates", pkgParserPkg.getClass(), Boolean.TYPE);
                pkgParser_collect.invoke(pkgParser, pkgParserPkg, false);
                Field mSigningDetailsField = pkgParserPkg.getClass().getDeclaredField("mSigningDetails"); // SigningDetails
                mSigningDetailsField.setAccessible(true);
                Object mSigningDetails =  mSigningDetailsField.get(pkgParserPkg);
                Field infoField = mSigningDetails.getClass().getDeclaredField("signatures");
                infoField.setAccessible(true);
                Signature[] info = (Signature[]) infoField.get(mSigningDetails);

                MontLog.i(TAG, info[0].toCharsString());
                return info;
            } else {
                Method pkgParser_collectCertificatesMtd = pkgParserCls
                        .getDeclaredMethod("collectCertificates", new Class[]{ pkgParserPkg.getClass(),Integer.TYPE});

                pkgParser_collectCertificatesMtd.invoke(pkgParser, new Object[]{pkgParserPkg,PackageManager.GET_SIGNATURES});
                // 应用程序信息包, 这个公开的, 不过有些函数, 变量没公开
                Field packageInfoFld = pkgParserPkg.getClass().getDeclaredField("mSignatures");
                Signature[] info = (Signature[]) packageInfoFld.get(pkgParserPkg);

                MontLog.i(TAG, info[0].toCharsString());
                return info;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isSignaturesSame(Signature[] main,Signature[] src){
        boolean isSame = false;
        Signature mainSign = main[0];
        String[] mainA = parseSignature(mainSign.toByteArray());
        Signature srcSign = src[0];
        String[] srcA = parseSignature(srcSign.toByteArray());

        if(mainA.length < 4 || srcA.length < 4){
            MontLog.i(TAG,mainA.length+ "-" + srcA.length);
            isSame = false;
        }else{
            if((mainA[0] != null && srcA[0] != null && mainA[0].equals(srcA[0])) && (mainA[2] != null && srcA[2] != null && mainA[2].equals(srcA[2])) && (mainA[3] != null && srcA[3] != null && mainA[3].equals(srcA[3]))){
//            if(mainA[2] != null && srcA[2] != null && mainA[2] == srcA[2]){
                if((mainA[1] != null && srcA[1] != null && mainA[1].equals(srcA[1]))) {
                    MontLog.i(TAG,"OpenSSLRSAPublicKey==");
                    isSame = true;
                }else {
                    MontLog.i(TAG,"OpenSSLRSAPublicKey!=");
                    isSame = false;
                }
            }else{
                MontLog.i(TAG,"other!=");
                isSame = false;
            }
        }
        return isSame;
    }

    public static String[] parseSignature(byte[] signature) {
        String array[] = new String[4];
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
            String pubKey = cert.getPublicKey().toString();
            String signNumber = cert.getSerialNumber().toString();
            MontLog.e(TAG,cert.getSigAlgName());
            MontLog.e(TAG,pubKey);
            MontLog.e(TAG,signNumber);
            MontLog.e(TAG,cert.getSubjectDN().toString());
//            System.out.println("signName:" + cert.getSigAlgName());
//            System.out.println("pubKey:" + pubKey);
//            System.out.println("signNumber:" + signNumber);
//            System.out.println("subjectDN:"+cert.getSubjectDN().toString());
            array[0] = cert.getSigAlgName();
            array[1] = pubKey;
            array[2] = signNumber;
            array[3] = cert.getSubjectDN().toString();
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            return array;
        }
    }

}
