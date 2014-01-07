package com.yueyouai.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.turbo.app.TurboAppManager;
import com.turbo.app.TurboBaseActivity;
import com.turbo.app.TurboBaseApp;
import com.yueyouai.app.R;

public class SplashActivity extends Activity {
	
	private ImageView image; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		
	}
	//TODO:进行一些应用的初始化操作，在操作完成后跳转到相应的Activity中，在此过程中不做网络操作
	private void initView() {
		AlphaAnimation animation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.fade_in);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				//TODO: 做初始化的操作，在初始化完成后回调redirectTo方法，启动LoginActivity
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {
				redirectTo();
			}
		});
		image = (ImageView) findViewById(R.id.splash_image);
		image.startAnimation(animation);
	}
	
	/**
	 * 跳转到MainActivity中去
	 */
	private void redirectTo(){
		Intent intent = new Intent();
		intent.setClass(this, LoginActivity.class);
		startActivity(intent);
		this.finish();
	}
}
