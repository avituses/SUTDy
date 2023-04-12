package com.example.sutdy;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.Arrays;
import java.util.List;

public class FilterAdapter {
    public void setFilterCats(Context context, Spinner spinner) {
        //hardcode subject names
        List<String> mList = Arrays.asList("Computation Structures",
                                            "Info Systems",
                                            "Technological World",
                                            "Algorithms",
                                            "Data Driven World");

        // Create an adapter as shown below
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(context, R.layout.spinner_list, mList);
        mArrayAdapter.setDropDownViewResource(R.layout.spinner_list);

        spinner.setAdapter(mArrayAdapter);
    }
}
