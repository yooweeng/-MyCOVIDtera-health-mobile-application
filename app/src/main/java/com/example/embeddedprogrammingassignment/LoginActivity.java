package com.example.embeddedprogrammingassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button registerBtn, loginBtn;
    TextView forgetPwd;
    TextInputLayout nricEt, passwordEt;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerBtn = findViewById(R.id.btnLoginRegister);
        loginBtn = findViewById(R.id.btnLogin);
        forgetPwd = findViewById(R.id.btnLoginForgotPwd);
        nricEt = findViewById(R.id.etLoginIC);
        passwordEt = findViewById(R.id.etLoginPwd);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        String nric = nricEt.getEditText().getText().toString();
        String password = passwordEt.getEditText().getText().toString();

        if(validField(nric, password)) {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            Query checkUser = reference.orderByChild("nric").equalTo(nric);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        String passwordDB = snapshot.child(nric).child("password").getValue(String.class);
                        if (passwordDB.equals(password)) {

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("nric", nric);
                            startActivity(intent);
                            finish();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Login failed! You have entered incorrect NRIC or password.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Login failed! You have entered incorrect NRIC or password.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean validField(String nric, String password) {
        if(nric.length() != 12) {
            Toast.makeText(getApplicationContext(), "Invalid NRIC.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password.length() < 6 ) {
            Toast.makeText(getApplicationContext(), "Invalid password length.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}