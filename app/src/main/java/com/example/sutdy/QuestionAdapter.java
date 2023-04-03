package com.example.sutdy;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.CharaViewHolder>{
    Context context;
    LayoutInflater mInflater;
    ArrayList<DataSnapshot> dataSource;

    //TODO 11.3 Complete the constructor to initialize the DataSource instance variable
    /** Not so good design, because Adapter is tightly coupled to a specific concrete class */
    QuestionAdapter(Context context, ArrayList<DataSnapshot> dataSource){
        this.dataSource = dataSource;
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }


    //TODO 11.5 the layout of each Card is inflated and used to instantiate CharaViewHolder (no coding)
    @NonNull
    @Override
    public CharaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.post_card, viewGroup, false);
        return new CharaViewHolder(itemView);
    }

    //TODO 11.6 the data at position i is extracted and placed on the i-th card
    @Override
    public void onBindViewHolder(@NonNull CharaViewHolder charaViewHolder, int i) {
        String title = Objects.requireNonNull(dataSource.get(i).child("Title").getValue()).toString();
        String question = Objects.requireNonNull(dataSource.get(i).child("Question").getValue()).toString();
        String category = Objects.requireNonNull(dataSource.get(i).child("Category").getValue()).toString();

        charaViewHolder.questionCategory.setText(category);
        charaViewHolder.questionTitle.setText(title);
        charaViewHolder.questionContent.setText(question);}

    //TODO 11.7 the total number of data points must be returned here
    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    //TODO 11.4 complete the constructor to initialize the instance variables
    static class CharaViewHolder extends RecyclerView.ViewHolder{

        TextView questionCategory;
        TextView questionTitle;
        TextView questionContent;

        CharaViewHolder(View view){
            super(view);
            questionCategory = view.findViewById(R.id.question_category);
            questionTitle = view.findViewById(R.id.question_title);
            questionContent = view.findViewById(R.id.question_content);
        }

    }


}
