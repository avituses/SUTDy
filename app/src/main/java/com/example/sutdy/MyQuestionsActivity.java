package com.example.sutdy;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
public class MyQuestionsActivity extends AppCompatActivity {
    RecyclerView myQuestions;
    private String userID;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_questions_activity);

        //Set references to Widgets
        myQuestions = findViewById(R.id.my_questions);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);

        //set up recycler view
        DatabaseReference questionsNode = databaseReference.child("Questions");

        questionsNode.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<DataSnapshot> datasource = new ArrayList<>();
                for (DataSnapshot ds: snapshot.getChildren()){
                    //if question poster matches userID
                    if (Objects.requireNonNull(ds.child("User").getValue()).toString().equals(userID)){
                        datasource.add(ds);
                    }
                }
                QuestionAdapter questionAdapter = new QuestionAdapter( MyQuestionsActivity.this, datasource, userID);
                myQuestions.setAdapter(questionAdapter);
                myQuestions.setLayoutManager( new LinearLayoutManager(MyQuestionsActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyQuestionsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}