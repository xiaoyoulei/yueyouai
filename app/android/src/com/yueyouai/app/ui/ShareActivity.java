package com.yueyouai.app.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tedyin.turbo.R;
import com.turbo.app.TurboBaseActivity;
import com.turbo.common.CommonUtils;
import com.turbo.widget.ShareUtil;

public class ShareActivity extends TurboBaseActivity {

	private Button sharedSinaWeiBoBtn;
	private Button sharedTecentWeiBoBtn;
	private Button sharedWeiXinBtn;
	private Button sharedQQZoneBtn;
	private Button sharedShortMsg;
	private Button sharedListBtn;
	
	private String shareContent = "要分享的内容----测试"; // 要分享的内容
	private String shareUrl = "要分享的Url----测试"; // 要分享的Url
	private String sharePicPath = "要分享的图片路径----测试";// 要分享的图片的Url
	private Context mContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_activity);
		mContext = this;
		findView();
		addListener();
		CommonUtils.getAllAppPacakges(this);
	}
	
	private void findView() {
		// TODO Auto-generated method stub
		sharedSinaWeiBoBtn = (Button) findViewById(R.id.sharedSinaWeiBoBtn);
		sharedTecentWeiBoBtn = (Button) findViewById(R.id.sharedTecentWeiBoBtn);
		sharedQQZoneBtn = (Button) findViewById(R.id.sharedQQZoneBtn);
		sharedShortMsg =  (Button) findViewById(R.id.sharedShortMsg);
		sharedWeiXinBtn =  (Button) findViewById(R.id.sharedWeiXinBtn);
		sharedListBtn =  (Button) findViewById(R.id.sharedListBtn);
	}

	private void addListener() {

		// 新浪微博
		sharedSinaWeiBoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtil.sendSinaWeibo(mContext, shareContent, shareUrl);
			}
		});
		
		// 腾讯微博
		sharedTecentWeiBoBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtil.sendTecentWeibo(mContext, shareContent, shareUrl);
				finish();
			}
		});
		
		//短息分享
		sharedShortMsg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareUtil.sendSMS_Share(mContext, shareContent + shareUrl);
			}
		});

		// QQ空间
		sharedQQZoneBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtil.sendQZone(mContext, shareContent, shareUrl);
//				Toast.makeText(mContext, "分享到QQ空间", Toast.LENGTH_SHORT).show();
			}
		});
		
		// 微信
		sharedWeiXinBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ShareUtil.sendWeiXin(mContext, shareContent, shareUrl);
			}
		});
		
		//分享列表
		sharedListBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				ShareUtil.sendShare(mContext, shareContent,"file path");
			}
		});
	}
}
