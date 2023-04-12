package com.example.sutdy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SearchEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

//TODO: set up firebase

public class MainActivity extends AppCompatActivity {
    private FirebaseOperations firebase = new FirebaseOperations();
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;
    private String userID;
    private String filterCategory;
    private String searchInput;
    private SearchView searchBar;
    private ImageButton filterButton;
    private ImageButton refreshButton;
    private TextView noOfPosts;
    private RecyclerView postSpace;
    private FloatingActionButton postButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get intent data from Login.class.activity
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userID = mPreferences.getString("userID", null);

        if (userID == null){
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        }

        //get filter category from filter activity
        filterCategory = getIntent().getStringExtra("filterCategory");

        //Set references to Widgets
        searchBar = findViewById(R.id.search_bar);
        filterButton = findViewById(R.id.filter_button);
        refreshButton = findViewById(R.id.refresh_button);
        noOfPosts = findViewById(R.id.no_of_posts);
        postSpace = findViewById(R.id.post_space);
        postButton = findViewById(R.id.post_button);

        //set up recyclerview
        ArrayList<DataSnapshot> datasource = new ArrayList<>();

        QuestionAdapter questionAdapter = new QuestionAdapter( MainActivity.this, datasource, userID);
        postSpace.setLayoutManager( new LinearLayoutManager(MainActivity.this));
        postSpace.setAdapter(questionAdapter);
        firebase.updateDatasource(filterCategory, searchInput, datasource, noOfPosts, questionAdapter);
        //filter button takes user to filter activity
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filterIntent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(filterIntent);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchInput = null;
                filterCategory = null;
                firebase.updateDatasource(filterCategory, searchInput, datasource, noOfPosts, questionAdapter);
            }
        });

        //searchbar
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchInput = s.toLowerCase();
                firebase.updateDatasource(filterCategory, searchInput, datasource, noOfPosts, questionAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        //set onclick listener for postButton, redirects user to create post activity
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent postIntent = new Intent(MainActivity.this, CreatePostActivity.class);
                startActivity(postIntent);
            }
        });

    }

    //Action bar menu: for stuff associated to your account
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.log_out) {
            //reset userID, bring user to log in page
            userID = null;
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.my_questions) {
            //go to my questions page
            Intent intent = new Intent(MainActivity.this, MyQuestionsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}

