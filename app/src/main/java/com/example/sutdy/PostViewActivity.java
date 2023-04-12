package com.example.sutdy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Objects;

public class PostViewActivity extends AppCompatActivity {
    private FirebaseOperations firebase = new FirebaseOperations();
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
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
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);

        //Set references to Widgets
        postCategory = findViewById(R.id.post_category);
        postTitle = findViewById(R.id.post_title);
        postUser = findViewById(R.id.post_user);
        postContent = findViewById(R.id.post_content);
        commentSpace = findViewById(R.id.comment_space);
        toCommentButton = findViewById(R.id.to_comment_button);

        //display post
        firebase.displayPost(postID, postCategory, postTitle, postUser, postContent);

        //set up comment recyclerview
        ArrayList<DataSnapshot> datasource = new ArrayList<>();
        CommentAdapter commentAdapter = new CommentAdapter( PostViewActivity.this, datasource);
        commentSpace.setAdapter(commentAdapter);
        commentSpace.setLayoutManager( new LinearLayoutManager(PostViewActivity.this));

        firebase.updateCommentDatasource(postID, datasource, commentAdapter);

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