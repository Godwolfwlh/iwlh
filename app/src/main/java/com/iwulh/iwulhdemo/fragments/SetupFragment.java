package com.iwulh.iwulhdemo.fragments;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.activitys.MainActivity;
import com.iwulh.iwulhdemo.adapter.TopRecyclerViewAdapter;
import com.iwulh.iwulhdemo.utils.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class SetupFragment extends BaseFragment {

    private RecyclerView tRecyclerView;

    private List<Integer> typeList = new ArrayList<>();

    private TextView actionBarBack4, actionBarTitle4, actionBarOther4;

    @Override
    protected int setLayout() {
        return R.layout.fragment_setup;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            actionBarBack4 = mainActivity.findById(R.id.actionbar_back4);
            actionBarTitle4 = mainActivity.findById(R.id.actionbar_title4);
            actionBarOther4 = mainActivity.findById(R.id.actionbar_other4);

            actionBarBack4.setOnClickListener(this);
            actionBarTitle4.setOnClickListener(this);
            actionBarOther4.setOnClickListener(this);
        }
    }

    @Override
    protected void initView() {
        tRecyclerView = fvbi(R.id.recyclerview);
        TopRecyclerViewAdapter adapter = new TopRecyclerViewAdapter(getContext(), typeList);
        tRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        tRecyclerView.setHasFixedSize(false);
        tRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        typeList.add(2);
        typeList.add(3);
        typeList.add(3);
        typeList.add(2);
        typeList.add(3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionbar_back4:
                Toast.makeText(getContext(), "返回", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionbar_title4:
                Toast.makeText(getContext(), "标题", Toast.LENGTH_SHORT).show();
                break;
            case R.id.actionbar_other4:
                Toast.makeText(getContext(), "其他", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}