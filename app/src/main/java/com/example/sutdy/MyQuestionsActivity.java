package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.google.firebase.database.DataSnapshot;
import java.util.ArrayList;
public class MyQuestionsActivity extends AppCompatActivity {
    private FirebaseOperations firebase = new FirebaseOperations();
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;
    private RecyclerView myQuestions;
    private String userID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_questions_activity);

        //Set references to Widgets
        myQuestions = findViewById(R.id.my_questions);
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);

        ArrayList<DataSnapshot> datasource = new ArrayList<>();
        //set up recycler view
        QuestionAdapter questionAdapter = new QuestionAdapter(MyQuestionsActivity.this, datasource, userID);
        firebase.updateMyQuestionsDatasource(datasource, userID, questionAdapter);
        myQuestions.setLayoutManager(new LinearLayoutManager(MyQuestionsActivity.this));
        myQuestions.setAdapter(questionAdapter);
    }

}
