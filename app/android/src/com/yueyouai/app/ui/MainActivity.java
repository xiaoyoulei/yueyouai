package com.yueyouai.app.ui;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.OnMenuVisibilityListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.turbo.TurboBaseActivity;
import com.turbo.view.LoadingDialog;
import com.turbo.view.TurboToast;
import com.yueyouai.app.R;

public class MainActivity extends TurboBaseActivity implements OnRefreshListener{

	private PullToRefreshAttacher mPullToRefreshAttacher;
	private SlidingMenu sm;
	private ListView listView;
	private Activity activity;
	
	 static String[] ITEMS = {"Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam",
         "Abondance", "Ackawi", "Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu",
         "Airag", "Airedale", "Aisy Cendre", "Allgauer Emmentaler", "Abbaye de Belloc",
         "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi", "Acorn", "Adelost",
         "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
         "Allgauer Emmentaler","太平天国","一户人家","有得有失","加油到底"};
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		activity = this;
		initActionBar();
		initPullToRefresh();
		initSlidingMenu();
		initListView();
	}

	@SuppressLint("NewApi")
	private void initListView() {
		listView = (ListView) findViewById(R.id.listView);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.test_list_item, ITEMS);
		listView.addFooterView(getLayoutInflater().inflate(R.layout.test_list_item, null));
		listView.setAdapter(adapter);
		listView.setBackgroundColor(Color.TRANSPARENT);
		listView.setBackgroundResource(R.drawable.toast_view_bg);
		listView.setCacheColorHint(Color.CYAN);
		listView.setDividerHeight(0);
	}

	/** 初始化ActionBar*/
	@SuppressLint("NewApi")
	private void initActionBar() {
		// TODO Auto-generated method stub
		ActionBar actionBar = getActionBar();
		actionBar.addOnMenuVisibilityListener(new OnMenuVisibilityListener() {
			@Override
			public void onMenuVisibilityChanged(boolean isVisible) {
				Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
			}
		});
	}

	/**
	 * 初始化SlidingMenu
	 */
	private void initSlidingMenu() {
		sm = new SlidingMenu(this);
		sm.attachToActivity(this, SlidingMenu.SLIDING_WINDOW,true);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setMenu(R.layout.left_sliding_layout);
		
		sm.findViewById(R.id.left_btn1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TurboToast.showMsg(activity, "按钮1被点击！");
				LoadingDialog.loading(MainActivity.this, "Hi Guys !");
			}
		});;
	}



	private void initPullToRefresh() {
		// Get View for which the user will scroll…
		View scrollableView = findViewById(R.id.listView);
	    // Create a PullToRefreshAttacher instance
	    mPullToRefreshAttacher = PullToRefreshAttacher.get(this);
	    // Add the Refreshable View and provide the refresh listener
	    mPullToRefreshAttacher.addRefreshableView(scrollableView, this);
	    mPullToRefreshAttacher.setRefreshing(true);
	    onRefreshStarted(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/*****************************************
	 * 			监	 听	器
	 *****************************************/
	/** 滑动刷新回调函数*/
	@Override
	public void onRefreshStarted(View view) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                //通知加载完成
                mPullToRefreshAttacher.setRefreshComplete();
                TurboToast.showMsg(MainActivity.this,Gravity.CENTER, "已是最新内容");
            }
        }.execute();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		LoadingDialog.endLoading();
	}
}
