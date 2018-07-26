package com.iwulh.iwulhdemo.fragments;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.activitys.MainActivity;
import com.iwulh.iwulhdemo.adapter.SeekTabFragmentAdapter;
import com.iwulh.iwulhdemo.utils.BaseFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

public class SeekFragment extends BaseFragment {

    private TextView actionBarBack3, actionBarTitle3, actionBarOther3;
    //fragment的适配器
    private SeekTabFragmentAdapter seekTabFragmentAdapter;

    //viewpager
    private ViewPager mViewPager;

    //AppBarLayout
    private AppBarLayout mAppBarLayout;

    //顶部HeaderLayout
    private LinearLayout headerLayout;

    private SmartTabLayout mTabs;

    //是否隐藏了头部
    private boolean isHideHeaderLayout = false;

    @Override
    protected int setLayout() {
        return R.layout.fragment_seek;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            actionBarBack3 = mainActivity.findById(R.id.actionbar_back3);
            actionBarTitle3 = mainActivity.findById(R.id.actionbar_title3);
            actionBarOther3 = mainActivity.findById(R.id.actionbar_other3);

            actionBarBack3.setOnClickListener(this);
            actionBarTitle3.setOnClickListener(this);
            actionBarOther3.setOnClickListener(this);
        }
    }

    @Override
    protected void initView() {
        mTabs = fvbi(R.id.tabs);
        seekTabFragmentAdapter = new SeekTabFragmentAdapter(getFragmentManager(), getContext());
        mViewPager = fvbi(R.id.viewpager);
        mViewPager.setAdapter(seekTabFragmentAdapter);
        mTabs.setViewPager(mViewPager);

//        mViewPager.setOffscreenPageLimit(mainTabFragmentAdapter.getCount());
        headerLayout = fvbi(R.id.ll_header_layout);
        initAppBarLayout();
    }

    @Override
    protected void initData() {
    }

    // 初始化AppBarLayout
    private void initAppBarLayout() {
        LayoutTransition mTransition = new LayoutTransition();
        /**
         * 添加View时过渡动画效果
         */
        ObjectAnimator addAnimator = ObjectAnimator.ofFloat(null, "translationY", 0, 1.f).
                setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
        mTransition.setAnimator(LayoutTransition.APPEARING, addAnimator);

        //header layout height
        final int headerHeight = getResources().getDimensionPixelOffset(R.dimen.header_height);
        mAppBarLayout = fvbi(R.id.appbar);
        mAppBarLayout.setLayoutTransition(mTransition);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                verticalOffset = Math.abs(verticalOffset);
                if (verticalOffset >= headerHeight) {
                    isHideHeaderLayout = true;
                    //当偏移量超过顶部layout的高度时，我们认为他已经完全移动出屏幕了
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) headerLayout.getLayoutParams();
                            mParams.setScrollFlags(0);
                            headerLayout.setLayoutParams(mParams);
                            headerLayout.setVisibility(View.GONE);
                        }
                    }, 100);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_back3:
                if (isHideHeaderLayout) {
                    isHideHeaderLayout = false;
                    ((SeeKTabFragment) seekTabFragmentAdapter.getFragments().get(0)).getRvList().scrollToPosition(0);
                    headerLayout.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            AppBarLayout.LayoutParams mParams = (AppBarLayout.LayoutParams) headerLayout.getLayoutParams();
                            mParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                                    AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                            headerLayout.setLayoutParams(mParams);
                        }
                    }, 300);
                } else {
                    Toast.makeText(getContext(), "返回", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.actionbar_title3:
                Toast.makeText(getContext(), "标题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionbar_other3:
                Toast.makeText(getContext(), "其他", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}