package com.yueyouai.app.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.turbo.TurboBaseActivity;
import com.yueyouai.app.R;

public class LoginActivity extends TurboBaseActivity implements OnClickListener{
	
	private EditText userNameText;
	private EditText passwordText;
	
	
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
		findView();		//初始化控件
		addListener();	//添加监听器
	}


	private void findView() {
		findViewById(R.id.act_login_loginBtn).setOnClickListener(this);
		findViewById(R.id.act_login_registerBtn).setOnClickListener(this);
		userNameText = (EditText) findViewById(R.id.act_login_account_edit_text);
		passwordText = (EditText) findViewById(R.id.act_login_psw_edit_text);
	}
	
	private void addListener(){
		
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
		String userName = userNameText.getText().toString();
		String password = passwordText.getText().toString();
	}
	
	/**
	 * 注册 
	 */
	private void doRegister() {
		
	}

}
