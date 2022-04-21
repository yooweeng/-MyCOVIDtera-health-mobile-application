package com.example.embeddedprogrammingassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import com.example.embeddedprogrammingassignment.fragments.HomeFragment;
import com.example.embeddedprogrammingassignment.fragments.ProfileFragment;
import com.example.embeddedprogrammingassignment.fragments.StatisticsFragment;
import com.example.embeddedprogrammingassignment.fragments.TraceFragment;
import com.example.embeddedprogrammingassignment.fragments.home.SelfReportCovidFragment;
import com.example.embeddedprogrammingassignment.fragments.trace.CheckInSuccessfulFragment;
import com.example.embeddedprogrammingassignment.fragments.trace.QrHistoryFragment;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque = new ArrayDeque<>(4);
    boolean openFlag = true;
    User user;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navbar);

        getActiveUser();

        integerDeque.push(R.id.Home);
        // Load initial Fragment & set initial bot nav item
        loadFragment(new SelfReportCovidFragment());
        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            //Get selected item id
            int id = item.getItemId();
            if(integerDeque.contains(id)) {
                //When deque list contains selected id
                //Check condition
                if(id == R.id.Home) {
                    //When selected id is the home fragment id
                    if(integerDeque.size() != 1) {
                        //When deque list size is not equal to 1
                        if(openFlag){
                            Log.d("Deque Home", integerDeque.toString());
                            integerDeque.addFirst(R.id.Home);
                            openFlag = false;
                        }
                    }
                }
                //Remove selected id from deque list
                Log.d("Deque Removed", integerDeque.toString());
                integerDeque.remove(id);
            }
            //Push selected id in deque list
            Log.d("Deque Pushed", integerDeque.toString());
            integerDeque.push(id);
            //Load fragment
            loadFragment(getFragment(item.getItemId()));
            return true;
        });
    }

    private void getActiveUser() {
        //To get the IC for user that has successfully logged in
        Intent intent = getIntent();
        String currActiveAcc = intent.getStringExtra("nric");
        Log.d("Current Active Account:",  currActiveAcc);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(currActiveAcc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                bundle = new Bundle();
                bundle.putParcelable("activeUser", Parcels.wrap(user));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    private Fragment getFragment(int itemId) {
        switch (itemId) {
            case R.id.Home:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                return new HomeFragment();
            case R.id.Statistic:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                return new StatisticsFragment();
            case R.id.Trace:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return new TraceFragment();
            case R.id.Profile:
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                return new ProfileFragment();
        }

        //Default
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        return new HomeFragment();
    }

    private void loadFragment(Fragment fragment) {
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        integerDeque.pop();
        Log.d("Deque Popped", integerDeque.toString());
        if (!integerDeque.isEmpty()) {
            loadFragment(getFragment(integerDeque.peek()));
        }
        else {
            finish();
        }
    }
}
