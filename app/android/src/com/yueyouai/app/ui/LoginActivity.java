package com.yueyouai.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.turbo.net.impl.TurboJSONResponse;
import com.turbo.view.TurboLoadingDialog;
import com.turbo.view.TurboToast;
import com.yueyouai.app.App;
import com.yueyouai.app.R;
import com.yueyouai.app.data.Constant;

import org.apache.http.ParseException;
import org.apache.http.cookie.Cookie;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		// 如果用户登录过，则自动填充文本框
		String userName = SharedPerferencesHelper.newInstance().readString(
				"username");
		userNameText.setText(userName);
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
		if (mailText.getVisibility() == View.VISIBLE) {
			mailText.setVisibility(View.GONE);
			mailText.startAnimation(AnimationUtils.loadAnimation(activity,
					R.anim.dock_menu_fadeout));
			pswAgainText.setVisibility(View.GONE);
			pswAgainText.startAnimation(AnimationUtils.loadAnimation(activity,
					R.anim.dock_menu_fadeout));
		}

		TurboLoadingDialog.loading(activity, "正在登录...");
		String userName = userNameText.getText().toString();
		String password = passwordText.getText().toString();
		final Map<String, String> map = new HashMap<String, String>();
		map.put("username", userName);
		map.put("psw", CyptoUtils.MD5(password));
//		new Thread() {
//			public void run() {
//				App.getNetHellper().doSyncBasePost(Constant.URL_LOGIN, map,
//						new DoLoginCallBack());
//			};
//		}.start();
		App.getNetHellper().doTurboJSONPost(Constant.URL_LOGIN, map, null, new DoLoginCallBack());
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
			map.put("username", userName);
			map.put("psw", CyptoUtils.MD5(password));
			map.put("mail", mail);
//			new Thread() {
//				public void run() {
//					App.getNetHellper().doSyncBasePost(Constant.URL_REGISTER,
//							map, new DoRegisterCallBack());
//				};
//			}.start();
			App.getNetHellper().doTurboJSONPost(Constant.URL_REGISTER, map, null, new DoRegisterCallBack());
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
	private class DoLoginCallBack implements NetCallBack<TurboJSONResponse> {
		@Override
		public void onError(String errorMsg) {
			TurboLoadingDialog.endLoading();
			TurboToast.showMsg(activity, errorMsg);
		}

		@Override
		public void onSuccess(TurboJSONResponse jsonResp) {
			try {
				TurboLoadingDialog.endLoading();
				JSONObject obj = jsonResp.take();
				final int status = obj.getInt("status");
				if (status == Constant.RESPONSE_STATUS_SUCCESS) {
					// 登录成功后保存Cookie
                    List<Cookie> cookies = jsonResp.getCookies();
                    for(Cookie cookie : cookies){
                        if(Constant.COOKIE.equals(cookie.getName())){
                            SharedPerferencesHelper.newInstance().writeString(Constant.COOKIE, cookie.getValue());
                            break;
                        }
                    }
					// 保存用户名
					SharedPerferencesHelper.newInstance().writeString(
							"username", userNameText.getText().toString());
					// 保存密码
					SharedPerferencesHelper.newInstance().writeString("psw",
							CyptoUtils.MD5(passwordText.getText().toString()));
					// 进入应用首页
					Intent intent = new Intent();
					intent.setClass(activity, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					activity.finish();
				} else {
					// 登录不成功，从info中读出错误信息
					final String info = "用户名或密码错误！";
//					if (obj.has("info"))
//						info = obj.getString("info");
					TurboToast.showMsg(activity, "状态码：" + status + " 错误信息："
							+ info);
					
					switch (status) {
					case 2001:	//从文档中读取
						// 用户名或密码错误
						activity.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								passwordText.setError(info);
								passwordText.requestFocus();
							}
						});
						break;
					default:
						break;
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 注册回调
	 * 
	 * @author Ted
	 */
	private class DoRegisterCallBack implements NetCallBack<TurboJSONResponse> {
		@Override
		public void onSuccess(TurboJSONResponse jsonResp) {
			try {
				TurboLoadingDialog.endLoading();
				JSONObject obj = jsonResp.take();
				final int status = obj.getInt("status");
				if (status == Constant.RESPONSE_STATUS_SUCCESS) {
					// 注册成功后保存Cookie
                    List<Cookie> cookies = jsonResp.getCookies();
                    for(Cookie cookie : cookies){
                        if(Constant.COOKIE.equals(cookie.getName())){
                            SharedPerferencesHelper.newInstance().writeString(Constant.COOKIE, cookie.getValue());
                            break;
                        }
                    }
					// 保存用户名
					SharedPerferencesHelper.newInstance().writeString(
							"username", obj.getString("username"));
					// 保存邮箱账号
					SharedPerferencesHelper.newInstance().writeString("mail",
							obj.getString("mail"));
					// 保存密码
					SharedPerferencesHelper.newInstance().writeString("psw",
							CyptoUtils.MD5(passwordText.getText().toString()));
					// 进入应用首页
					TurboToast.showMsg(activity, "恭喜您注册成功！");
					Intent intent = new Intent();
					intent.setClass(activity, MainActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					activity.finish();
				} else {
					// 注册不成功，从info中读出错误信息
					String info = "";
					if (obj.has("info"))
						info = obj.getString("info");
					TurboToast.showMsg(activity, "状态码：" + status + " 错误信息："
							+ info);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} finally {
				TurboLoadingDialog.endLoading();
			}
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
					&& account.length() >= 4) {
				passwordText.setEnabled(true);
				// TODO: 再次优化时，可以添加账号的输入状态的提示
			} else {
				if (mailText.getVisibility() == View.VISIBLE) {
					userNameText.setError("用户名为4~18为数字和字母组合");
				} else {
					// userNameText.setError("账号输入有误");
				}
			}
		}

		/**
		 * 正在输入密码
		 * 
		 * @param psw
		 */
		private void pswTextChanged(String psw) {
			if (psw != null && !psw.contains(" ") && psw.length() >= 6) {
				if (pswAgainText.getVisibility() == View.VISIBLE) {
					pswAgainText.setEnabled(true);
				}
				loginButton.setEnabled(true);
			} else {
				if (mailText.getVisibility() == View.VISIBLE) {
					passwordText.setError("密码由6~18位数字字母组合");
				} else {
					// passwordText.setError("密码输入有误！");
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
			} else {
				pswAgainText.setError("输入的密码不同！");
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
