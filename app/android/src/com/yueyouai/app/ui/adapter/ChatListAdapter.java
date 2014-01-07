package com.yueyouai.app.ui.adapter;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyouai.app.R;
import com.yueyouai.app.data.MessageBean;

public class ChatListAdapter extends BaseAdapter {

	private LinkedList<MessageBean> datas;
	private Context mContext;
	private LayoutInflater inflater;

	public ChatListAdapter(LinkedList<MessageBean> datas, Context context) {
		this.datas = datas;
		this.mContext = context;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		MessageBean bean = datas.get(position);
		
		if (convertView == null || (holder = (ViewHolder) convertView.getTag()).isSend != bean.isSend()) {
			holder = new ViewHolder();
			holder.isSend = bean.isSend();
			if(bean.isSend()){
				//发送的消息
				convertView = inflater
						.inflate(R.layout.chat_list_item_right, null);
				holder.photo = (ImageView) convertView
						.findViewById(R.id.chat_list_item_right_userPhotoImage);
				holder.text = (TextView) convertView
						.findViewById(R.id.chat_list_item_right_messageTextView);
			}else{
				//接收的消息
				convertView = inflater
						.inflate(R.layout.chat_list_item_left, null);
				holder.photo = (ImageView) convertView
						.findViewById(R.id.chat_list_item_left_userPhotoImage);
				holder.text = (TextView) convertView
						.findViewById(R.id.chat_list_item_left_messageTextView);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		initView(bean, holder);
		return convertView;
	}
	
	/**
	 * 处理数据
	 * @param bean
	 * @param holder
	 */
	private void initView(MessageBean bean,ViewHolder holder){
		// TODO:处理数据信息
		holder.text.setText(bean.getMsg());
	}
	
	/**
	 * 添加消息对象
	 * 
	 * @param bean
	 */
	public void addBean(MessageBean bean) {
		datas.addLast(bean);
	}

	private static class ViewHolder {
		ImageView photo;
		TextView text;
		boolean isSend;
	}

}
