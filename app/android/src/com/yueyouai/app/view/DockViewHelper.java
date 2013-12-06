package com.yueyouai.app.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.yueyouai.app.R;

/**
 * 底部按钮控件助手类
 * @author Ted
 */
public class DockViewHelper {
	
	private Context mContext;
	private LayoutInflater mInflater;
	private Button leftBtn;
	private Button rightBtn;
	private Button middleBtn;
	
	private TextView leftBtnText;
	private TextView middleBtnText;
	private TextView rightBtnText;
	
	private Animation showAnimation;
	private Animation closeAnimation;
	
	private int IntervalTime = 50;
	
	
	public DockViewHelper(Context context){
		this.mContext = context;
		this.mInflater = LayoutInflater.from(context);
		initView();
	}
	
	
	private void initView() {
		//初始化动画
		showAnimation = AnimationUtils.loadAnimation(mContext, R.anim.dock_menu_show);
		closeAnimation= AnimationUtils.loadAnimation(mContext, R.anim.dock_menu_close);
		//设置显示位置
		LinearLayout layout = (LinearLayout) mInflater.inflate(R.layout.view_dock_menu, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.gravity = Gravity.BOTTOM;
		layout.setLayoutParams(params);
		
		//初始化控件
		leftBtn = (Button) layout.findViewById(R.id.dock_menu_leftBtn);
		middleBtn = (Button) layout.findViewById(R.id.dock_menu_middleBtn);
		rightBtn = (Button) layout.findViewById(R.id.dock_menu_rightBtn);
		
		leftBtnText = (TextView) layout.findViewById(R.id.dock_menu_leftBtn_text);
		middleBtnText = (TextView) layout.findViewById(R.id.dock_menu_middleBtn_text);
		rightBtn = (Button) layout.findViewById(R.id.dock_menu_rightBtn_text);
		
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
	public void closeDockMenu(){
		close(leftBtn, 0);
		close(middleBtn, IntervalTime);
		close(rightBtn, IntervalTime*2);
	}
	
	/**
	 * 打开DockMenu
	 */
	public void showDockMenu(){
		show(leftBtn, 0);
		show(middleBtn, IntervalTime);
		show(rightBtn, IntervalTime*2);
		
	}
	
	/**
	 * 显示
	 * @param view
	 * @param delayTime
	 */
	private void show(final View view, int delayTime){
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				view.setVisibility(View.VISIBLE);
				view.startAnimation(showAnimation);
			}
		}, delayTime);
	}
	
	/**
	 * 关闭
	 * @param view
	 * @param delayTime
	 */
	private void close(final View view, int delayTime) {
		view.postDelayed(new Runnable() {
			@Override
			public void run() {
				view.setVisibility(View.GONE);
				view.startAnimation(closeAnimation);
			}
		}, delayTime);
	}
}
