package com.example.firstapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.firstapplication.PostActivity;
import com.example.firstapplication.R;
import com.example.firstapplication.bean.Post;
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
        final Post post = data.get(i);
        viewHolder.author.setText(post.getUser().getId());
        viewHolder.time.setText(RelativeDateFormat.format(post.getCreated_at()));
        viewHolder.title.setText(post.getTitle());
        viewHolder.tags.setText(tagsToString(post.getTags()));
        Glide.with(context)
                .load(post.getUser().getProfile_image_url())
                .into(viewHolder.authorImg);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtra(PostActivity.POST_BODY, post.getRendered_body());
                intent.putExtra(PostActivity.POST_TITLE, post.getTitle());
                intent.putExtra(PostActivity.POST_CREATEDAT, post.getCreated_at());
                intent.putExtra(PostActivity.POST_TAGS, tagsToString(post.getTags()));
                context.startActivity(intent);
            }
        });
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
        TextView author;
        TextView time;
        TextView title;
        TextView tags;
        View itemView;
        ImageView authorImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            author = itemView.findViewById(R.id.post_author);
            time = itemView.findViewById(R.id.post_time);
            title = itemView.findViewById(R.id.post_title);
            tags = itemView.findViewById(R.id.post_tags);
            authorImg = itemView.findViewById(R.id.post_author_img);
        }
    }
}
