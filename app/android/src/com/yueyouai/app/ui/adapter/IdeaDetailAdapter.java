package com.yueyouai.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yueyouai.app.data.IdeaDetailBean;

import java.util.List;

/**
 * 点子详细信息数据源
 * @author Ted
 */
public class IdeaDetailAdapter extends BaseAdapter{

	private LayoutInflater inflater;
	private Context context;
	private List<IdeaDetailBean> datas;
	
	public IdeaDetailAdapter(Context context, List<IdeaDetailBean> datas){
		this.context = context;
		this.datas = datas;
		this.inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parentView) {
		
		if(convertView == null){
			
		}else{
			
		}
		return convertView;
	}
	
	private static class ViewHolder{
	    
	} 

}
