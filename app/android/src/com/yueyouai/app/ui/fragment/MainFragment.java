package com.yueyouai.app.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarsherlock.PullToRefreshAttacher;
import uk.co.senab.actionbarpulltorefresh.library.OtherTouchListener;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshAttacher.OnRefreshListener;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.turbo.common.URLHelper;
import com.turbo.data.SharedPerferencesHelper;
import com.turbo.net.VolleyNetHelper.NetCallBack;
import com.turbo.view.TurboLoadingFooter;
import com.turbo.view.TurboLoadingFooter.State;
import com.turbo.view.TurboToast;
import com.yueyouai.app.App;
import com.yueyouai.app.R;
import com.yueyouai.app.data.Constant;
import com.yueyouai.app.data.DoMainBean;
import com.yueyouai.app.data.HotelMessageBean;
import com.yueyouai.app.ui.adapter.DoMainAdapter;
import com.yueyouai.app.view.DockViewHelper;
import com.yueyouai.app.view.PullToRefreshHelper;

/**
 * 首页数据
 * @author Ted
 */
public class MainFragment extends Fragment{
	
	/** View控件*/
	private ListView listView;
	private TurboLoadingFooter loadingFooter;
	private DockViewHelper dockViewHelper;
	
	/** 全局变量*/
	private Activity activity;
	private OnRefreshL refreshListener;
	private PullToRefreshAttacher mPullToRefreshAttacher;

	/** 数据源*/
	private List<DoMainBean> mainData = new ArrayList<DoMainBean>();
	private DoMainAdapter adapter;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}
	
	private void init() {
		activity = getActivity();
		dockViewHelper = new DockViewHelper(activity);
		loadingFooter = new TurboLoadingFooter(activity);
		initListView();
		initActionBar();
		initPullToRefresh();
		loadData(new LoadMoreDoMainNetCallBack());
	}

	/**
	 * 初始化ListView
	 */
	private void initListView() {
		listView = (ListView) activity.findViewById(R.id.listView);
		listView.addFooterView(loadingFooter.getView());
		adapter = new DoMainAdapter(activity, mainData, App.getNetHellper());
		listView.setAdapter(adapter);
		listView.setDividerHeight(18);
		// 添加事件监听
		listView.setOnScrollListener(new OnScrollL());
	}

	/** 初始化ActionBar */
	private void initActionBar() {}

	/**
	 * 初始化下拉刷新控件
	 */
	private void initPullToRefresh() {
		refreshListener = new OnRefreshL();
		mPullToRefreshAttacher = PullToRefreshHelper.initPullToRefresh(
				activity, listView, refreshListener,new DockMenuL());
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
	 * 加载数据
	 */
	private void loadData(NetCallBack<JSONObject> callBack) {
		String url = "";
		Map<String, String> params = new HashMap<String, String>();
		if (mainData == null || mainData.size() == 0) {
			params.put("num", Constant.DATA_STEP_NUM + "");
			url = URLHelper.buildUrl(Constant.URL_DO_MAIN, params);
		} else {
			int from = mainData.get(0).getId();
			params.put("from", from + "");
			params.put("num", Constant.DATA_STEP_NUM + "");
			url = URLHelper.buildUrl(Constant.URL_DO_MAIN, params);
		}
		App.getNetHellper().doJsonGet(activity, url, null, callBack);
	}
	
	/**
	 * 刷新首页数据的网络回调
	 * @author Ted
	 */
	private class RefreshDoMainNetCallBack implements NetCallBack<JSONObject> {
		@Override
		public void onSuccess(JSONObject resp) {
			ArrayList<DoMainBean> data = (ArrayList<DoMainBean>) DoMainBean
					.parse(resp.toString());
			HotelMessageBean.parseMessage(resp.toString());
			mainData.addAll(0, data);
			mPullToRefreshAttacher.setRefreshComplete();
			if (mainData.size() > 0) {
				int currentNum = SharedPerferencesHelper.newInstance().readInt(
						Constant.KEY_CURRENT_ITEM_NUM);
				SharedPerferencesHelper.newInstance().writeInt(
						Constant.KEY_CURRENT_ITEM_NUM,
						currentNum + mainData.size());
				adapter.notifyDataSetChanged();
				TurboToast.showMsg(activity, "更新了" + data.size() + "条数据");
			} else {
				TurboToast.showMsg(activity, "已加载完全部！");
				loadingFooter.setState(State.TheEnd);
			}
		}

		@Override
		public void onError(String errorMsg) {
			TurboToast.showMsg(activity, errorMsg);
			listView.removeFooterView(loadingFooter.getView());
		}
	}

	/**
	 * 加载更多首页数据
	 * 
	 * TODO:此处逻辑需要修改
	 * 
	 * @author Ted
	 */
	private class LoadMoreDoMainNetCallBack implements NetCallBack<JSONObject> {
		@Override
		public void onSuccess(JSONObject resp) {
			ArrayList<DoMainBean> data = (ArrayList<DoMainBean>) DoMainBean
					.parse(resp.toString());
			mainData.addAll(data);
			if (mainData.size() > 0) {
				int currentNum = SharedPerferencesHelper.newInstance().readInt(
						Constant.KEY_CURRENT_ITEM_NUM);
				SharedPerferencesHelper.newInstance().writeInt(
						Constant.KEY_CURRENT_ITEM_NUM,
						currentNum + mainData.size());
				adapter.notifyDataSetChanged();
			} else {
				loadingFooter.setState(State.TheEnd);
			}
		}

		@Override
		public void onError(String errorMsg) {
			TurboToast.showMsg(activity, errorMsg);
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
