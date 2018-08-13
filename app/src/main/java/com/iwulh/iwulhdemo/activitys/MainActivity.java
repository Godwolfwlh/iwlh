package com.iwulh.iwulhdemo.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.ShapeBadgeItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.google.gson.Gson;
import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.bean.JournalismNBean;
import com.iwulh.iwulhdemo.fragments.HomeFragment;
import com.iwulh.iwulhdemo.fragments.MessageFragment;
import com.iwulh.iwulhdemo.fragments.SeekFragment;
import com.iwulh.iwulhdemo.fragments.SetupFragment;
import com.iwulh.iwulhdemo.okhttp.CallBackUtil;
import com.iwulh.iwulhdemo.okhttp.OkhttpUtil;
import com.iwulh.iwulhdemo.utils.ACache;
import com.iwulh.iwulhdemo.utils.BaseActivity;
import com.iwulh.iwulhdemo.utils.Constants;

import okhttp3.Call;

public class MainActivity extends BaseActivity {

    private TextView actionBarBack, actionBarBack2, actionBarBack3, actionBarBack4;
    private TextView actionBarTitle, actionBarTitle2, actionBarTitle3, actionBarTitle4;
    private TextView actionBarOther, actionBarOther2, actionBarOther3, actionBarOther4;
    private ImageView actionBarScan, actionBarScan2, actionBarScan3, actionBarScan4;
    private ImageView actionBarHeadPortrait, actionBarHeadPortrait2, actionBarHeadPortrait3, actionBarHeadPortrait4;

    private BottomNavigationBar bottomNavigationBar;
    private TextBadgeItem mTextBadgeItem;
    private ShapeBadgeItem mShapeBadgeItem;

    private JournalismNBean journalismNBean = new JournalismNBean();

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private SeekFragment seekFragment;
    private SetupFragment setupFragment;
    private Fragment[] mFragments;
    private FragmentTransaction transaction;

