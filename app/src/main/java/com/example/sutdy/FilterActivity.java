package com.example.sutdy;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class FilterActivity extends AppCompatActivity {
    private Spinner filterCategoryMenu;
    private Button applyFilterButton;
    private FilterAdapter filterAdapter = new FilterAdapter();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_activity);

        //Set references to Widgets
        filterCategoryMenu = findViewById(R.id.filter_category_menu);
        applyFilterButton = findViewById(R.id.apply_filter_button);

        //set filter categories
        filterAdapter.setFilterCats(FilterActivity.this, filterCategoryMenu);
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send filter category to main
                Intent intent = new Intent(FilterActivity.this, MainActivity.class);
                intent.putExtra("filterCategory", filterCategoryMenu.getSelectedItem().toString());
                intent.putExtra("filtered", true);
                startActivity(intent);
            }
        });

    }
}