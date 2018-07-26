package com.iwulh.iwulhdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.adapter.TestRvAdapter;

public class SeeKTabFragment extends Fragment {

    public static SeeKTabFragment newInstance() {
        return new SeeKTabFragment();
    }

    private RecyclerView mRvList;

    private View rootView;

    private TestRvAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_seek_tab,container,false);
        initWidget();
        return rootView;
    }

    public void initWidget(){
        adapter = new TestRvAdapter(getActivity());
        mRvList = (RecyclerView) rootView.findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRvList.setAdapter(adapter);
    }

    public RecyclerView getRvList(){
        return mRvList;
    }

}
