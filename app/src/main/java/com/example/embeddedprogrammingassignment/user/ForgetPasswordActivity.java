package com.example.embeddedprogrammingassignment.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.embeddedprogrammingassignment.MainActivity;
import com.example.embeddedprogrammingassignment.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
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

        if(pwd1.equals(pwd2)) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            Query findUser = reference.orderByChild("nric").equalTo(nric);

            findUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists() && (Objects.equals(snapshot.child(nric).child("phone").getValue(String.class), phone))) {
                        reference.child(nric).child("password").setValue(pwd1);
                        Toast.makeText(getApplicationContext(), "Password reset success!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ForgetPasswordActivity.this, MainActivity.class);
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
        else
            Toast.makeText(getApplicationContext(), "Your passwords does not match!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}