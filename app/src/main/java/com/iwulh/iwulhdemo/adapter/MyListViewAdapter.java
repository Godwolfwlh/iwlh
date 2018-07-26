package com.iwulh.iwulhdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.bean.Data;

import java.util.List;

public class MyListViewAdapter extends RecyclerView.Adapter<MyListViewAdapter.ViewHolder> {

    private List<Data> dataList;

    public MyListViewAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ptr_frame_item, parent, false);
        MyListViewAdapter.ViewHolder viewHolder = new MyListViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.id.setText(dataList.get(position).getId() + "");
        holder.userName.setText(dataList.get(position).getUserName());
        holder.userSex.setText(dataList.get(position).getUserSex());
        holder.loginName.setText(dataList.get(position).getLoginName());
        holder.userAge.setText(dataList.get(position).getUserAge() + "");
        holder.creationTime.setText(dataList.get(position).getCreationTime());
        holder.updataTime.setText(dataList.get(position).getUpdataTime());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView userName;
        TextView userSex;
        TextView loginName;
        TextView userAge;
        TextView creationTime;
        TextView updataTime;
        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.ptr_item_id);
            userName = itemView.findViewById(R.id.ptr_item_username);
            userSex = itemView.findViewById(R.id.ptr_item_sex);
            loginName = itemView.findViewById(R.id.ptr_item_login_name);
            userAge = itemView.findViewById(R.id.ptr_item_age);
            creationTime = itemView.findViewById(R.id.ptr_item_creation_time);
            updataTime = itemView.findViewById(R.id.ptr_item_updata_time);
        }
    }
}