package com.example.sutdy;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.*;
import java.io.IOException;

public class AddCommentActivity extends AppCompatActivity {
    private final FirebaseOperations firebase = new FirebaseOperations();
    private final FirebaseStorageOperations firebaseStorageOperations = new FirebaseStorageOperations();
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;
    private ClockFunc idGenerator = new ClockFunc();
    private String userID;
    private String postID;
    private String commentID;
    private String fileName;
    private EditText commentInputText;
    private ImageView postedCommentImage;
    private Button uploadCommentPhotoButton;
    private Button addCommentButton;
    private Bitmap bitmap;
    final static int REQUEST_IMAGE_GET = 2000;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_comment_activity);

        //Set references
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);
        postID = getIntent().getStringExtra("postID");
        commentID = String.valueOf(idGenerator.getCurrentDateTime());
        commentInputText = findViewById(R.id.comment_input_text);
        uploadCommentPhotoButton = findViewById(R.id.upload_comment_photo_button);
        addCommentButton = findViewById(R.id.add_comment_button);
        postedCommentImage = findViewById(R.id.posted_comment_image);

        addCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = commentInputText.getText().toString();

                if(content.isEmpty()){
                    Toast.makeText(AddCommentActivity.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                }
                else{
                    firebase.createComment(postID, commentID, userID, content, fileName);

                    Toast.makeText(AddCommentActivity.this, "Answer Posted!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCommentActivity.this, PostViewActivity.class);
                    intent.putExtra("postID", postID);
                    startActivity(intent);
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
                            postedCommentImage.setImageURI(photoUri);
                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(AddCommentActivity.this.getContentResolver(), photoUri);
                                fileName = firebaseStorageOperations.uploadUriToStorage(AddCommentActivity.this,photoUri);
                            } catch (IOException e) {
                                e.printStackTrace(); //Write a toast if you want
                            } //now go vvvvvvvvv(down)

                        }
                    }
                }
        );
        uploadCommentPhotoButton.setOnClickListener(new View.OnClickListener() {
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

