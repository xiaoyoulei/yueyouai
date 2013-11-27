package com.yueyouai.app.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueyouai.app.R;
import com.yueyouai.app.data.DoMainBean;

/**
 * 首页数据适配器
 * @author Ted
 *
 */
public class DoMainAdapter extends BaseAdapter{

	private List<DoMainBean> datas;
	private LayoutInflater inflater;
	public DoMainAdapter(Context context,List<DoMainBean> datas){
		this.datas = datas;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.activity_main_list_item, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id.main_item_pic_image);
			holder.titleText = (TextView) convertView.findViewById(R.id.main_item_title_text);
			holder.introduceText = (TextView) convertView.findViewById(R.id.main_item_introduce_text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		// TODO 接下来根据不同的数据对控件进行不同的操作
		
		return convertView;
	}
	
	static class ViewHolder{
		ImageView image;
		TextView titleText;
		TextView introduceText;
	}

}
