package com.iwulh.iwulhdemo.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity{
    /***封装ActionBar对象*/
    private ActionBar supportActionBar;
    /***获取TAG的activity名称**/
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局
        setContentView(intiLayout());
        //初始化控件
        initView();
        //设置数据
        initData();
    }

    /**
     * 设置布局
     *
     * @return
     */
    public abstract int intiLayout();

    /**
     * 初始化布局
     */
    public abstract void initView();

    /**
     * 设置数据
     */
    public abstract void initData();

    public <T extends View> T findById(int id) {
        return (T) findViewById(id);
    }

    /**
     * 设置标题栏
     *
     * @param layout_actionbar
     */
    public void setActionBar(int layout_actionbar) {

        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = LayoutInflater.from(this).inflate(layout_actionbar, null);
        supportActionBar = getSupportActionBar();
//        supportActionBar.setIcon(R.mipmap.gender);//设置ActionBar的icon图标
        supportActionBar.setCustomView(mActionBarView, lp);
        supportActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayShowHomeEnabled(false);
        supportActionBar.setDisplayShowTitleEnabled(false);
        supportActionBar.setHomeButtonEnabled(false);//主键按钮能否可点击
        supportActionBar.setDisplayHomeAsUpEnabled(false);//显示返回图标

    }

}
