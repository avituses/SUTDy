package com.example.sutdy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import android.widget.ArrayAdapter;
import java.util.Arrays;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

//TODO: set up firebase

public class CreatePostActivity extends AppCompatActivity {

    private UniqueRNG idGenerator;
    private String userID;
    Spinner postCategoryMenu;
    EditText postInputText;
    EditText postInputTitle;
    Button uploadPostMediaButton;
    Button uploadPostButton;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);

        idGenerator = new UniqueRNG();

        userID = getIntent().getStringExtra("userID");


        //Set references to Widgets
        postCategoryMenu = findViewById(R.id.post_category_menu);
        // Create a list to display in the Spinner
        List<String> mList = Arrays.asList("Computation Structures", "Info Systems", "Technological World", "Algorithms", "Data Driven World");

        // Create an adapter as shown below
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(CreatePostActivity.this, R.layout.spinner_list, mList);
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list);

        // Set the adapter to the Spinner
        postCategoryMenu.setAdapter(mArrayAdapter);

        postInputText = findViewById(R.id.post_input_text);
        postInputTitle = findViewById(R.id.post_input_title);
        uploadPostMediaButton = findViewById(R.id.upload_post_media_button);
        uploadPostButton = findViewById(R.id.upload_post_button);

        //TODO: set categories for postCategoryMenu
        //TODO: get post from postInputText, send content to Firebase (?)

        //TODO: set onclicklistener for uploadPostMediaButton, allow upload of media to post

        //TODO: set onclicklistener for uploadPostButton

        // uploadPostMediaButton.setOnClickListener(new View.OnClickListener() {
            // @Override
            // public void onClick(View view) {
            //
            //}
        // })



        uploadPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Question = postInputText.getText().toString();
                String Title = postInputTitle.getText().toString();
                String Category = postCategoryMenu.getSelectedItem().toString();
                int postID = idGenerator.getNextNumber();


                databaseReference.child("Questions").child(userID).child(Category).child(String.valueOf(postID)).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int postID = idGenerator.getNextNumber();
                        //check if anything is not filled in
                        if (Question.isEmpty()) {
                            Toast.makeText(CreatePostActivity.this, "Please fill in a question.", Toast.LENGTH_SHORT).show();
                        } else {
                            // check if postID already exists to prevent overwriting
                            if (snapshot.exists()) {
                                postID = idGenerator.getNextNumber();
                            }
                            databaseReference.child("Questions").child(String.valueOf(postID)).child("Category").setValue(Category);
                            databaseReference.child("Questions").child(String.valueOf(postID)).child("Title").setValue(Title);
                            databaseReference.child("Questions").child(String.valueOf(postID)).child("Question").setValue(Question);
                            databaseReference.child("Questions").child(String.valueOf(postID)).child("User").setValue(userID);


                            Toast.makeText(CreatePostActivity.this, "Question Posted!", Toast.LENGTH_SHORT).show();
                            Intent CreatePost = new Intent(CreatePostActivity.this, MainActivity.class);
                            CreatePost.putExtra("userID", userID);
                            startActivity(CreatePost);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(CreatePostActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        //TODO: uploadPostButton sends post content to Firebase
    }
}