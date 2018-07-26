package com.iwulh.iwulhdemo.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iwulh.iwulhdemo.R;

import java.util.List;

/**
 * Created by ZhengJiao on 2017/1/23.
 * 显示多个item和显示单个item的区别就是：
 *      1，TopRecyclerViewAdapter不再集成单个ViewHolder，而是集成RecyclerView.ViewHolder
 *      2，添加getItemViewType(int position)方法
 *      3，创建每个item对应的ViewHolder(创建多个ViewHolder)
 */
public class TopRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context context;
    //布局标识集合
    private final List<Integer> typeList;

    //设置两个常量
    private static final int TYPE_IMG_THREE = 2;      //2F(显示3张图片)
    private static final int TYPE_IMG_RECYCLER = 3;   //3F(显示无数张图片)


    public TopRecyclerViewAdapter(Context context,List<Integer> typeList) {
        this.context = context;
        this.typeList = typeList;
    }

    /**
     *根据不同的position，设置不同的ViewType
     *position表示当前是第几个Item，通过position拿到当前的Item对象，然后判断这个item对象需要那种视图
     */
    @Override
    public int getItemViewType(int position) {
        if (typeList.get(position) == 2) {
            return TYPE_IMG_THREE;
        } else if (typeList.get(position) == 3) {
            return TYPE_IMG_RECYCLER;
        }else {
            return 0;
        }
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder(当RecyclerView需要一个ViewHolder时会回调该方法，如果有可复用的View不会回调)
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       if (viewType == TYPE_IMG_THREE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_three, parent, false);
            ThreeViewHolder threeViewHolder = new ThreeViewHolder(view);
            return threeViewHolder;
        } else if (viewType == TYPE_IMG_RECYCLER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview_hrecyclerview, parent, false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
            return recyclerViewHolder;
        }
        return null;
    }

    //填充onCreateViewHolder方法返回的holder中的控件(当一个View需要出现在屏幕上时，该方法会被回调，我们需要再该方法中根据数据来更改视图)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       if (holder instanceof ThreeViewHolder) {
            setFloorTwo((ThreeViewHolder) holder);
        } else if (holder instanceof RecyclerViewHolder) {
            setFloorThree((RecyclerViewHolder) holder);
        }
    }

    //获取数据的数量(告诉RecyclerView有多少个视图需要显示)
    @Override
    public int getItemCount() {
        return typeList.size();
    }

    //设置二楼数据（显示3张图片）
    private void setFloorTwo(ThreeViewHolder holder) {
        holder.tvTitle.setText("这里显示三张图片");
    }

    //设置三楼数据（显示N张图片）
    private void setFloorThree(RecyclerViewHolder holder) {
        setHRecyclerView(holder.hRecyclerView);
    }

    private void setHRecyclerView(RecyclerView hRecyclerView) {

        HRecyclerViewAdapter hRecyclerViewAdapter = new HRecyclerViewAdapter(context);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(OrientationHelper.HORIZONTAL);
        hRecyclerView.setLayoutManager(layoutManager);
        hRecyclerView.setHasFixedSize(false);
        hRecyclerView.setAdapter(hRecyclerViewAdapter);

        hRecyclerViewAdapter.setOnItemClickListener(new HRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(context,"你点击了"+position+"条",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //三张图片
    public class ThreeViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public ImageView ivOne, ivTwo, ivThree;
        public ThreeViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            ivOne = (ImageView) itemView.findViewById(R.id.iv_one);
            ivTwo = (ImageView) itemView.findViewById(R.id.iv_two);
            ivThree = (ImageView) itemView.findViewById(R.id.iv_three);
        }

    }

    //横向的RecyclerView
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle;
        public RecyclerView hRecyclerView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            hRecyclerView = (RecyclerView) itemView.findViewById(R.id.h_recyclerview);
        }

    }

}
