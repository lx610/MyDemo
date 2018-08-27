/*
 * The MIT License (MIT)
 * Copyright (c) [2015] [chiemy]
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.demo.lixuan.mydemo.widgt.cardPageView;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

//import com.nineoldandroids.animation.Animator;
//import com.nineoldandroids.animation.AnimatorListenerAdapter;
//import com.nineoldandroids.view.ViewHelper;
//import com.nineoldandroids.view.ViewPropertyAnimator;

/**
 * @author chiemy
 * 
 */
public class CardPageViewLeftMove extends FrameLayout {
	private static final String TAG = "CardPageViewLeftMove";
	private static final int ITEM_SPACE = 40;
	private static final int DEF_MAX_VISIBLE = 4;
	public static final int FLAG_PALY_MODE_REPETE = 1;
	public static final int FLAG_PALY_MODE_SINGLE = 0;
	private int playMode = 0;


	private int mMaxVisible = DEF_MAX_VISIBLE;
	private int itemSpace = ITEM_SPACE;//默认的两个卡牌的	Y轴间距

	private float mTouchSlop;
	private BaseCardAdapter mListAdapter;
	private int mNextAdapterPosition =0;
	private SparseArray<View> viewHolder = new SparseArray<View>();
	private OnCardClickListener mListener;
	private int topPosition;
	private Rect topRect;

	public interface OnCardClickListener {
		void onCardClick(View view, int position);
	}

