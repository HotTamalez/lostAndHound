package com.example.zakweakland.lostandhound.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zakweakland.lostandhound.Models.Comment;
import com.example.zakweakland.lostandhound.R;

import org.w3c.dom.Text;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> data;

    public CommentAdapter(Context context, List<Comment> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_comment, viewGroup, false);
        return new CommentViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder commentViewHolder, int position) {

        commentViewHolder.tvCommentUsername.setText(data.get(position).getUname());
        commentViewHolder.tvCommentContent.setText(data.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        TextView tvCommentUsername, tvCommentContent;

        public CommentViewHolder(View itemView){
            super(itemView);
            tvCommentUsername = itemView.findViewById(R.id.commentUsername);
            tvCommentContent = itemView.findViewById(R.id.commentContent);
        }
    }
}
