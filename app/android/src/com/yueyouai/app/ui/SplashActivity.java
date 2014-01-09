package com.yueyouai.app.ui;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.turbo.data.SharedPerferencesHelper;
import com.turbo.net.VolleyNetHelper.NetCallBack;
import com.turbo.view.TurboLoadingDialog;
import com.yueyouai.app.App;
import com.yueyouai.app.R;
import com.yueyouai.app.data.Constant;

public class SplashActivity extends Activity {
	
	private ImageView image; 
	private Activity activity;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		activity = this;
		init();
	}
	
	private void init() {
		AlphaAnimation animation = (AlphaAnimation) AnimationUtils.loadAnimation(this, R.anim.fade_in);
		animation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				//开始自动登录，如果登录失败，跳转到登录页面
				doAutoLogin();
			}
			@Override
			public void onAnimationRepeat(Animation animation) {}
			@Override
			public void onAnimationEnd(Animation animation) {}
		});
		image = (ImageView) findViewById(R.id.splash_image);
		image.startAnimation(animation);
	}
	
	/**
	 * 进行自动登录
	 */
	private void doAutoLogin() {
		String userName = SharedPerferencesHelper.newInstance().readString("username");
		String password = SharedPerferencesHelper.newInstance().readString("psw");
		if(userName == null || password == null || TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(activity, LoginActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}else{
			final Map<String, String> map = new HashMap<String, String>();
			map.put("username", userName);
			map.put("psw", password);
			new Thread() {
				public void run() {
					App.getNetHellper().doSyncBasePost(Constant.URL_LOGIN, map,
							new DoLoginCallBack());
				};
			}.start();
		}
	}
	
	
	/**
	 * 登录回调
	 * 
	 * @author Ted
	 */
	private class DoLoginCallBack implements NetCallBack<HttpResponse> {
		@Override
		public void onError(String errorMsg) {
			TurboLoadingDialog.endLoading();
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(activity, LoginActivity.class);
			activity.startActivity(intent);
			activity.finish();
		}

		@Override
		public void onSuccess(HttpResponse resp) {
			try {
				String jsonStr = EntityUtils.toString(resp.getEntity());
				JSONObject obj = new JSONObject(jsonStr);
				final int status = obj.getInt("status");
				if(status == Constant.RESPONSE_STATUS_SUCCESS){
					//登录成功后保存Cookie
					SharedPerferencesHelper.newInstance().writeString(Constant.COOKIE, resp.getHeaders(Constant.COOKIE).toString());
					//进入应用首页
					Intent intent = new Intent();
					intent.setClass(activity, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					activity.finish();
				}else{
					//自动登录失败，启动登录页
					Intent intent = new Intent();
					intent.setClass(activity, LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					activity.finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