	public CardPageViewLeftMove(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CardPageViewLeftMove(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CardPageViewLeftMove(Context context) {
		super(context);
		init();
	}

	private void init() {
		topRect = new Rect();
		ViewConfiguration con = ViewConfiguration.get(getContext());
		mTouchSlop = con.getScaledTouchSlop();//设置最小滑动触发距离
	}

	public void setMaxVisibleCount(int count) {
		mMaxVisible = count;
	}

	public int getMaxVisibleCount() {
		return mMaxVisible;
	}

	public void setItemSpace(int itemSpace) {
		this.itemSpace = itemSpace;
	}

	public int getItemSpace() {
		return itemSpace;
	}

	public BaseCardAdapter getAdapter() {
		return mListAdapter;
	}

	public void setPlayMode(int modeFlag){
		 playMode = modeFlag;
	}

	public int getPlayMode() {
		return playMode;
	}

	public void setAdapter(BaseCardAdapter adapter) {
		if (mListAdapter != null) {
			mListAdapter.unregisterDataSetObserver(mDataSetObserver);
		}
		mListAdapter = adapter;
		adapter.registerDataSetObserver(mDataSetObserver);
		removeAllViews();
		refreshCardLayout();
	}

	/**
	 * @param position
	 */
	public void setCurrentPosition(int position){
		int itemCount = mListAdapter.getCount();
		if (position>itemCount){
			return;
		}
		if (playMode==FLAG_PALY_MODE_REPETE){
			mNextAdapterPosition=itemCount+position;
		}else {
			mNextAdapterPosition=position;
		}
		refreshCardLayout();
	}

	public void setOnCardClickListener(OnCardClickListener listener) {
		mListener = listener;
	}


	private void refreshCardLayout() {
		int itemCount = mListAdapter.getCount();
		while (mNextAdapterPosition%itemCount < itemCount
				&& getChildCount() < mMaxVisible) {
			int index = mNextAdapterPosition % mMaxVisible;
			final View view;
			if (playMode==FLAG_PALY_MODE_REPETE) {
				view = mListAdapter.getView(mNextAdapterPosition%itemCount,
						null, this);
			}else {
//				index = mNextAdapterPosition % mMaxVisible;
				View convertView = viewHolder.get(index);
				view = mListAdapter.getView(mNextAdapterPosition,
						convertView, this);
				view.setOnClickListener(null);
				viewHolder.put(index, view);
			}


			// 添加剩余的View时，始终处在最后
			index = Math.min(mNextAdapterPosition, mMaxVisible - 1);
			view.setScaleX(((mMaxVisible - index - 1) / (float) mMaxVisible) * 0.2f + 0.8f);
			int topMargin = (mMaxVisible - index - 1) * itemSpace;
			view.setTranslationY(topMargin);
//			view.setAlpha(mNextAdapterPosition == 0 ? 1 : 0.5f);

			LayoutParams params = (LayoutParams) view.getLayoutParams();
			if (params == null) {
				params = new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
			}
			addViewInLayout(view, 0, params);
			if (playMode==FLAG_PALY_MODE_SINGLE){
				++mNextAdapterPosition;
			}else {
				if (mNextAdapterPosition== mListAdapter.getCount()-1){
					//直接归零会，在第二次循环开始的view会比前面的小
					if (mListAdapter.getCount()>=mMaxVisible){
						mNextAdapterPosition = mListAdapter.getCount();
					}else {
						mNextAdapterPosition=mListAdapter.getCount()*mMaxVisible;
					}

				}else {
					++mNextAdapterPosition;
				}
			}
		}
		// requestLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int childCount = getChildCount();
		int maxHeight = 0;
		int maxWidth = 0;
		for (int i = 0; i < childCount; i++) {
			View child = getChildAt(i);
			this.measureChild(child, widthMeasureSpec, heightMeasureSpec);
			int height = child.getMeasuredHeight();
			int width = child.getMeasuredWidth();
			if (height > maxHeight) {
				maxHeight = height;
			}
			if (width > maxWidth) {
				maxWidth = width;
			}
		}
		int desireWidth = widthSize;
		int desireHeight = heightSize;
		if (widthMode == MeasureSpec.AT_MOST) {
			desireWidth = maxWidth + getPaddingLeft() + getPaddingRight();
		}
		if (heightMode == MeasureSpec.AT_MOST) {
			desireHeight = maxHeight + (mMaxVisible - 1) * itemSpace + getPaddingTop() + getPaddingBottom();
		}
		setMeasuredDimension(desireWidth, desireHeight);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		View topView = getChildAt(getChildCount() - 1);
		if (topView != null) {
			topView.setOnClickListener(listener);
		}
	}

	float downX, downY;


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final View topView = getChildAt(getChildCount() - 1);
		float currentY = event.getY();
		float currentX = event.getX();

		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			Log.d(TAG, "onTouchEvent: =================MotionEvent.ACTION_MOVE");
//			float distanceX =Math.abs(currentX - downX);
//			float moveX =downX - currentX;
//			float distanceY =Math.abs(currentY - downY);
//			if (distanceX>distanceY){//向左滑动
////					topView.setTranslationX(currentX - downX);
//				topView.scrollTo((int) moveX,0);
////					if (topView.getTranslationX()<topView.getWidth()/2||topView.getTranslationX()>topView.getWidth()/2){
//				float distence = Math.abs(topView.getTranslationX());
//				if (distanceX>topView.getWidth()/2){
//					Log.d(TAG, "MotionEvent.ACTION_MOVE distanceX>topView.getWidth()/2: distanceX:====" + distanceX);
//					if (turnPage(event)) {
//						downY = -1;
//					}
//					isGoBackPosition=false;
//				}
//			}
			isGoBackPosition=mListAdapter.moveTopView(topView,event,downX,downY);
			if (!isGoBackPosition){
				if (turnPage(event)) {
						downY = -1;
					}
			}
			break;

			case MotionEvent.ACTION_UP:
				Log.d(TAG, "onTouchEvent: =================MotionEvent.ACTION_UP");
//				reBackTopView(topView,event,downX,downY);
				mListAdapter.rebackToView(topView,event,downX,downY);
				break;
		}
		return super.onTouchEvent(event);
	}

