package com.yueyouai.app.ui;

import android.app.Activity;
import android.os.Bundle;

import com.yueyouai.app.R;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}
	//TODO:进行一些应用的初始化操作，在操作完成后跳转到相应的Activity中，在此过程中不做网络操作
	/**
	 * 跳转到MainActivity中去
	 */
	private void redirectTo(){
		
	}
}
