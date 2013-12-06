package com.yueyouai.app.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.OtherTouchListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.turbo.TurboBaseActivity;
import com.turbo.net.VolleyNetHelper.NetCallBack;
import com.turbo.view.TurboLoadingFooter;
import com.turbo.view.TurboToast;
import com.yueyouai.app.App;
import com.yueyouai.app.R;
import com.yueyouai.app.data.HotelMessageBean;
import com.yueyouai.app.ui.adapter.TestMAdapter;
import com.yueyouai.app.view.DockViewHelper;
import com.yueyouai.app.view.PullToRefreshHelper;
import com.yueyouai.app.view.SlidingMenuHelper;

public class MainActivity extends TurboBaseActivity {
	

	/** View控件*/
	private SlidingMenu sm;
	private ListView listView;
	private TurboLoadingFooter loadingFooter;
	private DockViewHelper dockViewHelper;
	
	/** 全局变量*/
	private Activity activity;
	private OnRefreshL refreshListener;
	private PullToRefreshAttacher mPullToRefreshAttacher;

	/** 数据源*/
//	private List<DoMainBean> mainData = new ArrayList<DoMainBean>();
	private List<HotelMessageBean> mainData = new ArrayList<HotelMessageBean>();
	private TestMAdapter adapter;

	 private String url =
	 "https://s3-ap-northeast-1.amazonaws.com/testhotel/hotels.json";// 测试的URl
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		activity = this;
		loadingFooter = new TurboLoadingFooter(activity);
		dockViewHelper = new DockViewHelper(activity);
		initListView();
		initActionBar();
		initSlidingMenu();
		initPullToRefresh();
		loadData(new LoadMoreDoMainNetCallBack());
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		listView = (ListView) findViewById(R.id.listView);
		listView.addFooterView(loadingFooter.getView());
		adapter = new TestMAdapter(activity, mainData, App.getNetHellper());
		listView.setAdapter(adapter);
		listView.setDividerHeight(18);
		// 添加事件监听
		listView.setOnScrollListener(new OnScrollL());
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

	/**
	 * 初始化下拉刷新控件
	 */
	private void initPullToRefresh() {
		refreshListener = new OnRefreshL();
		mPullToRefreshAttacher = PullToRefreshHelper.initPullToRefresh(
				activity, listView, refreshListener,new DockMenuL());
	}