	/**翻页
	 * 下移所有视图
	 * @param event
	 */
	private boolean turnPage(MotionEvent event) {
		final View topView = getChildAt(getChildCount() - 1);
		if(!topView.isEnabled()){
			return false;
		}
		// topView.getHitRect(topRect); 在4.3以前有bug，用以下方法代替
		topRect = getHitRect(topRect, topView);
		// 如果按下的位置不在顶部视图上，则不移动
		if (!topRect.contains((int) downX, (int) downY)) {
			return false;
		}
		if (playMode==FLAG_PALY_MODE_REPETE){
			topView.setEnabled(true);
		}else {
			topView.setEnabled(false);
		}

		//==========================把第一张卡牌移除动画 start ==========================

//		ValueAnimator translationYanimator = ValueAnimator.ofFloat(topView.getTranslationY(), topView.getTranslationY() + topView.getHeight());
//		translationYanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//   @Override
//   public void onAnimationUpdate(ValueAnimator animation) {
//				               Float value = (Float) animation.getAnimatedValue();
//	   							topView.setTranslationY(value);
//				           }
//         });
//		ValueAnimator translationYanimator;
//		float startPoitX = downX - event.getX();
//		if (event.getX()-downX>0){//往右边滑
////			translationYanimator = ValueAnimator.ofFloat(event.getX(), event.getX() + topView.getWidth());
//			translationYanimator = ValueAnimator.ofInt((int)startPoitX, (int)startPoitX - topView.getWidth());
//		}else {//往左边滑
////			translationYanimator = ValueAnimator.ofFloat(event.getX(), event.getX() - topView.getWidth());
//			translationYanimator = ValueAnimator.ofInt((int)startPoitX, (int)startPoitX +(int)topView.getWidth());
//		}

//		translationYanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//			@Override
//			public void onAnimationUpdate(ValueAnimator animation) {
//				Integer value = (Integer) animation.getAnimatedValue();
////				Float value = (Float) animation.getAnimatedValue();
//				topView.scrollTo(value,0);
//
////				topView.setTranslationX(value);
//			}
//		});
//		translationYanimator.start();
//		ValueAnimator alphaYanimator = ValueAnimator.ofFloat(1, 0);
//		alphaYanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//			@Override
//			public void onAnimationUpdate(ValueAnimator animation) {
//				Float value = (Float) animation.getAnimatedValue();
//				topView.setAlpha(value);
//			}
//		});
//		alphaYanimator.start();
//		AnimatorSet animatorSet =new AnimatorSet();
		AnimatorSet animatorSet = mListAdapter.getTurnPageAnimator(topView,event,downX,downY);
//		animatorSet.setDuration(300);
//		animatorSet.playTogether(translationYanimator);
		animatorSet.start();
		//==========================把第一张卡牌移除的动画 end ==========================

		animatorSet.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animator) {

			}

			@Override
			public void onAnimationEnd(Animator animator) {
				//==========================第一张卡牌移除动画结束后，把第一张卡牌从布局中移除 start ==========================

				topView.setEnabled(true);
				removeView(topView);//把第一张卡牌从布局中移除
				refreshCardLayout();
				final int count = getChildCount();
				for (int i = 0; i < count; i++) {
					final View view = getChildAt(i);

					float scaleX = view.getScaleX()
							+ ((float) 1 / mMaxVisible) * 0.2f;

//					float tranlateY = ViewHelper.getTranslationY(view)
					float tranlateY = view.getTranslationY()
							+ itemSpace;//设置卡牌在y轴的移动距离为itemSpace
					if (i == count - 1) {//如果遍历到了最后一张，把他放在最顶上。
						bringToTop(view);
					} else {
						if ((count == mMaxVisible && i != 0)
								|| count < mMaxVisible) {

							ValueAnimator traslationValue =ValueAnimator.ofFloat(view.getTranslationY(),tranlateY);
							traslationValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
								@Override
								public void onAnimationUpdate(ValueAnimator valueAnimator) {
									Float value = (Float) valueAnimator.getAnimatedValue();
									view.setTranslationY(value);
								}
							});

							ValueAnimator scaleXValue =ValueAnimator.ofFloat(view.getScaleX(),scaleX);
							scaleXValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
								@Override
								public void onAnimationUpdate(ValueAnimator valueAnimator) {
									Float value = (Float) valueAnimator.getAnimatedValue();
									view.setScaleX(value);
								}
							});

							Log.d(TAG, "count := " + count +"   onAnimationEnd: tranlateY = " + tranlateY + "  alphaValue =:1,0" + "  scaleX:" + scaleX);
							AnimatorSet animatorSet =new AnimatorSet();
							animatorSet.setDuration(200);
							animatorSet.setInterpolator(new AccelerateInterpolator());
//							animatorSet.playTogether(translationYAnimator,scaleXAnimator);
							animatorSet.playTogether(traslationValue,scaleXValue);
							animatorSet.start();
//

						}
					}
				}
			}

			@Override
			public void onAnimationCancel(Animator animator) {

			}

			@Override
			public void onAnimationRepeat(Animator animator) {

			}
		});

		return true;
	}

	/**
	 * 将下一个视图移到前边
	 * 
	 * @param view
	 */
	private void bringToTop(final View view) {
		float scaleX = view.getScaleX() + ((float) 1 / mMaxVisible)
				* 0.2f;
		float tranlateY = view.getTranslationY() + itemSpace;

		ValueAnimator traslationValue =ValueAnimator.ofFloat(view.getTranslationY(),tranlateY);
		traslationValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				Float value = (Float) valueAnimator.getAnimatedValue();
				view.setTranslationY(value);
			}
		});
		ValueAnimator alphaValue =ValueAnimator.ofFloat(0,1);
		alphaValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				Float value = (Float) valueAnimator.getAnimatedValue();
				view.setAlpha(value);
			}
		});
		ValueAnimator scaleXValue =ValueAnimator.ofFloat(view.getScaleX(),scaleX);
		scaleXValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				Float value = (Float) valueAnimator.getAnimatedValue();
				view.setScaleX(value);
			}
		});

		Log.d(TAG, "bringToTop:==================== translationY:" + tranlateY + "  alphaValue :=0-1" + "  scaleX =" + scaleX);
		AnimatorSet animatorSet =new AnimatorSet();
		animatorSet.setDuration(200);
		animatorSet.setInterpolator(new AccelerateInterpolator());
