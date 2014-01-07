package com.yueyouai.app.ui;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.turbo.app.TurboBaseActivity;
import com.turbo.common.CyptoUtils;
import com.turbo.data.SharedPerferencesHelper;
import com.turbo.data.StringHelper;
import com.turbo.net.VolleyNetHelper.NetCallBack;
import com.turbo.view.TurboLoadingDialog;
import com.turbo.view.TurboToast;
import com.yueyouai.app.App;
import com.yueyouai.app.R;
import com.yueyouai.app.data.Constant;

public class LoginActivity extends TurboBaseActivity implements OnClickListener {

	private Activity activity;
	private EditText userNameText; // 账号
	private EditText passwordText; // 密码
	private EditText pswAgainText; // 密码确认
	private EditText mailText; // 邮箱

	private Button loginButton; // 登录按钮
	private Button registerButton; // 注册按钮
	private Button forgetButton; // 忘记密码

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
		findView(); // 初始化控件
	}

	private void findView() {
		loginButton = (Button) findViewById(R.id.act_login_loginBtn);
		registerButton = (Button) findViewById(R.id.act_login_registerBtn);
		forgetButton = (Button) findViewById(R.id.act_login_forgetPswBtn);

		userNameText = (EditText) findViewById(R.id.act_login_account_edit_text);
		passwordText = (EditText) findViewById(R.id.act_login_psw_edit_text);
		pswAgainText = (EditText) findViewById(R.id.act_login_psw_again_edit_text);
		mailText = (EditText) findViewById(R.id.act_register_mail_edit_text);

		// Add listener
		loginButton.setOnClickListener(this);
		registerButton.setOnClickListener(this);
		forgetButton.setOnClickListener(this);

		// 添加文本框监听，以检测用户输入是否合法
		userNameText.addTextChangedListener(new EditorListener(
				R.id.act_login_account_edit_text));
		passwordText.addTextChangedListener(new EditorListener(
				R.id.act_login_psw_edit_text));
		pswAgainText.addTextChangedListener(new EditorListener(
				R.id.act_login_psw_again_edit_text));
		mailText.addTextChangedListener(new EditorListener(
				R.id.act_register_mail_edit_text));
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
	 * 处理并保存Token
	 */
	private void saveToken(String tempToken) {
		// TODO: 此处得到的不是真正的Cookie，需要进一步处理
		Log.e("Ted", tempToken);
		System.out.println(tempToken);
		SharedPerferencesHelper.newInstance().writeString(Constant.COOKIE,
				tempToken);
	}

	/**
	 * 登录
	 */
	private void doLogin() {
		if (mailText.getVisibility() == View.VISIBLE) {
			mailText.setVisibility(View.GONE);
			mailText.startAnimation(AnimationUtils.loadAnimation(activity,
					R.anim.dock_menu_fadeout));
		}

		TurboLoadingDialog.loading(activity, "正在登录...");
		String userName = userNameText.getText().toString();
		String password = passwordText.getText().toString();
		final Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("psw", CyptoUtils.MD5(password));
		new Thread() {
			public void run() {
				App.getNetHellper().doSyncBasePost(Constant.URL_LOGIN, map,
						new DoLoginCallBack());
			};
		}.start();
	}

	/**
	 * 注册
	 */
	private void doRegister() {
		// 1.检查所需信息是否填写完毕
		if (mailText.getVisibility() == View.VISIBLE) {
			// 进行注册
			TurboLoadingDialog.loading(activity, "正在注册...");
			String userName = userNameText.getText().toString();
			String password = passwordText.getText().toString();
			String mail = mailText.getText().toString();
			final Map<String, String> map = new HashMap<String, String>();
			map.put("userName", userName);
			map.put("psw", CyptoUtils.MD5(password));
			map.put("mail", mail);
			new Thread() {
				public void run() {
					App.getNetHellper().doSyncBasePost(Constant.URL_REGISTER,
							map, new DoRegisterCallBack());
				};
			}.start();
		} else {
			// 显示Email文本框
			pswAgainText.setVisibility(View.VISIBLE);
			pswAgainText.startAnimation(AnimationUtils.loadAnimation(activity,
					R.anim.dock_menu_fadein));
			mailText.setVisibility(View.VISIBLE);
			mailText.startAnimation(AnimationUtils.loadAnimation(activity,
					R.anim.dock_menu_fadein));
			pswAgainText.setError("注册需要再次确认密码哦！");
			mailText.setError("注册需要邮箱哦！");

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
			TurboToast.showMsg(activity, errorMsg);
			//TODO:根据错误码，确定错误类型！
			passwordText.setError("账号或密码错误！");
		}

		@Override
		public void onSuccess(HttpResponse resp) {
			String tempToken = resp.getFirstHeader("Set-Cookie").getValue();
			saveToken(tempToken);
		}
	}

	/**
	 * 注册回调
	 * 
	 * @author Ted
	 */
	private class DoRegisterCallBack implements NetCallBack<HttpResponse> {

		@Override
		public void onSuccess(HttpResponse resp) {
			String tempToken = resp.getFirstHeader("Set-Cookie").getValue();
			saveToken(tempToken);
		}

		@Override
		public void onError(String errorMsg) {
			TurboLoadingDialog.endLoading();
			TurboToast.showMsg(activity, errorMsg);
		}
	}

	/**
	 * 注册文本监听
	 * 
	 * @author Ted
	 */
	private class EditorListener implements TextWatcher {

		private int editorId;

		public EditorListener(int editorId) {
			this.editorId = editorId;
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			switch (editorId) {
			case R.id.act_login_account_edit_text:
				// 账号文本框
				accountTextChanged(s.toString());
				break;
			case R.id.act_login_psw_edit_text:
				// 密码文本框
				pswTextChanged(s.toString());
				break;
			case R.id.act_login_psw_again_edit_text:
				// 确认密码文本框
				pswAgainTextChanged(s.toString());
				break;
			case R.id.act_register_mail_edit_text:
				// 邮件文本框
				emailTextChanged(s.toString());
				break;
			default:
				break;
			}
		}

		/**
		 * 正在输入账号
		 * 
		 * @param account
		 */
		private void accountTextChanged(String account) {
			if (account != null && !account.contains(" ")
					&& account.length() > 6) {
				passwordText.setEnabled(true);
				// TODO: 再次优化时，可以添加账号的输入状态的提示
			} else {
				if (mailText.getVisibility() == View.VISIBLE) {
					userNameText.setError("用户名为6~18为数字和字母组合");
				} else {
//					userNameText.setError("账号输入有误");
				}
			}
		}

		/**
		 * 正在输入密码
		 * 
		 * @param psw
		 */
		private void pswTextChanged(String psw) {
			if (psw != null && !psw.contains(" ") && psw.length() > 6) {
				if (pswAgainText.isFocusable()) {
					pswAgainText.setEnabled(true);
				}
				loginButton.setEnabled(true);
			} else {
				if (mailText.getVisibility() == View.VISIBLE) {
					passwordText.setError("密码由6~18位数字字母组合");
				} else {
//					passwordText.setError("密码输入有误！");
				}
			}
		}

		/**
		 * 正在确认密码
		 * 
		 * @param pswAgain
		 */
		private void pswAgainTextChanged(String pswAgain) {
			if (pswAgain != null
					&& pswAgain.equals(passwordText.getText().toString())) {
				mailText.setEnabled(true);
			}
		}

		/**
		 * 正在输入邮箱
		 * 
		 * @param email
		 */
		private void emailTextChanged(String email) {
			if (email != null && StringHelper.isEmail(email)) {
				registerButton.setEnabled(true);
			} else {
				mailText.setError("请输入正确的邮箱地址哦O(∩_∩)O");
			}
		}
	}
}
