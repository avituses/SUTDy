package com.example.sutdy;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CharaViewHolder>{
    private static Context context;
    private LayoutInflater mInflater;
    private static ArrayList<DataSnapshot> dataSource;

    //complete the constructor to initialize the DataSource instance variable
    CommentAdapter(Context context, ArrayList<DataSnapshot> dataSource){
        this.dataSource = dataSource;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    //the layout of each Card is inflated and used to instantiate CharaViewHolder (no coding)
    @NonNull
    @Override
    public CharaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.comment_card, viewGroup, false);
        return new CharaViewHolder(itemView);
    }

    //the data at position i is extracted and placed on the i-th card
    @Override
    public void onBindViewHolder(@NonNull CharaViewHolder charaViewHolder, int i) {
        String user = Objects.requireNonNull(dataSource.get(i).child("User").getValue()).toString();
        String content = Objects.requireNonNull(dataSource.get(i).child("Content").getValue()).toString();

        charaViewHolder.commentUser.setText(user + " answered:");
        charaViewHolder.commentContent.setText(content);
    }

    //the total number of data points must be returned here
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    //complete the constructor to initialize the instance variables
    static class CharaViewHolder extends RecyclerView.ViewHolder{
        TextView commentUser;
        TextView commentContent;
        ImageView commentImage;

        CharaViewHolder(View view){
            super(view);
            commentUser = view.findViewById(R.id.comment_user);
            commentContent = view.findViewById(R.id.comment_content);
            commentImage = view.findViewById(R.id.comment_image);
        }
    }


}
