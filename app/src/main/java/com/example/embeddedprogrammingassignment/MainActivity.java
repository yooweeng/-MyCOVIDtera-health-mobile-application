package com.example.embeddedprogrammingassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;

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
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavHostFragment navHostFragment;
    boolean openFlag = true;
    User user;
    Bundle bundle;

    Toolbar toolbar;
    private final List<Integer> list = Arrays.asList(R.id.homeFragment, R.id.statisticsFragment, R.id.traceFragment, R.id.profileFragment, R.id.action_homeFragment_to_thingsToDoActivity);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        getActiveUser();

        bottomNavigationView = findViewById(R.id.navbar);
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragContainer);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.riskStatusFragment);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if(list.contains(navDestination.getId())) {
                    toolbar.setVisibility(View.GONE);
                }
                else {
                    toolbar.setVisibility(View.VISIBLE);
                }
            }
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
}
