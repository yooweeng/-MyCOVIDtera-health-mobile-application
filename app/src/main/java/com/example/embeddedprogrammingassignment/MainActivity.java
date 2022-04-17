package com.example.embeddedprogrammingassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.embeddedprogrammingassignment.fragments.HomeFragment;
import com.example.embeddedprogrammingassignment.fragments.ProfileFragment;
import com.example.embeddedprogrammingassignment.fragments.StatisticsFragment;
import com.example.embeddedprogrammingassignment.fragments.TraceFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //To get the IC for user that has successfully logged in
        Intent intent = getIntent();
        String currActiveAcc = intent.getStringExtra("nric");
        Log.d("Current Active Account:",  currActiveAcc);

        //Initialize and Assign Value
        BottomNavigationView bottomNavigationView = findViewById(R.id.navbar);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, new HomeFragment()).commit();

        //Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.Home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.Home:
                    fragment = new HomeFragment();
                    break;
                case R.id.Statistic:
                    fragment = new StatisticsFragment();
                    break;
                case R.id.Trace:
                    fragment = new TraceFragment();
                    break;
                case R.id.Profile:
                    fragment = new ProfileFragment();
                    break;
            }
            assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, fragment).commit();
            return true;
        });
    }
}