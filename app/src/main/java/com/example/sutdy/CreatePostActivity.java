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

public class CreatePostActivity extends AppCompatActivity{
    private FirebaseOperations firebase = new FirebaseOperations();
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;
    private UniqueRNG idGenerator;
    private String userID;
    private Spinner postCategoryMenu;
    private EditText postInputText;
    private EditText postInputTitle;
    private Button uploadPostMediaButton;
    private Button uploadPostButton;
    private FilterAdapter filterAdapter = new FilterAdapter();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);

        idGenerator = new UniqueRNG();

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);


        //Set references to Widgets
        postCategoryMenu = findViewById(R.id.post_category_menu);
        postInputText = findViewById(R.id.post_input_text);
        postInputTitle = findViewById(R.id.post_input_title);
        uploadPostMediaButton = findViewById(R.id.upload_post_media_button);
        uploadPostButton = findViewById(R.id.upload_post_button);

        filterAdapter.setFilterCats(CreatePostActivity.this, postCategoryMenu);

        uploadPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Question = postInputText.getText().toString();
                String Title = postInputTitle.getText().toString();
                String Category = postCategoryMenu.getSelectedItem().toString();
                int postID = idGenerator.getNextNumber();

                if (Question.isEmpty() || Title.isEmpty()) {
                    Toast.makeText(CreatePostActivity.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                } else {
                    firebase.createPost(CreatePostActivity.this, Category, Title, Question, userID, postID);

                    Intent CreatePost = new Intent(CreatePostActivity.this, MainActivity.class);
                    startActivity(CreatePost);
                }
            }
        });
    }
}