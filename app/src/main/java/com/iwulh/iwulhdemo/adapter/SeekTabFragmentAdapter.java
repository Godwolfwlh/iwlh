package com.iwulh.iwulhdemo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.fragments.SeeKTabFragment;

import java.util.ArrayList;

/**
 * Created by Felix on 16/11/22.
 * author Felix
 */
public class SeekTabFragmentAdapter extends FragmentStatePagerAdapter {

    public ArrayList<Fragment> fragments;

    public Context mContext;

    private String[] titles;

    public SeekTabFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        initFragments();
    }

    public ArrayList<Fragment> getFragments() {
        return fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    private void initFragments() {
        titles = new String[]{
                mContext.getResources().getString(R.string.test_1),
                mContext.getResources().getString(R.string.test_2),
                mContext.getResources().getString(R.string.test_3),
                mContext.getResources().getString(R.string.test_4),
        };

        fragments = new ArrayList<>();
        for ( int i=0; i < titles.length; i++ ){
            Fragment fragment = SeeKTabFragment.newInstance();
            fragments.add(fragment);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
