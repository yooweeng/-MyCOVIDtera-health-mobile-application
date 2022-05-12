package com.example.embeddedprogrammingassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.embeddedprogrammingassignment.modal.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    Button registerBtn;
    TextView loginBtn;
    TextInputLayout nricEt, passwordEt, nameEt, phoneEt, genderEt, stateEt;
    AutoCompleteTextView genderView, stateView;
    Toolbar toolbar;
    ArrayAdapter<String> genderAdapterItems, stateAdapterItems;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

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

        String[] gender_option = getResources().getStringArray(R.array.gender);
        genderAdapterItems = new ArrayAdapter<String>(this, R.layout.register_gender_dropdown_item, gender_option);
        genderView.setAdapter(genderAdapterItems);

        String[] state_option = getResources().getStringArray(R.array.state);
        stateAdapterItems = new ArrayAdapter<String>(this, R.layout.register_state_dropdown_item, state_option);
        stateView.setAdapter(stateAdapterItems);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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
                register();
            }
        });

    }

    private void register() {
        String nric = nricEt.getEditText().getText().toString();
        String password = passwordEt.getEditText().getText().toString();
        String name = nameEt.getEditText().getText().toString();
        String phone = phoneEt.getEditText().getText().toString();
        String gender = genderView.getText().toString();
        String state = stateView.getText().toString();
        User user = new User(nric, password, name, phone, gender, state);

        if(validField(user)) {
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("users");

            Query newUser = reference.orderByChild("nric").equalTo(nric);

            newUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        Toast.makeText(getApplicationContext(), "Registration failed! Account has been registered before.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        reference.child(nric).setValue(user);
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.putExtra("nric", nric);
                        startActivity(intent);
                        finish();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean validField(User user) {
        if(user.getNric().length() != 12) {
            Toast.makeText(getApplicationContext(), "Invalid NRIC.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getName().length() < 5) {
            Toast.makeText(getApplicationContext(), "Please enter a valid name.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getPassword().length() < 6 ) {
            Toast.makeText(getApplicationContext(), "Please create a password with at least 6 characters.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getPhone().length() < 9 ) {
            Toast.makeText(getApplicationContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getGender().equals("")) {
            Toast.makeText(getApplicationContext(), "Please select your gender.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getState().equals("")) {
            Toast.makeText(getApplicationContext(), "Please select your current state.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}