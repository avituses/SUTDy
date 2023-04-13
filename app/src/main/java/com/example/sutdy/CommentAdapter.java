package com.example.sutdy;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CharaViewHolder>{
    private FirebaseOperations firebase = new FirebaseOperations();
    private static Context context;
    private static String userID;
    private static String postID;
    private static String commentID;
    private LayoutInflater mInflater;
    private static ArrayList<DataSnapshot> dataSource;
    private FirebaseStorageOperations firestore = new FirebaseStorageOperations();

    //complete the constructor to initialize the DataSource instance variable
    CommentAdapter(Context context, ArrayList<DataSnapshot> dataSource){
        this.dataSource = dataSource;
        this.context = context;
        this.userID = userID;
        this.postID= postID;
        this.commentID= commentID;
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
        String ratingvalue= Objects.requireNonNull(dataSource.get(i).child("Ratings").getValue()).toString();
        String fileName;

        if (dataSource.get(i).child("ImageURL").getValue() != null){
            fileName = dataSource.get(i).child("ImageURL").getValue().toString();
            firestore.displayPhoto(charaViewHolder.commentImage, this.context, fileName);
        }
        charaViewHolder.commentUser.setText(user + " answered:");
        charaViewHolder.commentContent.setText(content);
        charaViewHolder.commentratingvalue.setText(ratingvalue);
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
        ImageButton commentupvote;
        TextView commentratingvalue;
        ImageButton commentdownvote;
        private FirebaseOperations firebase = new FirebaseOperations();

        private final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");



        CharaViewHolder(View view){
            super(view);
            commentUser = view.findViewById(R.id.comment_user);
            commentContent = view.findViewById(R.id.comment_content);
            commentImage = view.findViewById(R.id.comment_image);
            commentupvote = view.findViewById(R.id.upvote);
            commentratingvalue=view.findViewById(R.id.ratingvalue);
            commentdownvote=view.findViewById(R.id.downvote);

            commentupvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebase.ratingVote(postID,commentID,userID,true);
                }
            });
            commentdownvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    firebase.ratingVote(postID,commentID,userID,false);

                }
            });
        }
    }


}
