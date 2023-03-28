package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//TODO: set up firebase

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

        //TODO: Take input from searchBar, find matching posts, return as output in postSpace

        //TODO: set onclicklistener for mostRelevant, call method that sort posts by relevancy/vote count
        //TODO: set onclicklistener for mostRecent, calls method that sorts posts by date posted
        //TODO: set onclicklistener for filterButton, redirects user to filter activity

        //TODO: call set text method of noOfPosts, edit to output no. of posts matching search

        //TODO: for every relevant post found from search, add as child to LinearLayout postSpace with set TextView settings
        //TODO: set onclicklistener (?) for TextView that redirects user to view post activity

        //TODO: set onclick listener for postButton, redirects user to create post activity
    }

    //Action bar menu: for stuff associated to your account
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //TODO: menu items redirects user to account information/log out?

}