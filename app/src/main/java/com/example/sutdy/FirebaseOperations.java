package com.example.sutdy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.Objects;

public class FirebaseOperations {
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                  .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private FirebaseStorageOperations firestore = new FirebaseStorageOperations();

    /*** Send data to firebase: posting a question, comments etc ***/
    public void createPost(Context context,
                           String Category,
                           String Title,
                           String Question,
                           String userID,
                           String fileName,
                           int postID) {

        DatabaseReference questionNode = databaseReference.child("Questions").child(String.valueOf(postID));
        questionNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //deliver user provided content to firebase
                questionNode.child("Category").setValue(Category);
                questionNode.child("Title").setValue(Title);
                questionNode.child("Question").setValue(Question);
                questionNode.child("User").setValue(userID);
                //set reference to image posted
                if(fileName != null){
                    questionNode.child("ImageURL").setValue(fileName);
                }
                Toast.makeText(context, "Question Posted!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });
    }

    public void createComment(String postID,
                              String commentID,
                              String userID,
                              String content,
                              String fileName){
        DatabaseReference questionCommentNode = databaseReference.child("Questions").child(postID).child("Answers");
        questionCommentNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (fileName != null){
                    questionCommentNode.child(commentID).child("ImageURL").setValue(fileName);
                }
                questionCommentNode.child(commentID).child("Content").setValue(content);
                questionCommentNode.child(commentID).child("User").setValue(userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    /*** Retrieve data from firebase: Question data, items for recycler views ***/

    //retrieving question data for postview
    public void displayPost(String postID,
                            TextView postCategory,
                            TextView postTitle,
                            TextView postUser,
                            TextView postContent,
                            ImageView postImage,
                            Context context){
        //set content according question data matching postID
        DatabaseReference postData = databaseReference.child("Questions").child(postID);
        postData.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("ImageURL").getValue()!=null) {
                    String fileName = Objects.requireNonNull(snapshot.child("ImageURL").getValue()).toString();
                    firestore.displayPhoto(postImage, context, fileName);
                }
                postCategory.setText(Objects.requireNonNull(snapshot.child("Category").getValue()).toString());
                postTitle.setText(Objects.requireNonNull(snapshot.child("Title").getValue()).toString());
                postUser.setText(Objects.requireNonNull(snapshot.child("User").getValue()) + " asks:");
                postContent.setText(Objects.requireNonNull(snapshot.child("Question").getValue()).toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //setting up mainactivtiy recyclerview's datasource
    public void updateDatasource(String filterCategory,
                                 String searchInput,
                                 ArrayList<DataSnapshot> datasource,
                                 TextView noOfPosts,
                                 QuestionAdapter questionAdapter) {
        //empty datasource
        datasource.clear();
        DatabaseReference questionsNode = databaseReference.child("Questions");
        questionsNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()) {
                    //if no filter active and no search input
                    if (filterCategory == null && searchInput == null) {
                        datasource.add(ds);
                    }
                    //if filter is active
                    else if (searchInput == null) {
                        if (filterCategory.equals(ds.child("Category").getValue())){
                            datasource.add(ds);
                        }
                    }
                    //if a search query is input
                    else if (filterCategory == null){
                        if(Objects.requireNonNull(ds.child("Title").getValue()).toString().toLowerCase().contains(searchInput)
                                || Objects.requireNonNull(ds.child("Question").getValue()).toString().toLowerCase().contains(searchInput)){
                            datasource.add(ds);
                        }
                    }
                    //both active filter AND search query input
                    else{
                        if(filterCategory.equals(ds.child("Category").getValue()) &&
                            (Objects.requireNonNull(ds.child("Title").getValue()).toString().toLowerCase().contains(searchInput)
                            || Objects.requireNonNull(ds.child("Question").getValue()).toString().toLowerCase().contains(searchInput))){
                            datasource.add(ds);
                        }
                    }
                }
                //update question adapter
                questionAdapter.notifyDataSetChanged();
                //set no. of posts
                noOfPosts.setText(datasource.size() + " Related Questions");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //setting up myquestion recyclerview's datasource
    public void updateMyQuestionsDatasource(ArrayList<DataSnapshot> datasource, String userID, QuestionAdapter questionAdapter){
        DatabaseReference questionsNode = databaseReference.child("Questions");
        questionsNode.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //if question poster matches userID
                    if (Objects.requireNonNull(ds.child("User").getValue()).toString().equals(userID)) {
                        datasource.add(ds);
                    }
                }
                questionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    //setting up comment recyclerview's datasource
    public void updateCommentDatasource(String postID,
                                        ArrayList<DataSnapshot> datasource,
                                        CommentAdapter commentAdapter){
        //set up commments recyclerview
        DatabaseReference questionCommentsNode = databaseReference.child("Questions").child(postID).child("Answers");
        questionCommentsNode.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //add all comments to recyclerview array
                for (DataSnapshot ds: snapshot.getChildren()){
                    datasource.add(ds);
                }
                commentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
