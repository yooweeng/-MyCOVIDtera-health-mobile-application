package com.example.embeddedprogrammingassignment.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.embeddedprogrammingassignment.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    Button registerBtn, loginBtn;
    TextInputLayout nricEt, passwordEt, nameEt, phoneEt;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerBtn = findViewById(R.id.btnRegister);
        loginBtn = findViewById(R.id.btnRegisterBackLogin);

        nricEt = findViewById(R.id.etRegisterIC);
        passwordEt = findViewById(R.id.etRegisterPwd);
        nameEt = findViewById(R.id.etRegisterName);
        phoneEt = findViewById(R.id.etRegisterPhone);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nric = nricEt.getEditText().getText().toString();
                String password = passwordEt.getEditText().getText().toString();
                String name = nameEt.getEditText().getText().toString();
                String phone = phoneEt.getEditText().getText().toString();

                Log.d("User Register Input", "Input: " + nric +" " + password +" " + name +" " + phone +" ");
            }
        });

    }
}