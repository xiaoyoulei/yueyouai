package com.yueyouai.app.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.turbo.TurboBaseActivity;
import com.turbo.common.CommonUtils;
import com.turbo.data.SharedPerferencesHelper;
import com.turbo.net.VolleyNetHelper.NetCallBack;
import com.turbo.view.TurboLoadingDialog;
import com.turbo.view.TurboToast;
import com.yueyouai.app.App;
import com.yueyouai.app.R;
import com.yueyouai.app.data.Constant;

public class LoginActivity extends TurboBaseActivity implements OnClickListener{
	
	private Activity activity; 
	private EditText userNameText;
	private EditText passwordText;
	private EditText mailText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}
	
	
	/**
	 * 初始化
	 */
	private void init() {
		this.activity = this;
		findView();		//初始化控件
	}

	private void findView() {
		findViewById(R.id.act_login_loginBtn).setOnClickListener(this);
		findViewById(R.id.act_login_registerBtn).setOnClickListener(this);
		userNameText = (EditText) findViewById(R.id.act_login_account_edit_text);
		passwordText = (EditText) findViewById(R.id.act_login_psw_edit_text);
		mailText = (EditText) findViewById(R.id.act_register_mail_edit_text);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.act_login_loginBtn:
			doLogin();
			break;
		case R.id.act_login_registerBtn:
			doRegister();
			break;
		default:
			break;
		}
	}

	/**
	 * 登录
	 */
	private void doLogin() {
		TurboLoadingDialog.loading(activity, "正在登录...");
		String userName = userNameText.getText().toString();
		String password = passwordText.getText().toString();
		final Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("psw", CommonUtils.md5(password));
		new Thread(){
			public void run() {
				App.getNetHellper().doSyncBasePost(Constant.URL_LOGIN, map, new DoLoginCallBack());
			};
		}.start();
	}
	
	/**
	 * 注册 
	 */
	private void doRegister() {
		TurboLoadingDialog.loading(activity, "正在注册...");
		String userName = userNameText.getText().toString();
		String password = passwordText.getText().toString();
		String mail = mailText.getText().toString();
		final Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("psw", CommonUtils.md5(password));
		map.put("mail", mail);
		
		new Thread(){
			public void run() {
				App.getNetHellper().doSyncBasePost(Constant.URL_REGISTER, map, new DoRegisterCallBack());
			};
		}.start();
	}

	/**
	 * 登录回调
	 * @author Ted
	 */
	private class DoLoginCallBack implements NetCallBack<HttpResponse>{

		@Override
		public void onError(String errorMsg) {
			TurboLoadingDialog.endLoading();
			TurboToast.showMsg(activity, errorMsg);
		}
		@Override
		public void onSuccess(HttpResponse resp) {
			String tempToken = resp.getFirstHeader("Set-Cookie").getValue();
			saveToken(tempToken);
		}
	}
	
	private class DoRegisterCallBack implements NetCallBack<HttpResponse>{

		@Override
		public void onSuccess(HttpResponse resp) {
			String tempToken  = resp.getFirstHeader("Set-Cookie").getValue();
			saveToken(tempToken);
		}

		@Override
		public void onError(String errorMsg) {
			TurboLoadingDialog.endLoading();
			TurboToast.showMsg(activity, errorMsg);
		}
		
	}
	
	/**
	 * 处理并保存Token
	 */
	private void saveToken(String tempToken){
		//TODO: 此处得到的不是真正的Cookie，需要进一步处理
		SharedPerferencesHelper.newInstance().writeString(Constant.COOKIE, tempToken);
	}
	
}
