package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

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
        List<String> mList = Arrays.asList("Computation Structures", "Info Systems", "Technological World", "Algorithms", "Data Driven World");

        // Create an adapter as shown below
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list, mList);
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list);

        // Set the adapter to the Spinner
        filterCategoryMenu.setAdapter(mArrayAdapter);


        applyFilterButton = findViewById(R.id.apply_filter_button);

        //TODO: add categories to category menu
        //TODO: apply filter button to filter posts according to category in MainActivity
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                intent.putExtra("filterCategory", filterCategoryMenu.getSelectedItem().toString());
                startActivity(intent);
            }
        });

    }
}