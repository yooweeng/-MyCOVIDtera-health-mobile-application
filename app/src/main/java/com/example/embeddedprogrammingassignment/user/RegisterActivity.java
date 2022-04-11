package com.example.embeddedprogrammingassignment.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    Button registerBtn;
    TextView loginBtn;
    TextInputLayout nricEt, passwordEt, nameEt, phoneEt, genderEt, stateEt;
    AutoCompleteTextView genderView, stateView;
    Toolbar toolbar;
    ArrayAdapter<String> genderAdapterItems, stateAdapterItems;

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
        genderEt = findViewById(R.id.etRegisterGender);
        stateEt = findViewById(R.id.etRegisterState);
        genderView = findViewById(R.id.RegisterGenderDropDown);
        stateView = findViewById(R.id.RegisterStateDropDown);
        toolbar = findViewById(R.id.toolbar);

        String[] gender = getResources().getStringArray(R.array.gender);
        genderAdapterItems = new ArrayAdapter<String>(this, R.layout.register_gender_dropdown_item, gender);
        genderView.setAdapter(genderAdapterItems);

        genderView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String gender = adapterView.getItemAtPosition(i).toString();
            }
        });

        String[] state = getResources().getStringArray(R.array.state);
        stateAdapterItems = new ArrayAdapter<String>(this, R.layout.register_state_dropdown_item, state);
        stateView.setAdapter(stateAdapterItems);

        stateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String state = adapterView.getItemAtPosition(i).toString();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");
//        getSupportActionBar().setHomeActionContentDescription(R.);


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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}