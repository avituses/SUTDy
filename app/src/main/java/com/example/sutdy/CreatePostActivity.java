package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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

//TODO: set up firebase

public class CreatePostActivity extends AppCompatActivity {
    Spinner postCategoryMenu;
    EditText postInputText;
    Button uploadPostMediaButton;
    Button uploadPostButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);

        //Set references to Widgets
        postCategoryMenu = findViewById(R.id.post_category_menu);
        postInputText = findViewById(R.id.post_input_text);
        uploadPostMediaButton = findViewById(R.id.upload_post_media_button);
        uploadPostButton = findViewById(R.id.upload_post_button);

        //TODO: set categories for postCategoryMenu
        //TODO: get post from postInputText, send content to Firebase (?)
        //TODO: set onclicklistener for uploadPostMediaButton, allow upload of media to post

        //TODO: set onclicklistener for uploadPostButton
        uploadPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreatePostActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        //TODO: uploadPostButton sends post content to Firebase
    }
}