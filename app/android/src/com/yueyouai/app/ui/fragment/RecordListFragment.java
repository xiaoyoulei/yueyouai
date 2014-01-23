package com.yueyouai.app.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yueyouai.app.App;
import com.yueyouai.app.R;
import com.yueyouai.app.data.AllDataBean;
import com.yueyouai.app.data.Constant;
import com.yueyouai.app.ui.adapter.CommentListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录列表Fragment
 * @author Ted
 *
 */
public class RecordListFragment extends Fragment{

    
    private ListView listView;
    private List<AllDataBean> datas;
    
    @SuppressWarnings("unchecked")
    @Override
    public void setArguments(Bundle bundle) {
        initDatas((List<AllDataBean>) bundle.getSerializable("datas"));
    }
    
    private void initDatas(List<AllDataBean> allDatas){
        datas = new ArrayList<AllDataBean>();
        for(AllDataBean bean: allDatas){
            if(bean.getType() == Constant.DATA_TYPE_RECORD){
                datas.add(bean);
            }
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_listview, null);
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        listView = (ListView) getActivity().findViewById(R.id.fragment_record_listView);
        listView.setOnItemClickListener(new MItemClickListener());
        CommentListAdapter adapter = new CommentListAdapter(getActivity(), App.getNetHellper(), datas);
        listView.setAdapter(adapter);
    }
    
    /***************************************************
     *                 Listener 监听器
     ***************************************************/
    
    /**
     * 列表项点击监听
     * @author Ted
     */
    private class MItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            
        }
    }
    
}
