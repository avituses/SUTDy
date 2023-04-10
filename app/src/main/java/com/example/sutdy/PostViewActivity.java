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

//TODO: set up firebase

public class PostViewActivity extends AppCompatActivity {
    TextView postCategory;
    TextView postTitle;
    TextView postUser;
    TextView postContent;
    ImageView postImage;
    RecyclerView commentSpace;
    Button toCommentButton;
    private String postID;
    private String userID;
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");

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

        DatabaseReference postData = databaseReference.child("Questions").child(postID);

        postData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postCategory.setText(Objects.requireNonNull(snapshot.child("Category").getValue()).toString());
                postTitle.setText(Objects.requireNonNull(snapshot.child("Title").getValue()).toString());
                postUser.setText(Objects.requireNonNull(snapshot.child("User").getValue()).toString() + " asks:");
                postContent.setText(Objects.requireNonNull(snapshot.child("Question").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostViewActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    //TODO: set up commments recyclerview (same concept as question recycler view)
        DatabaseReference questionCommentsNode = databaseReference.child("Questions").child(postID).child("Answers");
        questionCommentsNode.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DataSnapshot> datasource = new ArrayList<>();
                for (DataSnapshot ds: snapshot.getChildren()){
                    datasource.add(ds);}

                if (datasource.size() == 0){
                    return;
                }

                CommentAdapter commentAdapter = new CommentAdapter( PostViewActivity.this, datasource);
                commentSpace.setAdapter(commentAdapter);
                commentSpace.setLayoutManager( new LinearLayoutManager(PostViewActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PostViewActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

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