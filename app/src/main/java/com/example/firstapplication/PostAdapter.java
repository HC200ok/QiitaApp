package com.example.firstapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.firstapplication.bean.Post;
import com.example.firstapplication.bean.Posts;
import com.example.firstapplication.bean.Tag;
import com.example.firstapplication.util.RelativeDateFormat;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context context;
    private List<Post> data;

    public PostAdapter(Context context, List<Post> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.author.setText(data.get(i).getUser().getId());
        viewHolder.time.setText(RelativeDateFormat.format(data.get(i).getCreated_at()));
        viewHolder.title.setText(data.get(i).getTitle());
        viewHolder.tags.setText(tagsToString(data.get(i).getTags()));
    }

    public void loadMore(List<Post> posts) {
        data.addAll(posts);
        notifyDataSetChanged();
    }

    public void refreshData(List<Post> posts) {
        data = posts;
        notifyDataSetChanged();
    }

    private String tagsToString(List<Tag> Tags) {
        String tags_string = "";
        for (int i = 0; i < Tags.size(); i ++) {
            tags_string += Tags.get(i).getName() + "  ";
        }
        return tags_string;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView author;
        private TextView time;
        private TextView title;
        private TextView tags;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.post_author);
            time = itemView.findViewById(R.id.post_time);
            title = itemView.findViewById(R.id.post_title);
            tags = itemView.findViewById(R.id.post_tags);
        }
    }
}
