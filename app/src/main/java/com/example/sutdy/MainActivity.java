package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    SearchView searchBar;
    Button mostRelevant;
    Button mostRecent;
    ImageButton filterButton;
    TextView noOfPosts;
    LinearLayout postSpace;
    FloatingActionButton postButton;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set references to Widgets
        searchBar = findViewById(R.id.search_bar);
        mostRelevant = findViewById(R.id.most_relevant);
        mostRecent = findViewById(R.id.most_recent);
        filterButton = findViewById(R.id.filter_button);
        noOfPosts = findViewById(R.id.no_of_posts);
        postSpace = findViewById(R.id.post_space);
        postButton = findViewById(R.id.post_button);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


}