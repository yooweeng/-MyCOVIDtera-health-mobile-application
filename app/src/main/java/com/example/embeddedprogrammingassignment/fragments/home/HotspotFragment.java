package com.example.embeddedprogrammingassignment.fragments.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.RedZoneLocation;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HotspotFragment extends Fragment {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    TextView tvCases, tvZoneStatus;
    User user;
    private static final int LOCATION_PERMISSION_CODE = 101;
    ArrayList<RedZoneLocation> zoneList = new ArrayList<>();
    ArrayList<Circle> circleList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_hotspot, container, false);

        tvCases = view.findViewById(R.id.tvZoneCases);
        tvZoneStatus = view.findViewById(R.id.tvZoneStatus);
        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));

        // Initialize map fragment
        supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMaps);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        getPermission();

        return view;
    }

    public void getPermission(){
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getCurrentLocation();
        }
        else {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_CODE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getCurrentLocation();
                }
            },2000);
        }
    }
    private void getCurrentLocation(){
        @SuppressLint("MissingPermission")
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                // Async map
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        // Initialize lat lug
                        LatLng latLng;

                        // This if-else is to check the GPS of the phone, if we can't get it, we set it to XMU location.
                        // So if you wanna spoof to XMU, remove the if-else and keep line 119 code.
                        if (location==null)
                            latLng = new LatLng(2.8325, 101.70694);
                         else
                            latLng = new LatLng(location.getLatitude(), location.getLongitude());

                        // Initialize marker options
                        MarkerOptions markerOptions = new MarkerOptions();
                        // Set position of marker
                        markerOptions.position(latLng);
                        // Set title of marker
                        markerOptions.title(latLng.latitude + ": " + latLng.longitude);
                        // Remove all marker
                        googleMap.clear();
                        // Animating to zoom the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        // Add marker on map
                        googleMap.addMarker(markerOptions);

                        getRedZone(googleMap, latLng.latitude, latLng.longitude);

                    }
                });
            }
        });
    }

    private void getRedZone(GoogleMap googleMap, double latitude, double longitude) {

        FirebaseDatabase.getInstance().getReference("hotspots").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    Log.i("MapError snapshot", String.valueOf(snapshot.getChildren()));
                    for(DataSnapshot child: snapshot.getChildren()) {
                        String cases = Objects.requireNonNull(child.child("cases").getValue()).toString();
                        String lg = Objects.requireNonNull(child.child("longitude").getValue()).toString();
                        String lt = Objects.requireNonNull(child.child("latitude").getValue()).toString();
                        Log.i("MapError loop", "cases: " + cases + " lat: " + lt + " long:" +  lg);
                        RedZoneLocation zone = new RedZoneLocation(Integer.parseInt(cases), Double.parseDouble(lt), Double.parseDouble(lg));
                        Log.i("MapError object", zone.toString());
                        zoneList.add(zone);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        new Handler().postDelayed(new Runnable() {
            @SuppressLint({"ResourceAsColor", "ResourceType"})
            @Override
            public void run() {
                List<Integer> tempIndex = new ArrayList<>();
                Log.i("MapError Zone List", zoneList.toString());
                for (int i=0; i<zoneList.size(); i++) {
                    if(zoneList.get(i).getCases() > 0) {
                        Circle circle = googleMap.addCircle(new CircleOptions()
                                .center(new LatLng(zoneList.get(i).getLatitude(), zoneList.get(i).getLongitude()))
                                .radius(500)
                                .strokeWidth(3f)
                                .strokeColor(Color.RED)
                                .fillColor(Color.argb(70,150,50,50)));

                        circleList.add(circle);
                        tempIndex.add(i);
                    }
                }

                if(circleList.isEmpty()) {
                    tvZoneStatus.setText("Green Zone");
                    tvZoneStatus.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                    tvZoneStatus.setForegroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                    tvZoneStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_warning));
                    tvCases.setText("Hi " + user.getName() + ", there has been no reported case(s) of COVID-19 within a 500m radius from your current position.");
                } else {
                    float[] distance = new float[2];
                    int tempCase=0;
                    float tempDistance = 2000;

                    for(int i=0; i<circleList.size(); i++) {
                        Location.distanceBetween(latitude, longitude, circleList.get(i).getCenter().latitude, circleList.get(i).getCenter().longitude, distance);

                        Log.i("MapDistance", Arrays.toString(distance));
                        if(distance[0] < circleList.get(i).getRadius()) {
                            tempCase = zoneList.get(tempIndex.get(i)).getCases();
                            tempDistance = distance[0];
                        }
                    }
                    if(tempDistance < circleList.get(0).getRadius()) {
                        tvCases.setText("Hi " + user.getName() + ", there has been "+ tempCase +" reported case(s) of COVID-19 within a 500m radius from your current position.");
                        tvZoneStatus.setText("Red Zone");
                        tvZoneStatus.setBackgroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                        tvZoneStatus.setForegroundTintList(ColorStateList.valueOf(Color.TRANSPARENT));
                        tvZoneStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.red_warning));
                    }
                    else {
                        tvCases.setText("Hi " + user.getName() + ", there has been no reported case(s) of COVID-19 within a 500m radius from your current position.");
                        tvZoneStatus.setText("Green Zone");
                        tvZoneStatus.setTextColor(ContextCompat.getColor(requireContext(), R.color.green_warning));
                    }
                }
            }
        },1500);

    }

}