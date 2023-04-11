package com.example.sutdy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.*;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//TODO: set up firebase

public class Register extends AppCompatActivity {
    private EditText newUsername;
    private EditText newPassword;
    private Button registerNew;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                    .getReferenceFromUrl("https://sutdy-1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        //Set references to Widgets
        newUsername = (EditText) findViewById(R.id.new_username);
        newPassword = (EditText) findViewById(R.id.new_password);
        registerNew = findViewById(R.id.register_new);


        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regUser = newUsername.getText().toString();
                String regPassword = newPassword.getText().toString();
                Boolean userTaken = false;


                databaseReference.child("users").child(regUser).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //check if username is taken, prompt user to change
                        if (snapshot.exists()) {
                            Toast.makeText(Register.this, "Username is taken. Try another one!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //create new user in firebase
                            if (regUser.isEmpty() || regPassword.isEmpty()){
                                Toast.makeText(Register.this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                databaseReference.child("users").child(regUser).child("password").setValue(regPassword);
                                Toast.makeText(Register.this, "Registered!", Toast.LENGTH_SHORT).show();
                                Intent register = new Intent(Register.this, Login.class);
                                startActivity(register);
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

}