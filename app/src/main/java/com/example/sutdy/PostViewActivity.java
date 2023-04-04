package com.example.sutdy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.util.Objects;

//TODO: set up firebase

public class PostViewActivity extends AppCompatActivity {
    TextView postCategory;
    TextView postTitle;
    TextView postUser;
    TextView postContent;
    //RecyclerView commentSpace;
    Button toCommentButton;
    private String postID;
    private String userID;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_view_activity);
        postID = getIntent().getStringExtra("postID");
        userID = getIntent().getStringExtra("userID");

        //Set references to Widgets
        postCategory = findViewById(R.id.post_category);
        postTitle = findViewById(R.id.post_title);
        postUser = findViewById(R.id.post_user);
        postContent = findViewById(R.id.post_content);
        //commentSpace = findViewById(R.id.comment_space);
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

    }

}