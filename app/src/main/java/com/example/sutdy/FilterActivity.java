package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

//TODO: set up firebase

public class FilterActivity extends AppCompatActivity {
    Spinner filterCategoryMenu;
    Button applyFilterButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);

        //Set references to Widgets
        filterCategoryMenu = findViewById(R.id.filter_category_menu);
        applyFilterButton = findViewById(R.id.apply_filter_button);

        //TODO: add categories to category menu
        //TODO: apply filter button to filter posts according to category in MainActivity
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}