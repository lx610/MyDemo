package com.demo.lixuan.mydemo.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.math.BigDecimal;


/**
 * author: wgz
 * email:wanggaozhuo@yeah.net
 * date: 2017/7/21 14:04
 */

public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * 递归删除 文件/文件夹
     */
    public static void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

    public static File getReturnFile(Context ctx, Intent data) {
        File file = null;
        String filePath = getReturnFilePath(ctx, data);
        if (filePath != null) {
            file = new File(filePath);
        }
        return file;
    }

    /**
     * 调用系统数据库拿到 图片地址
     *
     * @param ctx
     * @param data
     * @return
     */
    public static String getReturnFilePath(Context ctx, Intent data) {
        String filePath = null;
        if (data != null) {

            Log.i(TAG, "[getReturnFilePath] data:" + data.toString());

            ContentResolver resolver = ctx.getContentResolver();
            Uri outputFileUri = data.getData();
            if (outputFileUri == null)
                return null;

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = resolver.query(outputFileUri, filePathColumn, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                filePath = cursor.getString(columnIndex);
                if (TextUtils.isEmpty(filePath) && columnIndex - 1 > 0) {
                    filePath = cursor.getString(columnIndex - 1);
                }

                Log.i(TAG, "filePath:" + filePath);
                cursor.close();
            } else {
                Uri uri = data.getData();
                String type = data.getType();
                if (uri != null && type != null) {
                    if (uri.getScheme().equals("file") && (type.contains("image/"))) {
                        String path = uri.getEncodedPath();
                        if (path != null) {
                            path = Uri.decode(path);
                            Log.i(TAG, "filePath_2:" + path);
                            return path;
                        }
                    }
                }
            }
        }

        return filePath;
    }


    public static Bitmap getLocalBitmap(final String path) {

        if (!new File(path).exists()) {
            return null;
        }

        try {
            //先将所选图片转化为流的形式，path所得到的图片路径
            FileInputStream is = new FileInputStream(path);
            double fileLength = is.available();
            int size = 1;
            if (fileLength / 1024 / 1024 > 2) {
                size = 10;
            } else if (fileLength / 1024 / 1024 > 1) {
                size = 2;
            } else {
                size = 1;
            }
            //定义一个file，为压缩后的图片
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = size;
            //将图片缩小为原来的  1/size ,不然图片很大时会报内存溢出错误
            Bitmap image = BitmapFactory.decodeStream(is, null, options);
            is.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//这里100表示不压缩，将不压缩的数据存放到baos中
            int per = 100;
            while (baos.toByteArray().length / 1024 > 300) {
                // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
                baos.reset();// 重置baos即清空baos
                image.compress(Bitmap.CompressFormat.JPEG, per, baos);// 将图片压缩为原来的(100-per)%，把压缩后的数据存放到baos中
                per -= 10;// 每次都减少10
            }
            baos.close();
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 创建文件目录
     */
    public static boolean createDir(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        boolean ret = false;
        File file = new File(path);


        if (!file.exists() || !file.isDirectory()) {
            try {
                ret = file.mkdirs();
            } catch (Exception se) {
                se.printStackTrace();
            }
        } else {
            ret = true;
        }
        return ret;
    }

    public static boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static boolean IOOPeration(File f1, File f2) {
        FileInputStream File1 = null;
        FileInputStream File2 = null;
        try {
            File1 = new FileInputStream(f1);
            File2 = new FileInputStream(f2);
            try {

                if (File1.available() != File2.available()) {
                    //长度不同内容肯定不同
                    return false;
//                    System.out.println(args[0] + " is not equal to " + args[1]);
                } else {
                    boolean tag = true;
                    while (File1.read() != -1 && File2.read() != -1) {
                        if (File1.read() != File2.read()) {
                            tag = false;
                            break;
                        }
                    }
                    if (tag == true)
                        return true;
//                        System.out.println(args[0] + " equals to " + args[1]);
                    else
                        return false;
//                        System.out.println(args[0] + " is not equal to " + args[1]);
                }
            } catch (IOException e) {
//                System.out.println(e);
                return false;
            }
        } catch (FileNotFoundException e) {
//            System.out.println("File can't find..");
            return false;
        } finally {
            try {
                if (File1 != null)
                    File1.close();
                if (File2 != null)
                    File2.close();
            } catch (IOException e) {
//                System.out.println(e);
                return false;
            }
        }
    }

    public static boolean Compare(File sdCard, InputStream is) {
        InputStream file1 = null;
        try {
            file1 = new FileInputStream(sdCard);

            if (file1.available() != is.available()) {
                //长度不同内容肯定不同
//                MontLog.e(TAG,"The two files are different!");
                return false;
            } else {
                boolean tag = true;
                while (file1.read() != -1 && is.read() != -1) {
                    if (file1.read() != is.read()) {
                        tag = false;
                        break;
                    }
                }
                if (tag == true) {
//                    MontLog.e(TAG,"The two files are the same!");
                    return true;
                } else {
//                    MontLog.e(TAG,"The two files are different!");
                    return false;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (file1 != null)
                    file1.close();
            } catch (IOException e) {
                return false;
            }
        }
    }

    /**
     * 追加文件：使用FileWriter
     *
     * @param fileName
     * @param content
     */
    public static void FileWriterAdd(String fileName, String content) {
//        fileName = GlobalConstant.Config.DirPath + "log/log.txt";
        FileWriter writer = null;
        try {
            File file = new File(fileName);
            if (!file.exists())
                if (file.createNewFile()) {
                    System.out.println("Create file successed");
                }
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(fileName, true);
            writer.write(content + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writerOutLoginLog(String result) {
//        FileUtil.FileWriterAdd(GlobalConstant.Config.LogPath + "/LogloginOut.txt", DateUtil.getDateStr(System.currentTimeMillis()) + "\r\n" + result);
    }

    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param fileName 文件名
     * @param content  追加的内容
     */
    public static void RandomAccessFileAdd(String fileName, String content) {
        RandomAccessFile randomFile = null;
        try {
            File file = new File(fileName);
            if (!file.exists())
                if (file.createNewFile()) {
                    System.out.println("Create file successed");
                }
            // 打开一个随机访问文件流，按读写方式
            randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void BufferedWriterAdd(String fileName, String conent) {
        BufferedWriter out = null;
        try {
            File file = new File(fileName);
            if (!file.exists())
                if (file.createNewFile()) {
                    System.out.println("Create file successed");
                }
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
            out.write(conent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }

    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return "";

        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.length());
        return fileName;
    }

    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    public static boolean copyFile(String srcFilePath, String desFilePath) {

        if (TextUtils.isEmpty(srcFilePath))
            return false;

        if (TextUtils.isEmpty(desFilePath))
            return false;

        if(fileIsExists(desFilePath))
            new File(desFilePath).delete();

        try {
            InputStream is = new FileInputStream(srcFilePath);
            OutputStream os = new FileOutputStream(desFilePath);
            int byteCount;
            byte[] bytes = new byte[1024];
            while ((byteCount = is.read(bytes)) != -1) {
                os.write(bytes, 0, byteCount);
            }
            os.close();
            is.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件夹大小(递归)
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {

        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 获取当前文件夹大小，不递归子文件夹
     *
     * @param file
     * @return
     */
    public static long getCurrentFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    //跳过子文件夹

                } else {
                    size = size + fileList[i].length();

                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static boolean deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    /**
     * 删除指定目录下文件
     *
     * @param filePath
     * @return
     */
    public static void deleteFile(String filePath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                File[] fileList = file.listFiles();
                for (int i = 0; i < fileList.length; i++) {
                    if (!fileList[i].isDirectory()) {
                        fileList[i].delete();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
//        if (kiloByte < 1) {
//            return size + "Byte(s)";
//        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    /**
     * 下载文件配置
     */
    public static void initFileDir() {
//        FileUtil.createDir(GlobalConstant.Config.FilePath);
//        FileUtil.createDir(GlobalConstant.Config.ImagePath);
//        FileUtil.createDir(GlobalConstant.Config.SkinPath);
//        FileUtil.createDir(GlobalConstant.Config.MscPath);
//        FileUtil.createDir(GlobalConstant.Config.LogPath);
//        FileUtil.createDir(GlobalConstant.Config.IMSDKLOGS);
//        FileUtil.createDir(GlobalConstant.Config.CEASHSLOGS);
//        FileUtil.createDir(GlobalConstant.Config.DICM);
    }
}
