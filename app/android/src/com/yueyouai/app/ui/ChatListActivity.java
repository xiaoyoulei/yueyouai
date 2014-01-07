package com.yueyouai.app.ui;

import java.util.LinkedList;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.turbo.app.TurboBaseActivity;
import com.yueyouai.app.R;
import com.yueyouai.app.data.MessageBean;
import com.yueyouai.app.ui.adapter.ChatListAdapter;

public class ChatListActivity extends TurboBaseActivity implements
		OnClickListener {

	private ListView listView;
	private ChatListAdapter adapter;

	private Button sendButton;
	private Button moreButton;
	private Button voiceButton;
	private EditText editText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_list_activity);
		initView();
		initList();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.chat_list_activity_listView);
		sendButton = (Button) findViewById(R.id.chat_list_activity_bottom_sendButton);
		moreButton = (Button) findViewById(R.id.chat_list_activity_bottom_more_button);
		voiceButton = (Button) findViewById(R.id.chat_list_activity_bottom_voiceButton);
		editText = (EditText) findViewById(R.id.chat_list_activity_bottom_editText);

		sendButton.setOnClickListener(this);
		moreButton.setOnClickListener(this);
		voiceButton.setOnClickListener(this);

		editText.addTextChangedListener(new MTextWatcher());
	}

	private void initList() {
		LinkedList<MessageBean> datas = new LinkedList<MessageBean>();
		adapter = new ChatListAdapter(datas, this);
		listView.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.chat_list_activity_bottom_sendButton:
			sendMsg();
			break;
		case R.id.chat_list_activity_bottom_more_button:
			more();
			break;
		case R.id.chat_list_activity_bottom_voiceButton:
			changeVoice();
			break;
		default:
			break;
		}
	}

	private void sendMsg() {
		// TODO Auto-generated method stub
		MessageBean sendBean = new MessageBean(
				MessageBean.MessageType.MSG_TYPE_TEXT, editText.getText()
						.toString(), true);
		adapter.addBean(sendBean);
		adapter.notifyDataSetChanged();
		editText.setText("");
		listView.setSelection(adapter.getCount() - 1);			//滚动到当前的消息
	}

	private void more() {
		// TODO Auto-generated method stub
		MessageBean receiveBean = new MessageBean(
				MessageBean.MessageType.MSG_TYPE_TEXT, editText.getText()
						.toString(), false);
		adapter.addBean(receiveBean);
		adapter.notifyDataSetChanged();
		editText.setText("");
		listView.setSelection(adapter.getCount() - 1);			//滚动到当前的消息
	}

	private void changeVoice() {
		// TODO Auto-generated method stub

	}

	private class MTextWatcher implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			if (s.length() > 0) {
				voiceButton.setVisibility(View.INVISIBLE);
				sendButton.setVisibility(View.VISIBLE);
			} else {
				voiceButton.setVisibility(View.VISIBLE);
				sendButton.setVisibility(View.INVISIBLE);
			}
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}
	}
}
