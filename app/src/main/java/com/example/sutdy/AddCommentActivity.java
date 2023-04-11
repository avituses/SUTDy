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
    private UniqueRNG idGenerator = new UniqueRNG();
    private String userID;
    private String postID;
    private String commentID;
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;
    private EditText commentInputText;
    private Button uploadCommentPhotoButton;
    private Button addCommentButton;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");

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

        DatabaseReference questionCommentNode = databaseReference.child("Questions").child(postID).child("Answers");

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = commentInputText.getText().toString();
                questionCommentNode.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check if id is taken
                        if (snapshot.hasChild(commentID)){
                            commentID = String.valueOf(idGenerator.getNextNumber());
                        }
                        else{
                            questionCommentNode.child(commentID).child("Content").setValue(content);
                            questionCommentNode.child(commentID).child("User").setValue(userID);

                            Toast.makeText(AddCommentActivity.this, "Answer Posted!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AddCommentActivity.this, PostViewActivity.class);
                            intent.putExtra("postID", postID);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCommentActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

    }

}