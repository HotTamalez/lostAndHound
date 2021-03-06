package com.example.zakweakland.lostandhound.Adapters;

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
import com.example.zakweakland.lostandhound.Activities.PostDetailActivity;
import com.example.zakweakland.lostandhound.Models.Post;
import com.example.zakweakland.lostandhound.R;

import org.w3c.dom.Text;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {


    Context context;
    List<Post> mData;

    public PostAdapter(Context context, List<Post> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View row = LayoutInflater.from(context).inflate(R.layout.row_post_item, viewGroup, false);
        return new MyViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {

        myViewHolder.postTitle.setText(mData.get(position).getDogName());
        Glide.with(context).load(mData.get(position).getImage()).into(myViewHolder.imgPost);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView postTitle;
        ImageView imgPost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            postTitle = itemView.findViewById(R.id.rowPostTitle);
            imgPost = itemView.findViewById(R.id.rowPostImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent postDetailActivity = new Intent(context, PostDetailActivity.class);
                    int position = getAdapterPosition();

                    postDetailActivity.putExtra("name", mData.get(position).getDogName());
                    postDetailActivity.putExtra("breed", mData.get(position).getBreed());
                    postDetailActivity.putExtra("age", mData.get(position).getDogAge());
                    postDetailActivity.putExtra("desc", mData.get(position).getAdditionalInfo());
                    postDetailActivity.putExtra("image", mData.get(position).getImage());
                    postDetailActivity.putExtra("key", mData.get(position).getPostKey());
//                    postDetailActivity.putExtra("username", mData.get(position).getUserName());
                    long timestamp = (long) mData.get(position).getTimestamp();
                    postDetailActivity.putExtra("timestamp", timestamp);
                    context.startActivity(postDetailActivity);
                }
            });
        }
    }
}
