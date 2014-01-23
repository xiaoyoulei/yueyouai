package com.yueyouai.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.turbo.net.VolleyNetHelper;
import com.turbo.volley.toolbox.NetworkImageView;
import com.yueyouai.app.R;
import com.yueyouai.app.data.AllDataBean;

import java.util.List;

/**
 * 评论列表数据源
 * @author Ted
 */
public class CommentListAdapter extends BaseAdapter{
    
    private List<AllDataBean> datas;
    private LayoutInflater inflater;
    private VolleyNetHelper helper;
    private Context context;
    
    public CommentListAdapter(Context context,VolleyNetHelper helper, List<AllDataBean> datas){
        this.context = context;
        this.datas = datas;
        this.helper = helper;
        this.inflater = LayoutInflater.from(context);
    }
    
    @Override
    public int getCount() {
        return datas.size();
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.comment_list_view_item, null);
            holder = new ViewHolder();
            holder.userPhoto = (NetworkImageView) convertView.findViewById(R.id.comment_list_item_user_photo);
            holder.commentTextView = (TextView) convertView.findViewById(R.id.comment_list_item_textView);
            holder.commentBtn = (Button) convertView.findViewById(R.id.comment_list_item_commentBtn);
            holder.praiseBtn = (Button) convertView.findViewById(R.id.comment_list_item_praiseBtn);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        //初始化数据
        AllDataBean bean = datas.get(position);
        String[] pic = bean.getPic();
        //TODO:此处的头像信息有误
        holder.userPhoto.setImageUrl(pic[0], helper.getImageLoader(context));
        holder.commentTextView.setText(bean.getDesc());
        holder.dateTextView.setText("2014-01-22");
        holder.praiseBtn.setOnClickListener(new MPraisListener());
        holder.commentBtn.setOnClickListener(new MCommentListener());
        return convertView;
    }
    
    private static class ViewHolder{
        NetworkImageView userPhoto;
        TextView commentTextView;
        TextView dateTextView;
        Button praiseBtn;
        Button commentBtn;
    }
    
    private class MCommentListener implements OnClickListener{
        @Override
        public void onClick(View v) {
//            TurboStringRequest request = new TurboStringRequest(url, entity, headers, callBack)
        }
    }
    
    private class MPraisListener implements OnClickListener{
        @Override
        public void onClick(View v) {
            
        }
    }
}
