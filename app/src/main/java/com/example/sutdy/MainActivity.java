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
    private String userID;
    private String filterCategory;
    private String searchInput;
    SearchView searchBar;
    ImageButton filterButton;
    ArrayList<DataSnapshot> datasource = new ArrayList<>();
    TextView noOfPosts;
    RecyclerView postSpace;
    FloatingActionButton postButton;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    private final String sharedPrefFile = "com.example.android.mainsharedprefs";
    private SharedPreferences mPreferences;


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
        noOfPosts = findViewById(R.id.no_of_posts);
        postSpace = findViewById(R.id.post_space);
        postButton = (FloatingActionButton) findViewById(R.id.post_button);


        //TODO: Take input from searchBar, find matching posts, return as output in postSpace
        //TODO: if input empty, show error message

        //set onclicklistener for filterButton, takes user to filter activity
        //TODO: retrieve filter-by category from FilterActivity, filter posts through category
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent filterIntent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(filterIntent);
            }
        });



        //TODO: call set text method of noOfPosts, edit to output no. of posts matching search
        DatabaseReference questionsNode = databaseReference.child("Questions");

        questionsNode.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    if (filterCategory == null || filterCategory.equals(ds.child("Category").getValue())){
                    datasource.add(ds);
                    }
                }
                noOfPosts.setText(String.valueOf(datasource.size()) + " Related Questions");
                QuestionAdapter questionAdapter = new QuestionAdapter( MainActivity.this, datasource,userID);
                postSpace.setAdapter(questionAdapter);
                postSpace.setLayoutManager( new LinearLayoutManager(MainActivity.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                datasource.clear();
                searchInput = s.toLowerCase();
                questionsNode.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            if ((filterCategory == null || filterCategory.equals(ds.child("Category").getValue())) &&
                                    ds.child("Title").getValue().toString().toLowerCase(Locale.ROOT).contains(searchInput)) {
                                datasource.add(ds);
                            }
                        }
                        noOfPosts.setText(String.valueOf(datasource.size()) + " Related Questions");
                        QuestionAdapter questionAdapter = new QuestionAdapter(MainActivity.this, datasource, userID);
                        postSpace.setAdapter(questionAdapter);
                        postSpace.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                return false;
            }
        });



        //TODO: for every relevant post found from search, add as child to LinearLayout postSpace with set TextView settings
        //TODO: set onclicklistener (?) for TextView that redirects user to view post activity

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

    //TODO: menu items redirects user to account information/log out?
    //TODO: Log Out takes user to Log In page
    //TODO: My Questions takes user to page showing the Questions they posted
    //TODO: My Answers takes user to page showing Comments/Answers they posted
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.log_out) {
            userID = null;
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.my_questions) {
            //TODO: go to my questions page
            Intent intent = new Intent(MainActivity.this, MyQuestionsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }



}

