package com.yueyouai.app.view;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.turbo.view.TurboToast;
import com.yueyouai.app.R;
import com.yueyouai.app.ui.LoginActivity;
import com.yueyouai.app.ui.ShareActivity;

/**
 * SlidingMenu助手类
 * @author Ted
 */
public class SlidingMenuHelper {
	
	private static SlidingMenu sm;
	private static Activity mActivity;
	
	private SlidingMenuHelper(){
	}
	
	/**
	 * 初始化SlidingMenu
	 * @param activity
	 * @return
	 */
	public static SlidingMenu initSlidingMenu(Activity activity){
		mActivity = activity;
		sm = new SlidingMenu(mActivity);
		sm.attachToActivity(mActivity, SlidingMenu.SLIDING_WINDOW, false);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setMenu(R.layout.left_sliding_layout);
		initView();
		return sm;
	}
	
	/**
	 * 初始化SlidingMenu中的控件
	 */
	private static void initView(){
		sm.findViewById(R.id.sm_left_btn1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mActivity, LoginActivity.class);
				mActivity.startActivity(intent);
				sm.showContent();
			}
		});
		
		sm.findViewById(R.id.left_btn2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TurboToast.showMsg(mActivity, "我们是一个神秘的组织 ...");
			}
		});
		
		sm.findViewById(R.id.left_btn3).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(mActivity, ShareActivity.class);
				mActivity.startActivity(intent);
				sm.showContent();
			}
		});
	}
	
}
