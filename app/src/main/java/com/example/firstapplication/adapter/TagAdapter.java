package com.example.firstapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firstapplication.R;
import com.example.firstapplication.bean.Tag;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private Context context;
    private List<Tag> data;

    public TagAdapter(Context context, List<Tag> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.tag_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder viewHolder, int i) {
        Tag tag = data.get(i);
        viewHolder.tagId.setText(tag.getId());
        viewHolder.itemsCount.setText(tag.getItems_count() + "件の投稿");
        viewHolder.followersCount.setText(tag.getFollowers_count() + "人のフォロウー");
        Glide.with(context)
                .load(tag.getIcon_url())
                .into(viewHolder.tagIcon);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tagId;
        TextView itemsCount;
        TextView followersCount;
        ImageView tagIcon;

        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            tagId = itemView.findViewById(R.id.tag_id);
            itemsCount = itemView.findViewById(R.id.item_count);
            followersCount = itemView.findViewById(R.id.followers_count);
            tagIcon = itemView.findViewById(R.id.tag_icon);
        }
    }
}
