package com.yueyouai.app.view;
import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.app.Activity;
import android.view.View;

/**
 * 下拉刷新控件助手类
 * @author Ted
 */
public class PullToRefreshHelper {
	
	/**
	 * 初始化下拉刷新控件
	 * @param activity
	 * @param scrollableView
	 * @param refreshListener
	 * @return
	 */
	public static PullToRefreshAttacher initPullToRefresh(Activity activity,View scrollableView,OnRefreshListener refreshListener) {
		// Get View for which the user will scroll…
		// View scrollableView = findViewById(R.id.listView);
		// Create a PullToRefreshAttacher instance
		PullToRefreshAttacher mPullToRefreshAttacher = PullToRefreshAttacher.get(activity);
		// Add the Refreshable View and provide the refresh listener
		mPullToRefreshAttacher.addRefreshableView(scrollableView, refreshListener);
		mPullToRefreshAttacher.setRefreshing(true);
		return mPullToRefreshAttacher;
	}
}
