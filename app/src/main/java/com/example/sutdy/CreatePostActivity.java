package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import java.io.IOException;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

public class CreatePostActivity extends AppCompatActivity{
    private FirebaseOperations firebase = new FirebaseOperations();
    private FirebaseStorageOperations firebaseStorageOperations = new FirebaseStorageOperations();
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;
    private ClockFunc idGenerator;
    private String userID;
    private String fileName;
    private Spinner postCategoryMenu;
    private EditText postInputText;
    private EditText postInputTitle;
    private Button uploadPostMediaButton;
    private Button uploadPostButton;
    private ImageView postedImage;
    private FilterAdapter filterAdapter = new FilterAdapter();
    private Bitmap bitmap;
    final static int REQUEST_IMAGE_GET = 2000;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_post_activity);

        idGenerator = new ClockFunc();

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);


        //Set references to Widgets
        postCategoryMenu = findViewById(R.id.post_category_menu);
        postInputText = findViewById(R.id.post_input_text);
        postInputTitle = findViewById(R.id.post_input_title);
        uploadPostMediaButton = findViewById(R.id.upload_post_media_button);
        uploadPostButton = findViewById(R.id.upload_post_button);
        postedImage = findViewById(R.id.posted_image);

        //set filter categories
        filterAdapter.setFilterCats(CreatePostActivity.this, postCategoryMenu);

        uploadPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Question = postInputText.getText().toString();
                String Title = postInputTitle.getText().toString();
                String Category = postCategoryMenu.getSelectedItem().toString();
                String postID = idGenerator.getCurrentDateTime();

                if (Question.isEmpty() || Title.isEmpty()) {
                    Toast.makeText(CreatePostActivity.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                } else {
                    firebase.createPost(CreatePostActivity.this, Category, Title, Question, userID, fileName, postID);

                    Intent CreatePost = new Intent(CreatePostActivity.this, MainActivity.class);
                    startActivity(CreatePost);
                }
            }
        });

        /***upload media stuff***/
        final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            Intent intent = result.getData();
                            Uri photoUri = intent.getData();
                            postedImage.setImageURI(photoUri);
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(CreatePostActivity.this.getContentResolver(), photoUri);
                                fileName = firebaseStorageOperations.uploadUriToStorage(CreatePostActivity.this,photoUri);
                            } catch (IOException e) {
                                e.printStackTrace(); //Write a toast if you want
                            } //now go vvvvvvvvv(down)

                        }
                    }
                }
        );
        uploadPostMediaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //MediaStore.Images.Media.EXTERNAL_CONTENT_URI <-- location of the image gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(intent);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {
            Uri mImageUri = data.getData();
        }
    }
}