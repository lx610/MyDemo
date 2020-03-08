package com.demo.lixuan.mydemo.Utils;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.NotificationManagerCompat;

import com.demo.lixuan.mydemo.MainActivity;
import com.demo.lixuan.mydemo.base.BaseApplication;


/**
 * className: NotificationManagerUtil
 * description:通知栏
 * author：yehexing
 * email：418198972@163.com
 * date: 2018/6/13 8:37
 */

public class NotificationManagerUtil {

    private static NotificationManagerUtil instance = null;
    private NotificationManager mNotificationManager;
    private Notification.Builder mBuilder;
    private PendingIntent intent;
    private final int pushId = 1;

    public NotificationManagerUtil() {
        mNotificationManager = (NotificationManager) BaseApplication.getContext().getSystemService(BaseApplication.getContext().NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(BaseApplication.getContext());
        Intent notificationIntent = new Intent(BaseApplication.getContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent = PendingIntent.getActivity(BaseApplication.getContext(), 0, notificationIntent, 0);
    }

    public static NotificationManagerUtil getInstance() {
        if (instance == null)
            instance = new NotificationManagerUtil();
        return instance;
    }

    public void CustomNotify(String title, String content, String ticker) {
        mBuilder.setContentTitle(title)//设置通知栏标题
                .setContentText(content)
                .setContentIntent(intent) //设置通知栏点击意图
//                .setNumber(++pushNum) //设置通知集合的数量
                .setTicker(ticker) //通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis());//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//                .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                .setSmallIcon(R.drawable.logo_im);//设置通知小ICON

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            String CHANNEL_ONE_ID = "com.montnets.noticeking";
            String CHANNEL_ONE_NAME = "Channel One";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                    CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
            /*notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);*/
            NotificationManager nm = BaseApplication.getContext().getSystemService(NotificationManager.class);
            nm.createNotificationChannel(notificationChannel);
            mBuilder.setChannelId(CHANNEL_ONE_ID);

        }

        Notification notify = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notify = mBuilder.build();
        }
        notify.flags |= Notification.FLAG_AUTO_CANCEL;

//        if(preference.getBooleanData(GlobalConstant.Config.TRUN_SOUND,true) &&
//                preference.getBooleanData(GlobalConstant.Config.TRUN_VIBRATION,true)){
//            notify.defaults = Notification.DEFAULT_ALL;
//        }else{
//            if(preference.getBooleanData(GlobalConstant.Config.TRUN_SOUND,true)){
//                notify.defaults |= Notification.DEFAULT_SOUND;
//            }else if(preference.getBooleanData(GlobalConstant.Config.TRUN_VIBRATION,true)){
//                notify.defaults |= Notification.DEFAULT_VIBRATE;
//            }else{
//
//            }
//        }

        NotificationManagerCompat new_nm = NotificationManagerCompat.from(BaseApplication.getContext());
        new_nm.notify(pushId, notify);
    }

    public void reset() {
        mNotificationManager = (NotificationManager) BaseApplication.getContext().getSystemService(BaseApplication.getContext().NOTIFICATION_SERVICE);
        mNotificationManager.cancel(pushId);
    }

    public boolean isPermissionOpen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return NotificationManagerCompat.from(BaseApplication.getContext()).getImportance() != NotificationManager.IMPORTANCE_NONE;
        }
        return NotificationManagerCompat.from(BaseApplication.getContext()).areNotificationsEnabled();
    }

    public void openPermissionSetting() {
        try {
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //直接跳转到应用通知设置的代码：
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                localIntent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                localIntent.putExtra(Settings.EXTRA_APP_PACKAGE, BaseApplication.getContext().getPackageName());
                BaseApplication.getContext().startActivity(localIntent);
                return;

            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                localIntent.putExtra("app_package", BaseApplication.getContext().getPackageName());
                localIntent.putExtra("app_uid", BaseApplication.getContext().getApplicationInfo().uid);
                BaseApplication.getContext().startActivity(localIntent);
                return;

            }

            if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {

                localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                localIntent.addCategory(Intent.CATEGORY_DEFAULT);
                localIntent.setData(Uri.parse("package:" + BaseApplication.getContext().getPackageName()));
                BaseApplication.getContext().startActivity(localIntent);
                return;

            }

            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,
            if (Build.VERSION.SDK_INT >= 9) {

                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", BaseApplication.getContext().getPackageName(), null));
                BaseApplication.getContext().startActivity(localIntent);
                return;

            }

            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", BaseApplication.getContext().getPackageName());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
