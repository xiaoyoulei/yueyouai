package com.yueyouai.app.view;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.turbo.view.TurboLoadingDialog;
import com.turbo.view.TurboToast;
import com.yueyouai.app.R;

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
		sm.attachToActivity(mActivity, SlidingMenu.SLIDING_WINDOW, true);
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
		sm.findViewById(R.id.left_btn1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TurboToast.showMsg(mActivity, "按钮1被点击！");
				TurboLoadingDialog.loading(mActivity, "Hi Guys !");
				sm.showContent();
			}
		});
		
		sm.findViewById(R.id.left_btn2).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TurboToast.showMsg(mActivity, "按钮2被点击");
			}
		});
	}
	
}