	/**
	 * 加载数据
	 */
	private void loadData(NetCallBack<JSONObject> callBack) {
//		String url = "";
//		int from = SharedPerferencesHelper.newInstance().readInt(
//				Constant.KEY_CURRENT_ITEM_NUM);
//		Map<String, String> params = new HashMap<String, String>();
//		if (0 == from) {
//			params.put("num", Constant.DATA_STEP_NUM + "");
//			url = URLHelper.buildUrl(Constant.URL_DO_MAIN, params);
//		} else {
//			params.put("from", from + "");
//			url = URLHelper.buildUrl(url, params);
//		}
		App.getNetHellper().doJsonGet(activity, url, null, callBack);
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

	/**
	 * 刷新首页数据的网络回调
	 * 
	 * @author Ted
	 */
	private class RefreshDoMainNetCallBack implements NetCallBack<JSONObject> {
		@Override
		public void onSuccess(JSONObject resp) {
//			ArrayList<DoMainBean> data = (ArrayList<DoMainBean>) DoMainBean
//					.parse(resp.toString());
//			HotelMessageBean.parseMessage(resp.toString());
//			mainData.addAll(0, data);
//			mPullToRefreshAttacher.setRefreshComplete();
//			if (mainData.size() > 0) {
//				int currentNum = SharedPerferencesHelper.newInstance().readInt(
//						Constant.KEY_CURRENT_ITEM_NUM);
//				SharedPerferencesHelper.newInstance().writeInt(
//						Constant.KEY_CURRENT_ITEM_NUM,
//						currentNum + mainData.size());
//				adapter.notifyDataSetChanged();
//				TurboToast.showMsg(activity, "更新了" + mainData.size() + "条数据");
//			} else {
//				TurboToast.showMsg(activity, "已加载完全部！");
//				loadingFooter.setState(State.TheEnd);
//			}
			try {
				JSONArray jsonArray = resp.getJSONArray("hotels");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					HotelMessageBean bean = HotelMessageBean
							.parseMessage(jsonObj.toString());
					mainData.add(bean);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(mainData.size() > 0){
				adapter.notifyDataSetChanged();
				TurboToast.showMsg(activity, "目前共有" + mainData.size() + "条数据！");
				mPullToRefreshAttacher.setRefreshComplete();
			}
		}

		@Override
		public void onError(String errorMsg) {
			TurboToast.showMsg(activity, errorMsg);
			// listView.removeFooterView(loadingFooter.getView());
		}
	}

	/**
	 * 加载更多首页数据
	 * 
	 * @author Ted
	 */
	private class LoadMoreDoMainNetCallBack implements NetCallBack<JSONObject> {
		@Override
		public void onSuccess(JSONObject resp) {
//			ArrayList<DoMainBean> data = (ArrayList<DoMainBean>) DoMainBean
//					.parse(resp.toString());
//			mainData.addAll(data);
//			if (mainData.size() > 0) {
//				int currentNum = SharedPerferencesHelper.newInstance().readInt(
//						Constant.KEY_CURRENT_ITEM_NUM);
//				SharedPerferencesHelper.newInstance().writeInt(
//						Constant.KEY_CURRENT_ITEM_NUM,
//						currentNum + mainData.size());
//				// listView.removeFooterView(loadingFooter.getView());
//				adapter.notifyDataSetChanged();
//			} else {
//				loadingFooter.setState(State.TheEnd);
//			}
			
			try {
				JSONArray jsonArray = resp.getJSONArray("hotels");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					HotelMessageBean bean = HotelMessageBean
							.parseMessage(jsonObj.toString());
					mainData.add(bean);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if(mainData.size() > 0)
				adapter.notifyDataSetChanged();
		}

		@Override
		public void onError(String errorMsg) {
			TurboToast.showMsg(activity, errorMsg);
			// listView.removeFooterView(loadingFooter.getView());
		}
	}

	/**
	 * 下拉监听刷新首页数据
	 * 
	 * @author Ted
	 */
	private class OnRefreshL implements OnRefreshListener {
		@Override
		public void onRefreshStarted(View view) {
			loadData(new RefreshDoMainNetCallBack());
		}
	}

	/**
	 * 加载更多数据监听器
	 * 
	 * @author Ted
	 */
	private class OnScrollL implements OnScrollListener {

		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (isScrollEnd(view, loadingFooter.getView())) {
				loadData(new LoadMoreDoMainNetCallBack());
			}
		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
		}

		/**
		 * 判断ScrollView是否滚动到最底部（以targetView为参考） 判断是滚动到底部
		 * 
		 * @param view
		 * @param targetView
		 * @return
		 */
		private boolean isScrollEnd(AbsListView view, View targetView) {
			boolean scrollEnd = false;
			try {
				if (view.getPositionForView(targetView) == view
						.getLastVisiblePosition())
					scrollEnd = true;
			} catch (Exception e) {
				scrollEnd = false;
			}
			return scrollEnd;
		}
	}

	/**
	 * 打开关闭DockMenu的监听器
	 * @author Ted
	 */
	private class DockMenuL implements OtherTouchListener {
		float y1 = 0;
		float y2 = 0;
		float Ydelta = 0;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				y1 = event.getY();
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {
				y2 = event.getY();
				Ydelta = y2 - y1;
				if (Ydelta > 10) {
					// 向下滑动时，显示DockMenu
					dockViewHelper.showDockMenu();
				} else if (Ydelta < -10) {
					// 向上滑动时，因此DockMenu
					dockViewHelper.closeDockMenu();
				}
			}
			return true;
		}
	}
}