    private int index = 0;//点击的fragment的下标
    private int currentTabIndex = 0;//当前的fragment的下标
    private ACache acache;

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        acache = ACache.get(this);
        setActionBar(R.layout.actionbar_layout);
        //设置ActionBar
        actionBarBack = findById(R.id.actionbar_back);
        actionBarBack2 = findById(R.id.actionbar_back2);
        actionBarBack3 = findById(R.id.actionbar_back3);
        actionBarBack4 = findById(R.id.actionbar_back4);
        actionBarHeadPortrait = findById(R.id.actionbar_head_portrait);
        actionBarHeadPortrait2 = findById(R.id.actionbar_head_portrait2);
        actionBarHeadPortrait3 = findById(R.id.actionbar_head_portrait3);
        actionBarHeadPortrait4 = findById(R.id.actionbar_head_portrait4);
        actionBarTitle = findById(R.id.actionbar_title);
        actionBarTitle2 = findById(R.id.actionbar_title2);
        actionBarTitle3 = findById(R.id.actionbar_title3);
        actionBarTitle4 = findById(R.id.actionbar_title4);
        actionBarScan = findById(R.id.actionbar_scan);
        actionBarScan2 = findById(R.id.actionbar_scan2);
        actionBarScan3 = findById(R.id.actionbar_scan3);
        actionBarScan4 = findById(R.id.actionbar_scan4);
        actionBarOther = findById(R.id.actionbar_other);
        actionBarOther2 = findById(R.id.actionbar_other2);
        actionBarOther3 = findById(R.id.actionbar_other3);
        actionBarOther4 = findById(R.id.actionbar_other4);

        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        seekFragment = new SeekFragment();
        setupFragment = new SetupFragment();
        mFragments = new Fragment[]{homeFragment, messageFragment, seekFragment, setupFragment};
        bottomNavigationBar = findById(R.id.bottom_navigation_bar);
        initBottomNavigationBar();
    }

    @Override
    public void initData() {
        MessCallBackUtil CallBackUtil = new MessCallBackUtil();
        OkhttpUtil.okHttpGet(Constants.JOURNALISM_NEW, CallBackUtil);
        Log.i("MainLog",String.valueOf(journalismNBean.getCount()));
    }

    private void initBottomNavigationBar() {

        mTextBadgeItem = new TextBadgeItem()
                .setBorderWidth(4)
                .setBackgroundColorResource(R.color.colorAccent)
                .setAnimationDuration(200)
                .setText(String.valueOf(journalismNBean.getCount()))
                .setHideOnSelect(false);

        mShapeBadgeItem = new ShapeBadgeItem()
                .setShapeColorResource(R.color.colorPrimary)
                .setGravity(Gravity.TOP | Gravity.END)
                .setHideOnSelect(false);

        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

        bottomNavigationBar //值得一提，模式跟背景的设置都要在添加tab前面，不然不会有效果。
                .setActiveColor(R.color.colorOrange)//选中颜色 图标和文字
                .setInActiveColor("#8e8e8e");//默认未选择颜色;
//                .setBarBackgroundColor(R.color.Indigo_colorPrimaryDark);//默认背景色

            bottomNavigationBar
                    .addItem(new BottomNavigationItem(R.mipmap.home_fill, R.string.main_tab_home_string)
                            .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.home_light)))
                    .addItem(new BottomNavigationItem(R.mipmap.community_fill_light, R.string.main_tab_message_string)
                            .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.community_light))
                            .setBadgeItem(mTextBadgeItem))
                    .addItem(new BottomNavigationItem(R.mipmap.attention_fill, R.string.main_tab_search_string)
                            .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.attention)))
                    .addItem(new BottomNavigationItem(R.mipmap.location_fill, R.string.main_tab_setup_string)
                            .setInactiveIcon(ContextCompat.getDrawable(MainActivity.this, R.mipmap.location)))
                    .setFirstSelectedPosition(index)//设置默认选择的按钮
                    .initialise();//所有的设置需在调用该方法前完成

        //默认事务
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_content, mFragments[index])
                .show(mFragments[index])
                .commit();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                //选择的选项卡
                switch (position) {
                    case 0:
                        index = 0;
                        actionBarBack.setVisibility(View.GONE);
                        actionBarBack2.setVisibility(View.GONE);
                        actionBarBack3.setVisibility(View.GONE);
                        actionBarBack4.setVisibility(View.GONE);
                        actionBarHeadPortrait.setVisibility(View.VISIBLE);
                        actionBarHeadPortrait2.setVisibility(View.GONE);
                        actionBarHeadPortrait3.setVisibility(View.GONE);
                        actionBarHeadPortrait4.setVisibility(View.GONE);
                        actionBarTitle.setVisibility(View.VISIBLE);
                        actionBarTitle.setText(R.string.app_name);
                        actionBarTitle2.setVisibility(View.GONE);
                        actionBarTitle3.setVisibility(View.GONE);
                        actionBarTitle4.setVisibility(View.GONE);
                        actionBarScan.setVisibility(View.VISIBLE);
                        actionBarScan2.setVisibility(View.GONE);
                        actionBarScan3.setVisibility(View.GONE);
                        actionBarScan4.setVisibility(View.GONE);
                        actionBarOther.setVisibility(View.GONE);
                        actionBarOther2.setVisibility(View.GONE);
                        actionBarOther3.setVisibility(View.GONE);
                        actionBarOther4.setVisibility(View.GONE);
                        break;
                    case 1:
                        index = 1;
                        actionBarBack.setVisibility(View.GONE);
                        actionBarBack2.setVisibility(View.VISIBLE);
                        actionBarBack3.setVisibility(View.GONE);
                        actionBarBack4.setVisibility(View.GONE);
                        actionBarHeadPortrait.setVisibility(View.GONE);
                        actionBarHeadPortrait2.setVisibility(View.GONE);
                        actionBarHeadPortrait3.setVisibility(View.GONE);
                        actionBarHeadPortrait4.setVisibility(View.GONE);
                        actionBarTitle.setVisibility(View.GONE);
                        actionBarTitle2.setVisibility(View.VISIBLE);
                        actionBarTitle2.setText(R.string.main_tab_message_string);
                        actionBarTitle3.setVisibility(View.GONE);
                        actionBarTitle4.setVisibility(View.GONE);
                        actionBarScan.setVisibility(View.GONE);
                        actionBarScan2.setVisibility(View.GONE);
                        actionBarScan3.setVisibility(View.GONE);
                        actionBarScan4.setVisibility(View.GONE);
                        actionBarOther.setVisibility(View.GONE);
                        actionBarOther2.setVisibility(View.VISIBLE);
                        actionBarOther3.setVisibility(View.GONE);
                        actionBarOther4.setVisibility(View.GONE);
                        break;
                    case 2:
                        index = 2;
                        actionBarBack.setVisibility(View.GONE);
                        actionBarBack2.setVisibility(View.GONE);
                        actionBarBack3.setVisibility(View.VISIBLE);
                        actionBarBack4.setVisibility(View.GONE);
                        actionBarHeadPortrait.setVisibility(View.GONE);
                        actionBarHeadPortrait2.setVisibility(View.GONE);
                        actionBarHeadPortrait3.setVisibility(View.GONE);
                        actionBarHeadPortrait4.setVisibility(View.GONE);
                        actionBarTitle.setVisibility(View.GONE);
                        actionBarTitle2.setVisibility(View.GONE);
                        actionBarTitle3.setVisibility(View.VISIBLE);
                        actionBarTitle3.setText(R.string.main_tab_search_string);
                        actionBarTitle4.setVisibility(View.GONE);
                        actionBarScan.setVisibility(View.GONE);
                        actionBarScan2.setVisibility(View.GONE);
                        actionBarScan3.setVisibility(View.GONE);
                        actionBarScan4.setVisibility(View.GONE);
                        actionBarOther.setVisibility(View.GONE);
                        actionBarOther2.setVisibility(View.GONE);
                        actionBarOther3.setVisibility(View.VISIBLE);
                        actionBarOther4.setVisibility(View.GONE);
                        break;
                    case 3:
                        index = 3;
                        actionBarBack.setVisibility(View.GONE);
                        actionBarBack2.setVisibility(View.GONE);
                        actionBarBack3.setVisibility(View.GONE);
                        actionBarBack4.setVisibility(View.VISIBLE);
                        actionBarHeadPortrait.setVisibility(View.GONE);
                        actionBarHeadPortrait2.setVisibility(View.GONE);
                        actionBarHeadPortrait3.setVisibility(View.GONE);
                        actionBarHeadPortrait4.setVisibility(View.GONE);
                        actionBarTitle.setVisibility(View.GONE);
                        actionBarTitle2.setVisibility(View.GONE);
                        actionBarTitle3.setVisibility(View.GONE);
                        actionBarTitle4.setVisibility(View.VISIBLE);
                        actionBarTitle4.setText(R.string.main_tab_setup_string);
                        actionBarScan.setVisibility(View.GONE);
                        actionBarScan2.setVisibility(View.GONE);
                        actionBarScan3.setVisibility(View.GONE);
                        actionBarScan4.setVisibility(View.GONE);
                        actionBarOther.setVisibility(View.GONE);
                        actionBarOther2.setVisibility(View.GONE);
                        actionBarOther3.setVisibility(View.GONE);
                        actionBarOther4.setVisibility(View.VISIBLE);
                        break;
                }
                if (currentTabIndex != index) {
                    transaction = getSupportFragmentManager().beginTransaction();//得到事务
                    transaction.hide(mFragments[currentTabIndex]);//当前fragment事务隐藏
                    if (!mFragments[index].isAdded()) {
                        transaction.add(R.id.fragment_content, mFragments[index]);
                    }
                    transaction.show(mFragments[index]).commitAllowingStateLoss();
                }
                currentTabIndex = index;
            }

            @Override
            public void onTabUnselected(int position) {
                //未选择的选项卡
                /*if (index == 2) {
                    bottomNavigationBar.selectTab(position);
                }*/
            }

            @Override
            public void onTabReselected(int position) {
                //重新选择的选项卡
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constants.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constants.INTENT_EXTRA_KEY_QR_SCAN);
            //将扫描出的信息显示出来
            Toast.makeText(this, "结果：" + scanResult, Toast.LENGTH_LONG).show();
        }
    }

    private class MessCallBackUtil extends CallBackUtil.CallBackString {
        @Override
        public void onFailure(Call call, Exception e) {
            Log.i("TAG", "onFailure：" + e);
            journalismNBean = new JournalismNBean();
        }

        @Override
        public void onResponse(String response) {
            Log.i("TAG", "onResponse：" + response);
            if (!response.equals("")) {
                Gson gson = new Gson();
                journalismNBean = gson.fromJson(response, JournalismNBean.class);
                acache.put("JOURNALISM_N_BEAN", journalismNBean, 60 * 60 * 24 * 7);
            }
        }
    }

}