//		animatorSet.playTogether(translationYAnimator,alphaAnimator,scaleXAnimator);
		animatorSet.playTogether(traslationValue,alphaValue,scaleXValue);
		animatorSet.start();

		if(playMode==FLAG_PALY_MODE_SINGLE){
			++topPosition;
		}else {
			if (topPosition==mListAdapter.getCount()-1){
				topPosition=0;//从头开始
			}else {
				++topPosition;
			}
		}
		if (mListAdapter!=null){
			mListAdapter.refreshTopItemPosition(topPosition);
		}
	}

	boolean isGoBackPosition=true;
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		float currentY = ev.getY();
		float currentX = ev.getX();
		View topView = getChildAt(getChildCount() - 1);

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			downX = ev.getX();
			downY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			Log.d(TAG, "onInterceptTouchEvent:  MotionEvent.ACTION_MOVE");
			if (mListAdapter.getTurnPageDisEnable()){
				return false;
			}
			return mListAdapter.InterceptActionMove(topView,ev,downX,downY);
//			return true;
		case MotionEvent.ACTION_UP:
			Log.d(TAG, "onInterceptTouchEvent:  MotionEvent.ACTION_UP");
//
//			float distanceX2 = Math.abs(currentX - downX);
//			if (distanceX2>topView.getWidth()/2){
//				return false;
//			}
//			if (distanceX2<mTouchSlop*2){
//				return false;//单击的时候允许item相应点击事件
//			}else {
//				return true;
//			}
			return mListAdapter.InterceptActionUp(topView,ev,downX,downY);
			case MotionEvent.ACTION_POINTER_UP:
				Log.d(TAG, "onInterceptTouchEvent: MotionEvent==== ACTION_POINTER_UP");
				return false;
		}
		return false;
	}

	/**回弹
	 * @param topView
	 * @param motionEvent
	 * @param downX
	 * @param downY
	 */
	private void reBackTopView(final View topView, MotionEvent motionEvent, float downX, float downY) {
		final float backDistenceX =  downX -motionEvent.getX();
		float backDistenceY = Math.abs( downY - motionEvent.getY());
		final int deltaX = 100;
		ValueAnimator animator = ValueAnimator.ofFloat(backDistenceX, 0).setDuration(600);
		animator.setInterpolator(new DecelerateInterpolator());
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animator) {
				float fraction = animator.getAnimatedFraction();
				topView.scrollTo((int)fraction,0);
//				topView.scrollTo(startX + (int) (backDistenceX * fraction), 0);
			}
		});
		animator.start();
	}

	public static Rect getHitRect(Rect rect, View child) {
		rect.left = child.getLeft();
		rect.right = child.getRight();
//		rect.top = (int) (child.getTop() + ViewHelper.getTranslationY(child));
		rect.top = (int) (child.getTop() + child.getTranslationY());
//		rect.bottom = (int) (child.getBottom() + ViewHelper
//				.getTranslationY(child));
		rect.bottom = (int) (child.getBottom() + child.getTranslationY());
		return rect;
	}

	private final DataSetObserver mDataSetObserver = new DataSetObserver() {
		@Override
		public void onChanged() {
			super.onChanged();
		}

		@Override
		public void onInvalidated() {
			super.onInvalidated();
		}
	};

	private OnClickListener listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mListener != null) {
				mListener.onCardClick(v, topPosition);
			}
		}
	};

	public int getTopPosition() {
		return topPosition;
	}
}
