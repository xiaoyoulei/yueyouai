package com.yueyouai.app.ui;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.turbo.app.TurboBaseActivity;
import com.turbo.data.SharedPerferencesHelper;
import com.turbo.view.TurboToast;
import com.turbo.volley.toolbox.NetworkImageView;
import com.yueyouai.app.R;
import com.yueyouai.app.ui.fragment.MainFragment;
import com.yueyouai.app.view.SlidingMenuHelper;

public class MainActivity extends TurboBaseActivity {

	/** View控件*/
	private SlidingMenu sm;
	private ActionBar actionBar;
	
	/** SlidingMenu部分*/
	private NetworkImageView userPhoto;
	private TextView userNameText;
	
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
	@SuppressLint("NewApi")
	private void initActionBar() {
		actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
	}

	/**
	 * 初始化SlidingMenu
	 */
	private void initSlidingMenu() {
		sm = SlidingMenuHelper.initSlidingMenu(activity);
		userPhoto = (NetworkImageView) sm.findViewById(R.id.left_top_user_photo_img);
		userNameText = (TextView) sm.findViewById(R.id.left_top_userName_text);
		String userName = SharedPerferencesHelper.newInstance().readString("username");
		userNameText.setText(userName);
		userPhoto.setErrorImageResId(R.drawable.ic_launcher);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if(sm.isMenuShowing()){
				//关闭SlidingMenu
				TurboToast.showMsg(activity, "关闭SM");
				actionBar.setDisplayHomeAsUpEnabled(false);
			}else{
				//显示SlidingMenu
				sm.showMenu();
				actionBar.setDisplayHomeAsUpEnabled(true);
			}
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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
