package com.example.sutdy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//TODO: set up firebase

public class Login extends AppCompatActivity {
    EditText inputUsername;
    EditText inputPassword;
    Button loginButton;
    Button registerButton;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
            .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");
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



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: set up login button such that can verify user details with firebase
                String username = inputUsername.getText().toString();
                String password = inputPassword.getText().toString();

                //TODO: if user credentials match, bring user to mainactivity with respective account
                databaseReference.child("users").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //if username exists, check if password matches
                        if (snapshot.exists()){
                            String verification = snapshot.child("password").getValue().toString();
                            if (password.equals(verification)){
                                Intent toMain = new Intent(Login.this, MainActivity.class);
                                toMain.putExtra("userID", username);
                                startActivity(toMain);
                            }
                            else Toast.makeText(Login.this, "Password incorrect.", Toast.LENGTH_SHORT).show();
                        }
                        else Toast.makeText(Login.this, "User does not exist.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Login.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
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