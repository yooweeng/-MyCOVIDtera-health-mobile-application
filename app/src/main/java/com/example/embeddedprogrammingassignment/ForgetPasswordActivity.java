package com.example.embeddedprogrammingassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.embeddedprogrammingassignment.modal.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ForgetPasswordActivity extends AppCompatActivity {

    Button resetPwd;
    TextInputLayout nricEt, phoneEt, pwdEt, confirmedPwdEt;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        resetPwd = findViewById(R.id.btnResetPwd);
        nricEt = findViewById(R.id.etForgetIC);
        phoneEt = findViewById(R.id.etForgetPhone);
        pwdEt = findViewById(R.id.etForgetPwd);
        confirmedPwdEt = findViewById(R.id.etConfirmedPwd);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Back");

        resetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String nric = nricEt.getEditText().getText().toString();
        String phone = phoneEt.getEditText().getText().toString();
        String pwd1 = pwdEt.getEditText().getText().toString();
        String pwd2 = confirmedPwdEt.getEditText().getText().toString();

        if(validField(nric, phone, pwd1, pwd2)) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            reference.child(nric).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if(user.getPhone().equals(phone)) {
                        reference.child(nric).child("password").setValue(pwd1);

                        Toast.makeText(getApplicationContext(), "Password reset success!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
                        intent.putExtra("nric", nric);
                        startActivity(intent);
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Password reset failed! You have entered incorrect details.", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private boolean validField(String nric, String phone, String pwd1, String pwd2) {
        if(nric.length() != 12) {
            Toast.makeText(getApplicationContext(), "Invalid NRIC.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(phone.length() < 9 ) {
            Toast.makeText(getApplicationContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!pwd1.equals(pwd2) && pwd1.length()<=5) {
            Toast.makeText(getApplicationContext(), "Passwords does not match or less than 5 characters!", Toast.LENGTH_SHORT).show();
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