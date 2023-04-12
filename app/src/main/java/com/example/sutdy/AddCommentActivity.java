package com.example.sutdy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class AddCommentActivity extends AppCompatActivity {
    private FirebaseOperations firebase = new FirebaseOperations();
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;
    private UniqueRNG idGenerator = new UniqueRNG();
    private String userID;
    private String postID;
    private String commentID;
    private EditText commentInputText;
    private Button uploadCommentPhotoButton;
    private Button addCommentButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment_activity);

        //Set references
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);
        postID = getIntent().getStringExtra("postID");
        commentID = String.valueOf(idGenerator.getNextNumber());
        commentInputText = findViewById(R.id.comment_input_text);
        uploadCommentPhotoButton = findViewById(R.id.upload_comment_photo_button);
        addCommentButton = findViewById(R.id.add_comment_button);

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = commentInputText.getText().toString();

                if(content.isEmpty()){
                    Toast.makeText(AddCommentActivity.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebase.createComment(postID, commentID, userID, content);

                    Toast.makeText(AddCommentActivity.this, "Answer Posted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCommentActivity.this, PostViewActivity.class);
                    intent.putExtra("postID", postID);
                    startActivity(intent);
                }
            }
        });

    }

}