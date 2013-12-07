package com.yueyouai.app.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.turbo.volley.toolbox.NetworkImageView;
import com.turbo.net.VolleyNetHelper;
import com.yueyouai.app.R;
import com.yueyouai.app.data.HotelMessageBean;

public class TestMAdapter extends BaseAdapter{

	private List<HotelMessageBean> data;
	private LayoutInflater li;
	private VolleyNetHelper helper;
	private Context mContext;

	public TestMAdapter(Context context, List<HotelMessageBean> data,VolleyNetHelper helper) {
		this.data = data;
		this.li = LayoutInflater.from(context);
		this.helper = helper;
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		return data == null ? 0 : data.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}	

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = li.inflate(R.layout.activity_main_list_item, null);
			holder = new ViewHolder();
			holder.nameText = (TextView) convertView
					.findViewById(R.id.main_item_title_text);
			holder.addressText = (TextView) convertView
					.findViewById(R.id.main_item_introduce_text);
			holder.image = (NetworkImageView) convertView
					.findViewById(R.id.main_item_pic_image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		HotelMessageBean bean = data.get(position);
		holder.addressText.setText(bean.getAddress());
		holder.nameText.setText(bean.getName());
		String imageUrl = data.get(position).getPhotoUrl();
		holder.image.setImageUrl(imageUrl,helper.getImageLoader(mContext));
		holder.image.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.dock_menu_fadein));
		return convertView;
	}
	
	static class ViewHolder {
		TextView nameText;
		TextView addressText;
		NetworkImageView image;
	}

}
