package com.example.embeddedprogrammingassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.example.embeddedprogrammingassignment.apiclient.Covid19.Covid19Data;
import com.example.embeddedprogrammingassignment.apiclient.Covid19.Covid19DataController;
import com.example.embeddedprogrammingassignment.apiclient.Covid19.Covid19DataService;
import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersData;
import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersDataController;
import com.example.embeddedprogrammingassignment.apiclient.Worldometers.WorldometersDataService;
import com.example.embeddedprogrammingassignment.modal.User;
import com.example.embeddedprogrammingassignment.singleton.SingletonBundle;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    NavHostFragment navHostFragment;
    User user;
    Bundle bundle;

    Toolbar toolbar;
    private final List<Integer> list = Arrays.asList(R.id.homeFragment,
                                                    R.id.statisticsFragment,
                                                    R.id.traceFragment,
                                                    R.id.profileFragment,
                                                    R.id.action_homeFragment_to_thingsToDoActivity,
                                                    R.id.checkInSuccessfulFragment);

    private ArrayList<Entry> total_cases_data = new ArrayList<>();
    private ArrayList<String> total_cases_label = new ArrayList<>();
    WorldometersData worldometersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bundle =  new Bundle();

        toolbar = findViewById(R.id.toolbar);

        getActiveUser();
    }

    private void getActiveUser() {
        //To get the IC for user that has successfully logged in
        Intent intent = getIntent();
        String currActiveAcc = intent.getStringExtra("nric");
        Log.d("Current Active Account @ Main Activity",  currActiveAcc);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(currActiveAcc).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                setNavigationComponent();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        readUserRiskFromDB(currActiveAcc);

        WorldometersDataService worldometersDataService = new WorldometersDataService();
        WorldometersDataController worldometersDataController = new WorldometersDataController(worldometersDataService);
        Call<WorldometersData> worldometerCall = worldometersDataController.findAll(true);

        worldometerCall.enqueue(new Callback<WorldometersData>() {
            @Override
            public void onResponse(Call<WorldometersData> call, Response<WorldometersData> response) {
                WorldometersData responseBody = response.body();
                Log.i("CovidDatafromApi Worldometers","Response body: " + responseBody);
                worldometersData = responseBody;
                bundle.putParcelable("worldometerData",Parcels.wrap(worldometersData));
            }

            @Override
            public void onFailure(Call<WorldometersData> call, Throwable t) {
                Log.i("CovidDatafromApi","onFailure: " + t.getMessage());
            }
        });

        Covid19DataService covid19DataService = new Covid19DataService();
        Covid19DataController covid19DataController = new Covid19DataController(covid19DataService);
        Call<List<Covid19Data>> covid19Call = covid19DataController.findAll("2022-01-01T00:00:00Z", LocalDate.now()+"T00:00:00Z");

        covid19Call.enqueue(new Callback<List<Covid19Data>>() {
            @Override
            public void onResponse(Call<List<Covid19Data>> call, Response<List<Covid19Data>> response) {
                List<Covid19Data> responseBody = response.body();

                Log.i("CovidDatafromApi Covid19Dataservice","Response body: " + responseBody.get(responseBody.size()-1));

                List<Covid19Data> pastWeekListTemp = responseBody.subList(responseBody.size()-8,responseBody.size());
                ArrayList<Integer> pastWeekCases = new ArrayList<>();
                ArrayList<String> pastWeekDayLabels = new ArrayList<>();

                for(int i=0; i<responseBody.size(); i++) {
                    total_cases_data.add(new Entry(i, Float.parseFloat(responseBody.get(i).getConfirmed())));
                    String[] tempSplit = responseBody.get(i).getDate().split("T");
                    total_cases_label.add(tempSplit[0]);
                }

                pastWeekListTemp.forEach(object -> {
                    pastWeekCases.add(Integer.parseInt(object.getConfirmed()));
                    pastWeekDayLabels.add(object.getDate());
                });

                bundle.putParcelableArrayList("total_cases_data",total_cases_data);
                bundle.putStringArrayList("total_cases_label",total_cases_label);
                bundle.putIntegerArrayList("pastWeekCases",pastWeekCases);
                bundle.putStringArrayList("pastWeekDayLabels",pastWeekDayLabels);

                SingletonBundle.saveBundle(bundle);
            }

            @Override
            public void onFailure(Call<List<Covid19Data>> call, Throwable t) {

            }
        });
    }

    private void setNavigationComponent() {
        bundle = new Bundle();
        bundle.putParcelable("activeUser", Parcels.wrap(user));
        Log.i("Count", user.toString());
        bottomNavigationView = findViewById(R.id.navbar);

        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragContainer);

        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();
        navController.setGraph(R.navigation.nav_graph, bundle);

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

                if(navDestination.getId()==R.id.checkInScanFragment){
                    bottomNavigationView.setVisibility(View.GONE);
                }
                else{
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            }
        });
        updateBottomNav(0, bundle);
    }

    private void updateBottomNav(int i, Bundle sendBundle) {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            navHostFragment.getNavController().getGraph().addInDefaultArgs(sendBundle);
            if (i==0)
                navHostFragment.getNavController().popBackStack(R.id.homeFragment, false);
            navHostFragment.getNavController().navigate(item.getItemId(), sendBundle);
            return true;
        });
    }

    public void getUser() {
        Intent intent = getIntent();
        String currActiveAcc = intent.getStringExtra("nric");

        readUserInfoFromDB(currActiveAcc);
        readUserRiskFromDB(currActiveAcc);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateBottomNav(1, bundle);
            }
        }, 2000);

    }

    private void readUserInfoFromDB(String currActiveAcc) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(currActiveAcc).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(User.class);
                bundle.putParcelable("activeUser", Parcels.wrap(user));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readUserRiskFromDB(String currActiveAcc) {
        final String[] currRisk = {""};

        FirebaseDatabase.getInstance().getReference("risks").child(currActiveAcc).child("risk").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    currRisk[0] = (String) snapshot.getValue();
                    Log.i("currRisk@main", currRisk[0]);
                    bundle.putString("currUserRisk", currRisk[0]);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
