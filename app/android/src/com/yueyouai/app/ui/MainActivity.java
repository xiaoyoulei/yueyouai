package com.yueyouai.app.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.turbo.app.TurboBaseActivity;
import com.yueyouai.app.R;
import com.yueyouai.app.ui.fragment.MainFragment;
import com.yueyouai.app.view.SlidingMenuHelper;

public class MainActivity extends TurboBaseActivity {

	/** View控件*/
	private SlidingMenu sm;
	
	/** 全局变量*/
	private Activity activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		Fragment fragment = new MainFragment();
		getFragmentManager().beginTransaction().replace(R.id.main_content_layout, fragment).commit();
	}

	private void init() {
		activity = this;
		initActionBar();
		initSlidingMenu();
	}

	/** 初始化ActionBar */
	private void initActionBar() {
		// ActionBar actionBar = getActionBar();
		// actionBar.addOnMenuVisibilityListener(new OnMenuVisibilityListener()
		// {
		// @Override
		// public void onMenuVisibilityChanged(boolean isVisible) {
		// Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT)
		// .show();
		// }
		// });
	}

	/**
	 * 初始化SlidingMenu
	 */
	private void initSlidingMenu() {
		sm = SlidingMenuHelper.initSlidingMenu(activity);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/*****************************************
	 * 				监 听 器
	 *****************************************/
	@Override
	public void onBackPressed() {
		if (sm.isMenuShowing())
			sm.showContent();
		else
			super.onBackPressed();
	}
}
