package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;

public class PostViewActivity extends AppCompatActivity {
    private final FirebaseOperations firebase = new FirebaseOperations();
    private SharedPreferences mPreferences;
    private TextView postCategory;
    private TextView postTitle;
    private TextView postUser;
    private TextView postContent;
    private ImageView postImage;
    private RecyclerView commentSpace;
    private Button toCommentButton;
    private String postID;
    private String userID;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_view_activity);

        postID = getIntent().getStringExtra("postID");
        String sharedPrefFile = "com.example.android.mainsharedprefs";
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);

        //Set references to Widgets
        postCategory = findViewById(R.id.post_category);
        postTitle = findViewById(R.id.post_title);
        postUser = findViewById(R.id.post_user);
        postContent = findViewById(R.id.post_content);
        postImage = findViewById(R.id.post_image);
        commentSpace = findViewById(R.id.comment_space);
        toCommentButton = findViewById(R.id.to_comment_button);

        //display post
        firebase.displayPost(postID,
                            postCategory,
                            postTitle,
                            postUser,
                            postContent,
                            postImage,
                            PostViewActivity.this);

        //set up comment recyclerview
        ArrayList<DataSnapshot> datasource = new ArrayList<>();
        CommentAdapter commentAdapter = new CommentAdapter( PostViewActivity.this, datasource, postID, userID);
        firebase.updateCommentDatasource(postID, datasource, commentAdapter);
        commentSpace.setAdapter(commentAdapter);
        commentSpace.setLayoutManager( new LinearLayoutManager(PostViewActivity.this));

        //add comment function
        toCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toPostComment = new Intent(PostViewActivity.this, AddCommentActivity.class);
                toPostComment.putExtra("postID", postID);
                startActivity(toPostComment);
            }
        });
    }

}