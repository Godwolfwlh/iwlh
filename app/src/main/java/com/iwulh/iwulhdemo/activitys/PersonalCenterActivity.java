package com.iwulh.iwulhdemo.activitys;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.bean.LoginBean;
import com.iwulh.iwulhdemo.utils.ACache;
import com.iwulh.iwulhdemo.utils.BaseActivity;

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener{

    private TextView actionBarBack, actionBarTitle, actionBarOther;
    private ImageView actionBarScan;
    private ImageView avatarImageView;
    private TextView userName;
    private TextView userVal;
    private LoginBean loginBean = new LoginBean();
    private ACache acache;
    private Intent intent;

    @Override
    public int intiLayout() {
        return R.layout.activity_personal_center;
    }

    @Override
    public void initView() {
        //设置ActionBar
        setActionBar(R.layout.actionbar_layout);
        actionBarBack = findById(R.id.actionbar_back);
        actionBarTitle = findById(R.id.actionbar_title);
        actionBarScan = findById(R.id.actionbar_scan);
        actionBarOther = findById(R.id.actionbar_other);

        actionBarTitle.setText(R.string.personal_center_string);
        actionBarScan.setVisibility(View.GONE);
        actionBarOther.setVisibility(View.VISIBLE);
        actionBarOther.setText(R.string.bar_text_album);

        actionBarBack.setOnClickListener(this);
        actionBarTitle.setOnClickListener(this);
        actionBarOther.setOnClickListener(this);

        avatarImageView = findById(R.id.iv_avatar);
        userName = findById(R.id.user_name);
        userVal = findById(R.id.user_val);
    }

    @Override
    public void initData() {
        acache = ACache.get(this);
        loginBean = (LoginBean) acache.getAsObject("LOGINBEAN_PAECELABLE");

        userName.setText(loginBean.getData().getUserName());
        userVal.setText(loginBean.getData().getUserPhone());
        Glide.with(this).load(R.mipmap.head)
                .into(avatarImageView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_back:
                finish();
                break;
            case R.id.actionbar_title:
                Toast.makeText(this, "你点击了标题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionbar_other:
                Toast.makeText(this, "你点击了相册", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
