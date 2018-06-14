package com.demo.lixuan.mydemo.widgt.cludeCircleImage;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Vector;


/**
 * 类 名: KeywordsFlow
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2017/7/17
 * author lixuan
 */

public class KeywordsFlow extends FrameLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "KeywordsFlow";
    public static final int IDX_X = 0;//X轴起点坐标KEY
    public static final int IDX_Y = 1;//Y轴起点坐标key
    public static final int IDX_TXT_LENGTH = 2;//控件宽度
    public static final int IDX_DIS_Y = 3;
    /** 由外至内的动画。 */
    public static final int ANIMATION_IN = 1;
    /** 由内至外的动画。 */
    public static final int ANIMATION_OUT = 2;
    /** 位移动画类型：从外围移动到坐标点。 */
    public static final int OUTSIDE_TO_LOCATION = 1;
    /** 位移动画类型：从坐标点移动到外围。 */
    public static final int LOCATION_TO_OUTSIDE = 2;
    /** 位移动画类型：从中心点移动到坐标点。 */
    public static final int CENTER_TO_LOCATION = 3;
    /** 位移动画类型：从坐标点移动到中心点。 */
    public static final int LOCATION_TO_CENTER = 4;
    public static final long ANIM_DURATION = 800l;
//    public static final int MAX = 12;
    public static final int MAX = 8;
    public static final int TEXT_SIZE_MAX = 20;
    public static final int TEXT_SIZE_MIN = 10;
    private OnClickListener itemClickListener;
    private static Interpolator interpolator;
    private static AlphaAnimation animAlpha2Opaque;
    private static AlphaAnimation animAlpha2Transparent;
    private static ScaleAnimation animScaleLarge2Normal, animScaleNormal2Large,
            animScaleZero2Normal, animScaleNormal2Zero;
    /** 存储显示的关键字。 */
    private Vector<String> vecKeywords;//要显示的文字
    private int width, height;
    /**
     * go2Show()中被赋值为true，标识开发人员触发其开始动画显示。<br/>
     * 本标识的作用是防止在填充keywrods未完成的过程中获取到width和height后提前启动动画。<br/>
     * 在show()方法中其被赋值为false。<br/>
     * 真正能够动画显示的另一必要条件：width 和 height不为0。<br/>
     */
    private boolean enableShow;
    private Random random;

    private int txtAnimInType, txtAnimOutType;
    /** 最近一次启动动画显示的时间。 */
    private long lastStartAnimationTime;
    /** 动画运行时间。 */
    private long animDuration;
    private Context mContext;
    private ArrayList<int[]> mLatList;//坐标集合
    private List<String> mImagePathList;


    public KeywordsFlow(Context context) {
        super(context);
        mContext=context;
        init();
    }

    public KeywordsFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        init();
    }

    public KeywordsFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext=context;
        init();
    }

    private void init() {
        lastStartAnimationTime = 0l;
        animDuration = ANIM_DURATION;
        random = new Random();
        vecKeywords = new Vector<String>(MAX);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
        interpolator = AnimationUtils.loadInterpolator(getContext(),
                android.R.anim.decelerate_interpolator);
        animAlpha2Opaque = new AlphaAnimation(0.0f, 1.0f);
        animAlpha2Transparent = new AlphaAnimation(1.0f, 0.0f);
        animScaleLarge2Normal = new ScaleAnimation(2, 1, 2, 1);
        animScaleNormal2Large = new ScaleAnimation(1, 2, 1, 2);
        animScaleZero2Normal = new ScaleAnimation(0, 1, 0, 1);
        animScaleNormal2Zero = new ScaleAnimation(1, 0, 1, 0);
    }

    public long getDuration() {
        return animDuration;
    }

    public void setDuration(long duration) {
        animDuration = duration;
    }

    /**添加显示的文字
     * @param keyword
     * @return
     */
    public boolean feedKeyword(String keyword) {
        boolean result = false;
        if (vecKeywords.size() < MAX) {
            result = vecKeywords.add(keyword);
        }
        return result;
    }

    /**添加显示的文字和图片
     * @param keyword
     * @return
     */
    public boolean feedKeyword(String keyword, String path) {
        boolean result = false;
        if (vecKeywords.size() < MAX) {
            result = vecKeywords.add(keyword);
            mImagePathList.add(path);
        }

        return result;
    }


    /**
     * 开始动画显示。<br/>
     * 之前已经存在的TextView将会显示退出动画。<br/>
     *
     * @return 正常显示动画返回true；反之为false。返回false原因如下：<br/>
     *         1.时间上不允许，受lastStartAnimationTime的制约；<br/>
     *         2.未获取到width和height的值。<br/>
     */
    public boolean go2Show(int animType) {
        if (System.currentTimeMillis() - lastStartAnimationTime > animDuration) {
            enableShow = true;
            if (animType == ANIMATION_IN) {
                txtAnimInType = OUTSIDE_TO_LOCATION;
                txtAnimOutType = LOCATION_TO_CENTER;
            } else if (animType == ANIMATION_OUT) {
                txtAnimInType = CENTER_TO_LOCATION;
                txtAnimOutType = LOCATION_TO_OUTSIDE;
            }
            disapper();
            boolean result = show();
            return result;
        }
        return false;
    }

    private void disapper() {
        int size = getChildCount();
        for (int i = size - 1; i >= 0; i--) {
            final CircleTargetView txv = (CircleTargetView) getChildAt(i);
            if (txv.getVisibility() == View.GONE) {//移除不显示控件
                removeView(txv);
                continue;
            }
            FrameLayout.LayoutParams layParams = (LayoutParams) txv
                    .getLayoutParams();
            int[] xy = new int[] { layParams.leftMargin, layParams.topMargin,
                    txv.getWidth() };
            AnimationSet animSet = getAnimationSet(xy, (width >> 1),
                    (height >> 1), txtAnimOutType);
            txv.startAnimation(animSet);
            animSet.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    txv.setOnClickListener(null);
                    txv.setClickable(false);
                    txv.setVisibility(View.GONE);
                }
            });
        }
    }

    private boolean show() {
        if (width > 0 && height > 0 && vecKeywords != null
                && vecKeywords.size() > 0 && enableShow) {
            enableShow = false;
            lastStartAnimationTime = System.currentTimeMillis();
            int xCenter = width >> 1, yCenter = height >> 1;//宽高除以2，获得布局中心点坐标
//            int size = vecKeywords.size();//获要展示的元素数量
//            int xItem = width / size, yItem = height / size;//把布局划分成单元格子
            int cell=1;
            for (int i = 0; i <vecKeywords.size() ; i++) {
                if (vecKeywords.size()>>i==0){
                    cell=i+1;
                    break;
                }
            }

            int xItem = width / cell, yItem = height / cell;//把布局划分成单元格子
            LinkedList<Integer> listX = new LinkedList<Integer>(), listY = new LinkedList<Integer>();
            for (int i = 0; i < cell; i++) {
                // 准备随机候选数，分别对应x/y轴位置
                //这里筛选出来的x，y值对应控件的margrinX，和marginY值，所以x，y可以是0
                listX.add(i * xItem);//记录x轴上每个单元格子，起点的坐标
                listY.add(i * yItem );//记录y轴上每个单元格子，起点的坐标
//                listY.add(i * yItem + (yItem >> 2));//记录y轴上每个单元格子，起点的坐标
            }
            mLatList =generateLat(listX,listY);
            LinkedList<CircleTargetView> listTxtTop = new LinkedList<CircleTargetView>();
            LinkedList<CircleTargetView> listTxtBottom = new LinkedList<CircleTargetView>();
            for (int i = 0; i < vecKeywords.size(); i++) {
                String keyword = vecKeywords.get(i);

                // 随机位置，糙值
                int xy[] = randomXY(random, listX, listY, xItem);//随机生成一个坐标，同时把生成的坐标元素从原来的集合中移除，避免重复。
                // 实例化TextView
                final CircleTargetView txv = new CircleTargetView(mContext);
                if (mImagePathList!=null){
                    String imagePath=mImagePathList.get(i);
                    txv.setHeadIcon(imagePath);
                }
//                txv.setBackgroundResource(R.drawable.shape_btn_blue_normal);
                txv.setGravity(Gravity.CENTER);
                txv.setOnClickListener(itemClickListener);
                txv.setText(keyword);//设置控件显示的文字
                txv.setTextColor(Color.BLACK);
                int padding = random.nextInt(xItem / 8);
                txv.setPadding(0, 0, 0, 0);
                txv.setSingleLine(true);
                int r = random.nextInt(256);
                int g= random.nextInt(256);
                int b = random.nextInt(256);
                int weith = random.nextInt(xItem-xItem/5) + xItem/5;
                txv.setCircleImageWithAndHeight(weith,weith);
                //设置控件的宽高不超过格子的大小
                RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(xItem,yItem);
                params.width=xItem;
                params.height=yItem;
                txv.setLayoutParams(params);
                int mColor = Color.rgb(r, g, b);
                //背景要shape类型才能用这一句
//                GradientDrawable myGrad = (GradientDrawable)txv.getBackground();
//                myGrad.setColor(mColor);
//              txv.setBackgroundColor(mColor);
                // 获取文本长度
//                Paint paint = txv.getPaint();
//                int strWidth = (int) Math.ceil(paint.measureText(keyword));
//                int strWidth = (int) Math.ceil(txv.getHeight());
                int strWidth = 60;

//                ======================================修正坐标=============================
//                xy[IDX_TXT_LENGTH] = strWidth;
//                // 第一次修正:修正x坐标
//                if (xy[IDX_X] + strWidth > width - (xItem >> 1)) {//起点位置距离最右边界不到格子宽度一半
//                    int baseX = width - strWidth;
//                    // 减少文本右边缘一样的概率
//                    xy[IDX_X] = baseX - xItem + random.nextInt(xItem >> 1);
//                } else if (xy[IDX_X] == 0) {
//                    // 减少文本左边缘一样的概率
//                    xy[IDX_X] = Math.max(random.nextInt(xItem), xItem / 3);
//                }
//                xy[IDX_DIS_Y] = Math.abs(xy[IDX_Y] - yCenter);
                //                ======================================修正坐标=============================
                txv.setTag(xy);
                if (xy[IDX_Y] > yCenter) {//y轴起点位于竖直中线以下
                    listTxtBottom.add(txv);
                } else {
                    listTxtTop.add(txv);//y轴起点位于竖直中线以上
                }
                Log.d(TAG, "show: getwith:getMeasuredWidth " + txv.getWidth() + " : " + txv.getHeight() + " : " +txv.getMeasuredWidth() + " : " + txv.getMeasuredWidth());
            }

            attach2Screen(listTxtTop, xCenter, yCenter, yItem);
            attach2Screen(listTxtBottom, xCenter, yCenter, yItem);
            return true;//显示
        }
        return false;//不显示
    }

    private ArrayList<int[]> generateLat(LinkedList<Integer> listX, LinkedList<Integer> listY) {
        ArrayList<int[]> list=new ArrayList();
        for (int i = 0; i < listX.size()-1; i++) {
            for (int i1 = 0; i1 < listY.size()-1; i1++) {
                int[] lat =new int[]{listX.get(i),listY.get(i1)};
                list.add(lat);
            }
        }
        return list;
    }

    /** 修正TextView的Y坐标将将其添加到容器上。
     * @param listTxt   对象坐标点集合
     * @param xCenter 中心x轴坐标
     * @param yCenter 中心y轴坐标
     * @param yItem     单元格子宽度
     *
     * */
    private void attach2Screen(LinkedList<CircleTargetView> listTxt, int xCenter,
                               int yCenter, int yItem) {
        int size = listTxt.size();
        sortXYList(listTxt, size);//根据与中心点的距离由近到远进行冒泡排序。
        for (int i = 0; i < size; i++) {
            CircleTargetView txv = listTxt.get(i);
            int[] iXY = (int[]) txv.getTag();
            // 第二次修正:修正y坐标
            int yDistance = iXY[IDX_Y] - yCenter;//y轴方向上距离中线距离
            // 对于最靠近中心点的，其值不会大于yItem<br/>
            // 对于可以一路下降到中心点的，则该值也是其应调整的大小<br/>
//            int yMove = Math.abs(yDistance);
//            inner: for (int k = i - 1; k >= 0; k--) {
//                int[] kXY = (int[]) listTxt.get(k).getTag();
//                int startX = kXY[IDX_X];//控件x轴起点
//                int endX = startX + kXY[IDX_TXT_LENGTH];//控件X轴终点
//                // y轴以中心点为分隔线，在同一侧
//                if (yDistance * (kXY[IDX_Y] - yCenter) > 0) {//Kq
//                    if (isXMixed(startX, endX, iXY[IDX_X], iXY[IDX_X] + iXY[IDX_TXT_LENGTH])) {//A线段与B线段所代表的直线在X轴映射上是否有交集。返回true表示有交集
//                        int tmpMove = Math.abs(iXY[IDX_Y] - kXY[IDX_Y]);//和前一个元素在y轴上的距离
//                        if (tmpMove > yItem) {
//                            yMove = tmpMove;
//                        } else if (yMove > 0) {
//                            // 取消默认值。
//                            yMove = 0;
//                        }
//                        break inner;
//                    }
//                }
//            }
//            if (yMove > yItem) {//当i元素与i-1元素的距离大于单元格子的宽度时候，调整距离
//                int maxMove = yMove - yItem;
//                int randomMove = random.nextInt(maxMove);
//                int realMove = Math.max(randomMove, maxMove >> 1) * yDistance
//                        / Math.abs(yDistance);
//                iXY[IDX_Y] = iXY[IDX_Y] - realMove;
//                iXY[IDX_DIS_Y] = Math.abs(iXY[IDX_Y] - yCenter);
//                // 已经调整过前i个需要再次排序
//                sortXYList(listTxt, i + 1);
//            }
            FrameLayout.LayoutParams layParams = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
            layParams.gravity = Gravity.LEFT | Gravity.TOP;
            layParams.leftMargin = iXY[IDX_X];
            layParams.topMargin = iXY[IDX_Y];
            Log.d(TAG, "attach2Screen: getwith:getMeasuredWidth " + txv.getWidth() + " : " + txv.getHeight() + " : " +txv.getMeasuredWidth() + " : " + txv.getMeasuredWidth());
            addView(txv, layParams);
            // 动画
            AnimationSet animSet = getAnimationSet(iXY, xCenter, yCenter,
                    txtAnimInType);
            txv.startAnimation(animSet);
        }
    }

    public AnimationSet getAnimationSet(int[] xy, int xCenter, int yCenter,
                                        int type) {
        AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(interpolator);
        if (type == OUTSIDE_TO_LOCATION) {
            animSet.addAnimation(animAlpha2Opaque);
            animSet.addAnimation(animScaleLarge2Normal);
            TranslateAnimation translate = new TranslateAnimation((xy[IDX_X]
                    + (xy[IDX_TXT_LENGTH] >> 1) - xCenter) << 1, 0,
                    (xy[IDX_Y] - yCenter) << 1, 0);
            animSet.addAnimation(translate);
        } else if (type == LOCATION_TO_OUTSIDE) {
            animSet.addAnimation(animAlpha2Transparent);
            animSet.addAnimation(animScaleNormal2Large);
            TranslateAnimation translate = new TranslateAnimation(0, (xy[IDX_X]
                    + (xy[IDX_TXT_LENGTH] >> 1) - xCenter) << 1, 0,
                    (xy[IDX_Y] - yCenter) << 1);
            animSet.addAnimation(translate);
        } else if (type == LOCATION_TO_CENTER) {
            animSet.addAnimation(animAlpha2Transparent);
            animSet.addAnimation(animScaleNormal2Zero);
            TranslateAnimation translate = new TranslateAnimation(0,
                    (-xy[IDX_X] + xCenter), 0, (-xy[IDX_Y] + yCenter));
            animSet.addAnimation(translate);
        } else if (type == CENTER_TO_LOCATION) {
            animSet.addAnimation(animAlpha2Opaque);
            animSet.addAnimation(animScaleZero2Normal);
            TranslateAnimation translate = new TranslateAnimation(
                    (-xy[IDX_X] + xCenter), 0, (-xy[IDX_Y] + yCenter), 0);
            animSet.addAnimation(translate);
        }
        animSet.setDuration(animDuration);
        return animSet;
    }

    /**
     * 根据与中心点的距离由近到远进行冒泡排序。
     *
     * @param endIdx
     *            起始位置。
     * @param listTxt
     *            待排序的数组。
     *
     */
    private void sortXYList(LinkedList<CircleTargetView> listTxt, int endIdx) {
        for (int i = 0; i < endIdx; i++) {
            for (int k = i + 1; k < endIdx; k++) {
                if (((int[]) listTxt.get(k).getTag())[IDX_DIS_Y] < ((int[]) listTxt
                        .get(i).getTag())[IDX_DIS_Y]) {
                    CircleTargetView iTmp = listTxt.get(i);
                    CircleTargetView kTmp = listTxt.get(k);
                    listTxt.set(i, kTmp);
                    listTxt.set(k, iTmp);
                }
            }
        }
    }

    /** A线段与B线段所代表的直线在X轴映射上是否有交集。 */
    private boolean isXMixed(int startA, int endA, int startB, int endB) {
        boolean result = false;
        if (startB >= startA && startB <= endA) {
            result = true;
        } else if (endB >= startA && endB <= endA) {
            result = true;
        } else if (startA >= startB && startA <= endB) {
            result = true;
        } else if (endA >= startB && endA <= endB) {
            result = true;
        }
        return result;
    }

    /**
     * @param ran 随机数生成器
     * @param listX x轴上，划分的每个格子的起点坐标
     * @param listY y轴上，划分的每个格子的起点坐标
     * @param xItem x轴上，每个格子的宽度
     * @return
     */
    //得到随机坐标
    private int[] randomXY(Random ran, LinkedList<Integer> listX,
                           LinkedList<Integer> listY, int xItem) {
        int[] arr = new int[4];
        int[] lat=mLatList.remove(ran.nextInt(mLatList.size()));
        arr[IDX_X] = lat[0];//随机抽取一个x轴 起点
        arr[IDX_Y] = lat[1];//随机抽取一个y轴 起点

//         arr[IDX_X] = listX.remove(ran.nextInt(listX.size()));//随机抽取一个x轴 起点
//        arr[IDX_Y] = listY.remove(ran.nextInt(listY.size()));//随机抽取一个y轴 起点
        Log.d(TAG, "randomXY:   arr[IDX_X] :" +  arr[IDX_X]);
        Log.d(TAG, "randomXY:    arr[IDX_Y] :" +   arr[IDX_Y]);
        return arr;
    }

    /**获取整个布局的宽高
     *
     */
    public void onGlobalLayout() {
        int tmpW = getWidth();
        int tmpH = getHeight();
        if (width != tmpW || height != tmpH) {
            width = tmpW;
            height = tmpH;
            show();
        }
    }

    public Vector<String> getKeywords() {
        return vecKeywords;
    }

    public void rubKeywords() {
        vecKeywords.clear();
    }

    /** 直接清除所有的TextView。在清除之前不会显示动画。 */
    public void rubAllViews() {
        removeAllViews();
    }

    public void setOnItemClickListener(OnClickListener listener) {
        itemClickListener = listener;
    }

}
