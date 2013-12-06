package com.yueyouai.app.view;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yueyouai.app.R;

/**
 * 底部按钮控件助手类
 * 
 * @author Ted
 */
public class DockViewHelper {

	private Activity mActivity;
	private LinearLayout leftBtnArea;
	private LinearLayout rightBtnArea;
	private LinearLayout middleBtnArea;

	private TextView leftBtnText;
	private TextView middleBtnText;
	private TextView rightBtnText;

	private Animation showAnimation;
	private Animation closeAnimation;

	private static boolean isDockMenuVisible = true;
	private ViewGroup viewGroup;

	public DockViewHelper(Activity activity) {
		this.mActivity = activity;
		initView();
	}

	private void initView() {
		// 初始化动画
		showAnimation = AnimationUtils.loadAnimation(mActivity,
				R.anim.dock_menu_fadein);
		closeAnimation = AnimationUtils.loadAnimation(mActivity,
				R.anim.dock_menu_fadeout);
		
		viewGroup = (ViewGroup) mActivity.findViewById(R.id.view_dock_menu_layout);
		
		// 初始化控件
		leftBtnArea = (LinearLayout) mActivity
				.findViewById(R.id.dock_menu_leftBtn_area);
		middleBtnArea = (LinearLayout) mActivity
				.findViewById(R.id.dock_menu_middleBtn_area);
		rightBtnArea = (LinearLayout) mActivity
				.findViewById(R.id.dock_menu_rightBtn_area);

		leftBtnText = (TextView) mActivity
				.findViewById(R.id.dock_menu_leftBtn_text);
		middleBtnText = (TextView) mActivity
				.findViewById(R.id.dock_menu_middleBtn_text);
		rightBtnText = (TextView) mActivity
				.findViewById(R.id.dock_menu_rightBtn_text);

		addListener();
	}

	/**
	 * 对按钮添加点击事件监听
	 */
	private void addListener() {
	}

	/**
	 * 关闭DockMenu
	 */
	public void closeDockMenu() {
		if (isDockMenuVisible) {
			closeAnimation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					viewGroup.setVisibility(View.INVISIBLE);
					isDockMenuVisible = false;
				}
			});
			viewGroup.startAnimation(closeAnimation);
		}
	}

	/**
	 * 打开DockMenu
	 */
	public void showDockMenu() {
		if (!isDockMenuVisible) {
			showAnimation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {}
				@Override
				public void onAnimationRepeat(Animation animation) {}
				@Override
				public void onAnimationEnd(Animation animation) {
					viewGroup.setVisibility(View.VISIBLE);
					isDockMenuVisible = true;
				}
			});
			viewGroup.startAnimation(showAnimation);
		}
	}

	/**
	 * 显示
	 * 
	 * @param view
	 * @param delayTime
	 */
	private void show(final View view, int delayTime) {
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				view.startAnimation(showAnimation);
				view.setVisibility(View.VISIBLE);
			}
		}, delayTime);
	}

	/**
	 * 关闭
	 * 
	 * @param view
	 * @param delayTime
	 */
	private void close(final View view, int delayTime) {
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				view.setVisibility(View.INVISIBLE);
				view.startAnimation(closeAnimation);
			}
		}, delayTime);
	}
}
