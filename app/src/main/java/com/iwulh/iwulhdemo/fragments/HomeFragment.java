package com.iwulh.iwulhdemo.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.activitys.LoginActivity;
import com.iwulh.iwulhdemo.activitys.MainActivity;
import com.iwulh.iwulhdemo.activitys.PersonalCenterActivity;
import com.iwulh.iwulhdemo.adapter.MyListViewAdapter;
import com.iwulh.iwulhdemo.bean.Data;
import com.iwulh.iwulhdemo.bean.LoginBean;
import com.iwulh.iwulhdemo.bean.UserBean;
import com.iwulh.iwulhdemo.okhttp.CallBackUtil;
import com.iwulh.iwulhdemo.okhttp.OkhttpUtil;
import com.iwulh.iwulhdemo.utils.ACache;
import com.iwulh.iwulhdemo.utils.BaseFragment;
import com.iwulh.iwulhdemo.utils.Constants;
import com.iwulh.iwulhdemo.utils.NetUtil;
import com.iwulh.iwulhdemo.utils.PermissionHelper;
import com.iwulh.iwulhdemo.views.DividerItemDecoration;
import com.iwulh.iwulhdemo.views.LoadStatusView;
import com.iwulh.iwulhdemo.views.MyPtrHandler;
import com.iwulh.iwulhdemo.views.MyPtrRefresher;
import com.iwulh.iwulhdemo.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

public class HomeFragment extends BaseFragment {

    private UserBean userBean = new UserBean();
    private List<Data> dataList = new ArrayList<>();
    private LoginBean loginBean = new LoginBean();

    private TextView actionBarBack, actionBarTitle, actionBarOther;
    private ImageView actionBarScan, actionBarHeadPortrait;

    private RecyclerView mRecyclerView;
    private PtrFrameLayout ptrFrameLayout;
    private MyListViewAdapter mAdapter;
    private LinearLayoutManager layoutManager;//RecyclerView管理器

    private LoadStatusView mLoadStatusView;
    private ACache acache;
    private Intent intent;
    private boolean grant;

    @Override
    protected int setLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            actionBarBack = mainActivity.findById(R.id.actionbar_back);
            actionBarHeadPortrait = mainActivity.findById(R.id.actionbar_head_portrait);
            actionBarTitle = mainActivity.findById(R.id.actionbar_title);
            actionBarScan = mainActivity.findById(R.id.actionbar_scan);
            actionBarOther = mainActivity.findById(R.id.actionbar_other);

            actionBarBack.setVisibility(View.GONE);
            actionBarHeadPortrait.setVisibility(View.VISIBLE);
            actionBarTitle.setVisibility(View.VISIBLE);
            actionBarTitle.setText(R.string.app_name);
            actionBarScan.setVisibility(View.VISIBLE);
            actionBarOther.setVisibility(View.GONE);

