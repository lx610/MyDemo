package com.demo.lixuan.mydemo.widgt.cardPageView;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.demo.lixuan.mydemo.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.chad.library.adapter.base.listener.SimpleClickListener.TAG;

public abstract class QuickCardHorizonSkipAdapter<T> extends BaseCardAdapter {
	public final Context mContext;
	private OnTopItemChangeListener mOnItemSelectListener;

	 public ArrayList<T> mData;
	private int mTopViewPosison;

	public QuickCardHorizonSkipAdapter(Context context) {
		mContext = context;
		mData = new ArrayList<T>();
	}

	public QuickCardHorizonSkipAdapter(Context context, Collection<? extends T> items) {
		mContext = context;
		mData = new ArrayList<T>(items);
	}

	@Override
	public void refreshTopItemPosition(int topPosition) {
		if (mOnItemSelectListener!=null){
			mOnItemSelectListener.refreshTopPosion(topPosition);
		}
		mTopViewPosison = topPosition;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		FrameLayout wrapper = (FrameLayout) convertView;
		View cardView;
		View convertedCardView;
		if (wrapper == null) {
			wrapper = new FrameLayout(mContext);
			wrapper.setBackgroundResource(R.drawable.shape_bg_round_blue_10dp);
			cardView = getCardView(position, null, wrapper);
			wrapper.addView(cardView);
		} else {
			cardView = wrapper.getChildAt(0);
			convertedCardView = getCardView(position, cardView, wrapper);
			//要先删除，然后再添加，否则界面不更新
			wrapper.removeView(cardView);
			wrapper.addView(convertedCardView);
			if (convertedCardView != cardView) {
			}
		}
		return wrapper;
	}

	protected abstract View getCardView(int position, View convertView, ViewGroup parent);

	public void addAll(List<T> items){
		mData.addAll(items);
	}
	
	@Override
	public T getItem(int position) {
		return mData.get(position);
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).hashCode();
	}

	public Context getContext() {
		return mContext;
	}
	
	public void clear(){
		if(mData != null){
			mData.clear();
		}
	}

	public int getTopViewPosison() {
		return mTopViewPosison;
	}

	public interface OnTopItemChangeListener {
		void refreshTopPosion(int topPosition);
	}

	public void setOnItemSelectListener(OnTopItemChangeListener onItemSelectListener) {
		mOnItemSelectListener = onItemSelectListener;
	}

	@Override
	public boolean InterceptActionUp(View topView, MotionEvent ev, float downX, float downY) {
		ViewConfiguration con = ViewConfiguration.get(getContext());
		int mTouchSlop = con.getScaledTouchSlop();//设置最小滑动触发距离
		float currentX = ev.getX();
			float distanceX2 = Math.abs(currentX - downX);
			if (distanceX2>topView.getWidth()/2){
				return false;
			}
			if (distanceX2<mTouchSlop*2){
				return false;//单击的时候允许item相应点击事件
			}else {
				return true;
			}
	}

	@Override
	public boolean moveTopView(View topView, MotionEvent event, float downX, float downY) {
		float currentX = event.getX();
		float currentY = event.getY();
		float distanceX =Math.abs(currentX - downX);
			float moveX =downX - currentX;
			float distanceY =Math.abs(currentY - downY);
			if (distanceX>distanceY){//向左滑动
//					topView.setTranslationX(currentX - downX);
				topView.scrollTo((int) moveX,0);
//					if (topView.getTranslationX()<topView.getWidth()/2||topView.getTranslationX()>topView.getWidth()/2){
				float distence = Math.abs(topView.getTranslationX());
				if (distanceX>topView.getWidth()/2){
					Log.d(TAG, "MotionEvent.ACTION_MOVE distanceX>topView.getWidth()/2: distanceX:====" + distanceX);
					return false;
				}
			}
		return true;
	}

	@Override
	public AnimatorSet getTurnPageAnimator(final View topView, MotionEvent event, float downX, float downY) {
		ValueAnimator translationYanimator;
		float startPoitX = downX - event.getX();
		if (event.getX()-downX>0){//往右边滑
//			translationYanimator = ValueAnimator.ofFloat(event.getX(), event.getX() + topView.getWidth());
			translationYanimator = ValueAnimator.ofInt((int)startPoitX, (int)startPoitX - topView.getWidth());
		}else {//往左边滑
//			translationYanimator = ValueAnimator.ofFloat(event.getX(), event.getX() - topView.getWidth());
			translationYanimator = ValueAnimator.ofInt((int)startPoitX, (int)startPoitX +(int)topView.getWidth());
		}

		translationYanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				Integer value = (Integer) animation.getAnimatedValue();
//				Float value = (Float) animation.getAnimatedValue();
				topView.scrollTo(value,0);

//				topView.setTranslationX(value);
			}
		});
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
		AnimatorSet animatorSet =new AnimatorSet();
		animatorSet.setDuration(300);
		animatorSet.playTogether(translationYanimator);
		animatorSet.start();
		return animatorSet;
	}

	/**归位
	 * @param topView
	 * @param event
	 * @param downX
	 * @param downY
	 */
	@Override
	public void rebackToView(final View topView, MotionEvent event, float downX, float downY) {
		final float backDistenceX =  downX -event.getX();
		float backDistenceY = Math.abs( downY - event.getY());
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
}
