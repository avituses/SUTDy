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

public class Register extends AppCompatActivity {
    EditText newUsername;
    EditText newPassword;
    Button registerNew;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        //Set references to Widgets
        newUsername = findViewById(R.id.new_username);
        newPassword = findViewById(R.id.new_password);
        registerNew = findViewById(R.id.register_new);

        String regUser = newUsername.getText().toString();
        String regPassword = newPassword.getText().toString();

        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: create new user in firebase
                Intent register = new Intent(Register.this, Login.class);
                startActivity(register);
            }
        });

    }

}