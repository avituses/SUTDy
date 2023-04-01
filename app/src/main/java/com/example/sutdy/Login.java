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

public class Login extends AppCompatActivity {
    EditText inputUsername;
    EditText inputPassword;
    Button loginButton;
    Button registerButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        //Set references to Widgets
        inputUsername = findViewById(R.id.input_username);
        inputPassword = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.login_button);
        registerButton = findViewById(R.id.register_button);

        String username = inputUsername.getText().toString();
        String password = inputPassword.getText().toString();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: set up login button such that can verify user details with firebase
                //TODO: if user credentials match, bring user to mainactivity with respective account
                Intent toMain = new Intent(Login.this, MainActivity.class);
                startActivity(toMain);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(Login.this, Register.class);
                startActivity(toRegister);
            }
        });
    }

}