            actionBarHeadPortrait.setOnClickListener(this);
            actionBarTitle.setOnClickListener(this);
            actionBarScan.setOnClickListener(this);
        }
    }

    @Override
    protected void initView() {
        acache = ACache.get(getContext());
        loginBean = (LoginBean) acache.getAsObject("LOGINBEAN_PAECELABLE");
        userBean = (UserBean) acache.getAsObject("HOME_DATA_LIST");

        mRecyclerView = fvbi(R.id.recycler_view);
        ptrFrameLayout = fvbi(R.id.ptr_classic_frame_layout);

        mLoadStatusView = fvbi(R.id.lsv_load_status);
        mLoadStatusView.setOnRefreshListener(new LoadStatusView.OnRefreshListener() {
            @Override
            public void onRefreshListener() {
                //重新加载操作在这里
                initData();
            }
        });

        ptrFrameLayout.setResistance(1.7f);//设置下拉的阻尼系数，值越大感觉越难下拉
        ptrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);//设置超过头部的多少时，释放可以执行刷新操作
        ptrFrameLayout.setDurationToClose(200);//设置下拉回弹的时间
        ptrFrameLayout.setDurationToCloseHeader(1000);//设刷新完成，头部回弹时间，注意和前一个进行区别
        ptrFrameLayout.setPullToRefresh(false);//设置下拉过程中执行刷新，我们一般设置为false
        ptrFrameLayout.setKeepHeaderWhenRefresh(true);//设置刷新的时候是否保持头部

        // 为布局设置头部和底部布局，使用自定义头部，注释掉下一行会使用默认头部
        ptrFrameLayout.setHeaderView(new MyPtrRefresher(getContext()));
        ptrFrameLayout.addPtrUIHandler(new MyPtrHandler(getContext(), ptrFrameLayout));

        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {// 上拉加载的回调方法
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        dataList.add("New Bottom List Item");
                        mAdapter.notifyDataSetChanged();
                        ptrFrameLayout.refreshComplete();
                        mRecyclerView.smoothScrollToPosition(0);
                    }
                }, 1000);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {// 下拉刷新的回调方法
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        dataList.add(0, "New Top List Item");
                        mAdapter.notifyDataSetChanged();
                        ptrFrameLayout.refreshComplete();
                        mRecyclerView.smoothScrollToPosition(dataList.size() - 1);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void initData() {
        if (userBean == null) {
            if (NetUtil.isConnected(getContext())) {
                //设置加载控件状态
                mLoadStatusView.setLoading();
                OkhttpUtil.okHttpGet(Constants.USER_GETBYNAME, new CallBackUtil.CallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {
                        Log.i("TAG", "onFailure：" + e);
                        mLoadStatusView.setFailRefresh();
                    }

                    @Override
                    public void onResponse(String response) {
                        Log.i("TAG", "onResponse：" + response);
                        Gson gson = new Gson();
                        userBean = gson.fromJson(response, UserBean.class);
                        acache.put("HOME_DATA_LIST", userBean, 60 * 60 * 24 * 7);
                        dataList = new ArrayList<Data>();
                        dataList = userBean.getData();
                        showRecyclerView(dataList);
                        mLoadStatusView.setHide();
                    }
                });
            } else {
                mLoadStatusView.setNoNet();
            }
        } else {
            dataList = userBean.getData();
            showRecyclerView(dataList);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_head_portrait:
                if (loginBean == null) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), PersonalCenterActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.actionbar_title:

                grant = PermissionHelper.isPermissionGranted(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (grant == true) {
                    Toast.makeText(getContext(), "写外部存储权限已允许", Toast.LENGTH_SHORT).show();
                } else {
                    PermissionHelper.runOnPermissionGranted(getActivity(), new Runnable() {
                        @Override
                        public void run() {
                            // 权限通过
                            Toast.makeText(getContext(), "已允许", Toast.LENGTH_SHORT).show();
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            // 权限不通过
                            Toast.makeText(getContext(), "未允许", Toast.LENGTH_SHORT).show();
                        }
                    }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }
                break;
            case R.id.actionbar_scan:
                grant = PermissionHelper.isPermissionGranted(getActivity(), Manifest.permission.CAMERA);
                if (grant == true) {
                    // 二维码扫码
                    intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, Constants.REQ_QR_CODE);
                } else {
                    PermissionHelper.runOnPermissionGranted(getActivity(), new Runnable() {
                        @Override
                        public void run() {
                            // 权限通过
                            Toast.makeText(getContext(), "已允许", Toast.LENGTH_SHORT).show();
                        }
                    }, new Runnable() {
                        @Override
                        public void run() {
                            // 权限不通过
                            Toast.makeText(getContext(), "未允许", Toast.LENGTH_SHORT).show();
                        }
                    }, Manifest.permission.CAMERA);
                }
                break;
        }
    }

    public void showRecyclerView(List<Data> dataList) {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layoutManager.setReverseLayout(true);//列表翻转
        mRecyclerView.setLayoutManager(layoutManager);//设置RecyclerView管理器
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //初始化适配器
        mAdapter = new MyListViewAdapter(dataList);
        //设置适配器
        mRecyclerView.setAdapter(mAdapter);
    }
}
