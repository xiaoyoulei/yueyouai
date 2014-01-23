
package com.yueyouai.app.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.turbo.app.TurboBaseActivity;
import com.yueyouai.app.R;

import java.util.List;

public class IdeaDetailActivity extends TurboBaseActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_data_detail_view);
        init();
    }

    private void init() {
        viewPager = (ViewPager) findViewById(R.id.all_data_view_viewPager);

    }

    private class MViewPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;
        
        public MViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        
        public MViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }
        
        @Override
        public int getCount() {
            return fragments.size();
        }

    }
}
