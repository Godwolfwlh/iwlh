package com.iwulh.iwulhdemo.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.activitys.MainActivity;
import com.iwulh.iwulhdemo.adapter.MessageAdapter;
import com.iwulh.iwulhdemo.bean.Journalism;
import com.iwulh.iwulhdemo.bean.JournalismBean;
import com.iwulh.iwulhdemo.bean.JournalismNBean;
import com.iwulh.iwulhdemo.okhttp.CallBackUtil;
import com.iwulh.iwulhdemo.okhttp.OkhttpUtil;
import com.iwulh.iwulhdemo.utils.ACache;
import com.iwulh.iwulhdemo.utils.BaseFragment;
import com.iwulh.iwulhdemo.utils.Constants;
import com.iwulh.iwulhdemo.utils.NetUtil;
import com.iwulh.iwulhdemo.views.DividerItemDecoration;
import com.iwulh.iwulhdemo.views.LoadStatusView;
import com.iwulh.iwulhdemo.views.MyPtrHandler;
import com.iwulh.iwulhdemo.views.MyPtrRefresher;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import okhttp3.Call;

public class MessageFragment extends BaseFragment {

    private JournalismBean journalismBean = new JournalismBean();
    private JournalismNBean journalismNBean;
    private List<Journalism> journalismList = new ArrayList<>();

    private TextView actionBarBack2, actionBarTitle2, actionBarOther2;

    private RecyclerView mRecyclerView;
    private PtrFrameLayout ptrFrameLayout;
    private MessageAdapter messageAdapter;
    private LinearLayoutManager layoutManager;//RecyclerView管理器

    private LoadStatusView mLoadStatusView;
    private ACache acache;

    @Override
    protected int setLayout() {
        return R.layout.fragment_message;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            actionBarBack2 = mainActivity.findById(R.id.actionbar_back2);
            actionBarTitle2 = mainActivity.findById(R.id.actionbar_title2);
            actionBarOther2 = mainActivity.findById(R.id.actionbar_other2);

            actionBarBack2.setOnClickListener(this);
            actionBarTitle2.setOnClickListener(this);
            actionBarOther2.setOnClickListener(this);
        }
    }

    @Override
    protected void initView() {
        acache = ACache.get(getContext());
        journalismBean = (JournalismBean) acache.getAsObject("JOURNALISM_BEAN");

        mRecyclerView = fvbi(R.id.recycler_view_message);
        ptrFrameLayout = fvbi(R.id.ptr_classic_frame_message);

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
                        messageAdapter.notifyDataSetChanged();
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
                        messageAdapter.notifyDataSetChanged();
                        ptrFrameLayout.refreshComplete();
                        mRecyclerView.smoothScrollToPosition(journalismList.size() - 1);
                    }
                }, 1000);
            }
        });
    }

    @Override
    protected void initData() {
        journalismNBean = (JournalismNBean) acache.getAsObject("JOURNALISM_N_BEAN");
        if (journalismNBean != null) {
            journalismList = journalismNBean.getData();
            if (journalismBean.getData() != null){
                journalismBean.getData().addAll(journalismList);
            }else {
                journalismBean = (JournalismBean) journalismList;
            }
            acache.put("JOURNALISM_BEAN", journalismBean, 60 * 60 * 24 * 7);
            acache.remove("JOURNALISM_N_BEAN");
            showRecyclerView(journalismList);
        } else {
            if (journalismBean != null) {
                journalismList = journalismBean.getData();
                showRecyclerView(journalismList);
                mLoadStatusView.setHide();
            } else {
                if (NetUtil.isConnected(getContext())) {
                    //设置加载控件状态
                    mLoadStatusView.setLoading();
                    MessCallBackUtilAll MessCallBackUtilAll = new MessCallBackUtilAll();
                    OkhttpUtil.okHttpGet(Constants.JOURNALISM_ALL, MessCallBackUtilAll);
                } else {
                    mLoadStatusView.setNoNet();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_back2:
                Toast.makeText(getContext(), "返回", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionbar_title2:
                Toast.makeText(getContext(), "标题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionbar_other2:
                Toast.makeText(getContext(), "其他", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void showRecyclerView(List<Journalism> journalismList) {
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
//        layout.setStackFromEnd(true);//列表再底部开始展示，反转后由上面开始展示
        layoutManager.setReverseLayout(true);//列表翻转
        mRecyclerView.setLayoutManager(layoutManager);//设置RecyclerView管理器
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        //初始化适配器
        messageAdapter = new MessageAdapter(journalismBean.getData());
        //设置适配器
        mRecyclerView.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();
    }

    private class MessCallBackUtilAll extends CallBackUtil.CallBackString {
        @Override
        public void onFailure(Call call, Exception e) {
            Log.i("TAG", "onFailure：" + e);
            mLoadStatusView.setFailRefresh();
        }

        @Override
        public void onResponse(String response) {
            Log.i("TAG", "onResponse：" + response);
            Gson gson = new Gson();
            journalismBean = gson.fromJson(response, JournalismBean.class);
            acache.put("JOURNALISM_BEAN", journalismBean, 60 * 60 * 24 * 7);
            journalismList = new ArrayList<Journalism>();
            journalismList = journalismBean.getData();
            showRecyclerView(journalismList);
            mLoadStatusView.setHide();
        }
    }
}