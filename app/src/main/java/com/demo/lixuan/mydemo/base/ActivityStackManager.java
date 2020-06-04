package com.demo.lixuan.mydemo.base;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;

/**
 * @author lihong
 * @since 2016/10/28
 */
public final class ActivityStackManager {
    /**
     * TAG
     */
    private static final String TAG = "ActivityStackManager";


    /**
     * 窗口栈。
     */
    private static LinkedList<Activity> mActivityStack = new LinkedList<>();

    /**
     * 得到指定activity前一个activity的实例
     *
     * @param curActivity 当前activity
     * @return 可能为null
     */
    public static Activity getPreviousActivity(Activity curActivity) {
        final LinkedList<Activity> activities = mActivityStack;
        Activity preActivity = null;
        for (int cur = activities.size() - 1; cur >= 0; cur--) {
            Activity activity = activities.get(cur);
            if (activity == curActivity) {
                int pre = cur - 1;
                if (pre >= 0) {
                    preActivity = activities.get(pre);
                }
            }
        }

        return preActivity;
    }

    /**
     * 从栈管理队列中移除该Activity。
     *
     * @param activity Activity。
     */
    public static synchronized void removeFromStack(Activity activity) {
        mActivityStack.remove(activity);

        printActivityStack();
    }

    /**
     * 将Activity加入栈管理队列中。
     *
     * @param activity Activity。
     */
    public static synchronized void addToStack(Activity activity) {
        // 移到顶端。
        mActivityStack.remove(activity);
        mActivityStack.add(activity);

        printActivityStack();
    }

    /**
     * 清除栈队列中的所有Activity。
     */
    public static synchronized void clearTask() {
        int size = mActivityStack.size();
        if (size > 0) {
            Activity[] activities = new Activity[size];
            mActivityStack.toArray(activities);

            for (Activity activity : activities) {
                activity.finish();
            }
        }
    }

    /**
     * 清除栈队列中其他的所有Activity。
     */
    public static synchronized void clearOtherTask() {
        int size = mActivityStack.size();
        if (size > 0) {
            Activity[] activities = new Activity[size];
            mActivityStack.toArray(activities);

            for (int i = size - 2; i >= 0; --i) {
                Activity activity = mActivityStack.get(i);
                activity.finish();
            }
        }
    }

    /**
     * 获得Activity栈
     */
    public static synchronized LinkedList<Activity> getActivityStackList() {
        return mActivityStack;
    }

    /**
     * 获得Activity栈
     */
    public static synchronized Activity[] getActivityStack() {
        Activity[] activities = new Activity[mActivityStack.size()];
        return mActivityStack.toArray(activities);
    }

    /**
     * 打印出当前activity stack的信息
     */
    private static void printActivityStack() {
        int size = mActivityStack.size();
        if (size > 0) {
            Log.d(TAG, "Activity stack begin ======== ");
            Log.d(TAG, "    The activity stack: ");
            for (int i = size - 1; i >= 0; --i) {
                Activity activity = mActivityStack.get(i);
                Log.i(TAG, "    Activity" + (i + 1) + " = " + activity.getClass().getSimpleName());
            }
            Log.d(TAG, "Activity stack end ========== ");
        }
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public static void finishLastActivity() {
        if (mActivityStack == null || mActivityStack.size() == 0) {
            return;
        }
        Activity activity = mActivityStack.getLast();
        finishActivity(activity);
    }

    /**
     * 多次结束Activity
     */
    public static void finishActivity(int count) {
        for (int i = 0; i < count; i++){
            finishLastActivity();
        }
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity) {
        if (activity != null) {
            mActivityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定的Activity在栈中的相同实例
     */
    public static void finishActivity2(Activity activity) {
        if (activity != null) {
            int count = 0;
            for (int i = mActivityStack.size() - 1; i >= 0; --i) {
                Activity a = mActivityStack.get(i);
                if(activity.getClass().getSimpleName().equals(a.getClass().getSimpleName())){
                    count = count + 1;
                }
            }
            finishActivity(count);
            activity = null;
        }
    }
}
