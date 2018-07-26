package com.iwulh.iwulhdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwulh.iwulhdemo.R;
import com.iwulh.iwulhdemo.bean.Journalism;
import com.iwulh.iwulhdemo.views.CircleImageView;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Journalism> journalismList;

    public MessageAdapter(List<Journalism> journalismList) {
        this.journalismList = journalismList;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
        MessageAdapter.ViewHolder viewHolder = new MessageAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MessageAdapter.ViewHolder holder, int position) {
        holder.userName.setText(journalismList.get(position).getUserName());
        holder.messageTitle.setText(journalismList.get(position).getJournalismTitle());
        holder.messageShares.setText(journalismList.get(position).getJournalismShares());
        holder.messageComments.setText(journalismList.get(position).getJournalismComments());
        holder.messageViews.setText(journalismList.get(position).getJournalismViews());

    }

    @Override
    public int getItemCount() {
        return journalismList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView messageHead;
        TextView userName;
        TextView JournalismTime;
        TextView phoneModels;
        TextView messageTitle;
        TextView messageShares;
        TextView messageComments;
        TextView messageViews;

        ViewHolder(View itemView) {
            super(itemView);
            messageHead = itemView.findViewById(R.id.message_head_image);
            userName = itemView.findViewById(R.id.message_user_name);
            JournalismTime = itemView.findViewById(R.id.message_user_time);
            phoneModels = itemView.findViewById(R.id.message_phone_models);
            messageTitle = itemView.findViewById(R.id.message_title);
            messageShares = itemView.findViewById(R.id.message_shares);
            messageComments = itemView.findViewById(R.id.message_comments);
            messageViews = itemView.findViewById(R.id.message_views);
        }
    }
}
