package com.example.embeddedprogrammingassignment.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.embeddedprogrammingassignment.LoginActivity;
import com.example.embeddedprogrammingassignment.MainActivity;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.fragments.profile.EditProfileFragment;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    long dosNumber, getDoseDetails;
    CardView dose1Card, dose2Card, dose3Card;
    TextView nricTv, phoneTv, stateTv, nameTv, riskTv, riskTitleTv;
    TextView dose1Date, dose1Manufacturer, dose1Location, dose2Date, dose2Manufacturer, dose2Location, dose3Date, dose3Manufacturer, dose3Location;
    TextView nricVaxTv, phoneVaxTv, nameVaxTv;
    ImageView editProfileBtn, riskIv;
    LottieAnimationView lottieBtn;
    Button logOutBtn;
    User user;
    CardView covidRiskCv;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        String userRisk = getArguments().getString("currUserRisk");
        Log.i("currRisk@profile", userRisk);

        nameTv = view.findViewById(R.id.tvProfileName);
        nricTv = view.findViewById(R.id.tvProfileNRIC);
        phoneTv = view.findViewById(R.id.tvProfilePhone);
        stateTv = view.findViewById(R.id.tvProfileState);
        editProfileBtn = view.findViewById(R.id.ivProfileEditBtn);
        nameVaxTv = view.findViewById(R.id.tvProfileVaxStatusName);
        nricVaxTv = view.findViewById(R.id.tvProfileVaxStatusNRIC);
        phoneVaxTv = view.findViewById(R.id.tvProfileVaxStatusPhone);
        lottieBtn = view.findViewById(R.id.lottieProfileLogoutBtn);
        logOutBtn = view.findViewById(R.id.btnProfileLogout);
        covidRiskCv = view.findViewById(R.id.cvCovidExposureRisk);
        riskTv = view.findViewById(R.id.tvCardviewCovidRisk);
        riskTitleTv = view.findViewById(R.id.tvCardviewCovidTitle);
        riskIv = view.findViewById(R.id.ivCardviewCovidRisk);
        dose1Date = view.findViewById(R.id.tvDose1Date);
        dose1Location = view.findViewById(R.id.tvDose1Location);
        dose1Manufacturer = view.findViewById(R.id.tvDose1Manufacturer);
        dose2Date = view.findViewById(R.id.tvDose2Date);
        dose2Location = view.findViewById(R.id.tvDose2Location);
        dose2Manufacturer = view.findViewById(R.id.tvDose2Manufacturer);
        dose3Date = view.findViewById(R.id.tvDose3Date);
        dose3Location = view.findViewById(R.id.tvDose3Location);
        dose3Manufacturer = view.findViewById(R.id.tvDose3Manufacturer);
        dose1Card = view.findViewById(R.id.userDose1);
        dose2Card = view.findViewById(R.id.userDose2);
        dose3Card = view.findViewById(R.id.userDose3);

        riskTv.setText(userRisk);
        if (userRisk.equals("No Exposure Detected")) {
            riskIv.setImageResource(R.drawable.risk_green);
            covidRiskCv.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_warning));
        } else if (userRisk.equals("You are at High Risk")) {
            riskTitleTv.setTextColor(R.color.black);
            riskTv.setTextColor(R.color.black);
            riskIv.setImageResource(R.drawable.risk_warning);
            riskIv.setImageTintList(ColorStateList.valueOf(R.color.black_txt));
            covidRiskCv.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow_warning));
        } else {
            riskIv.setImageResource(R.drawable.risk_red);
            riskIv.setImageTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            covidRiskCv.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red_warning));
        }


        stateTv.setText(user.getState());
        setDetails(nameTv, nricTv, phoneTv);
        setDetails(nameVaxTv, nricVaxTv, phoneVaxTv);

        Bundle bundle = new Bundle();
        bundle.putParcelable("activeUser", Parcels.wrap(user));
        bundle.putString("currUserRisk", userRisk);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment, bundle);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        lottieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = new AlertDialog.Builder(getContext())
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you want to delete the account?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                lottieBtn.playAnimation();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        requireActivity().finish();
                                    }
                                }, 2000);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        FirebaseDatabase.getInstance().getReference("appointments").child(user.getNric()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dosNumber = snapshot.getChildrenCount();
                Log.d("Vaccine No: ", String.valueOf(dosNumber));

                getDoseDetails = snapshot.getChildrenCount()-1;
                int dose1 = 1;
                int dose2 = 2;
                int dose3 = 3;
                if (String.valueOf(dosNumber).equals(String.valueOf(dose1))){
                    int dose1Details = 0;
                    String dateDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("appointmentDate").getValue();
                    String currentStatusDose1 = String.valueOf(snapshot.child(String.valueOf(dose1Details)).child("status").getValue());
                    String locationDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("appointmentLocation").getValue();
                    String manufacturerDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("vaccineManufacturer").getValue();

                    if (currentStatusDose1.equals("completed")) {
                        dose1Card.setVisibility(View.VISIBLE);
                        dose1Date.setText(dateDose1);
                        dose1Location.setText(locationDose1);
                        dose1Manufacturer.setText(manufacturerDose1);
                    }
                }
                if (String.valueOf(dosNumber).equals(String.valueOf(dose2))){
                    int dose1Details = 0;
                    int dose2Details = 1;
                    String currentStatusDose2 = String.valueOf(snapshot.child(String.valueOf(dose2Details)).child("status").getValue());
                    String dateDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("appointmentDate").getValue();
                    String locationDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("appointmentLocation").getValue();
                    String manufacturerDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("vaccineManufacturer").getValue();

                    String dateDose2 = (String) snapshot.child(String.valueOf(dose2Details)).child("appointmentDate").getValue();
                    String locationDose2 = (String) snapshot.child(String.valueOf(dose2Details)).child("appointmentLocation").getValue();
                    String manufacturerDose2 = (String) snapshot.child(String.valueOf(dose2Details)).child("vaccineManufacturer").getValue();

                    dose1Card.setVisibility(View.VISIBLE);
                    dose1Date.setText(dateDose1);
                    dose1Location.setText(locationDose1);
                    dose1Manufacturer.setText(manufacturerDose1);

                    if (currentStatusDose2.equals("completed")) {
                        dose2Card.setVisibility(View.VISIBLE);
                        dose2Date.setText(dateDose2);
                        dose2Location.setText(locationDose2);
                        dose2Manufacturer.setText(manufacturerDose2);
                    }
                }
                if (String.valueOf(dosNumber).equals(String.valueOf(dose3))){
                    int dose1Details = 0;
                    int dose2Details = 1;
                    int dose3Details = 2;
                    String currentStatusDose3 = String.valueOf(snapshot.child(String.valueOf(dose3Details)).child("status").getValue());
                    String dateDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("appointmentDate").getValue();
                    String locationDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("appointmentLocation").getValue();
                    String manufacturerDose1 = (String) snapshot.child(String.valueOf(dose1Details)).child("vaccineManufacturer").getValue();

                    String dateDose2 = (String) snapshot.child(String.valueOf(dose2Details)).child("appointmentDate").getValue();
                    String locationDose2 = (String) snapshot.child(String.valueOf(dose2Details)).child("appointmentLocation").getValue();
                    String manufacturerDose2 = (String) snapshot.child(String.valueOf(dose2Details)).child("vaccineManufacturer").getValue();

                    String dateDose3 = (String) snapshot.child(String.valueOf(dose3Details)).child("appointmentDate").getValue();
                    String locationDose3 = (String) snapshot.child(String.valueOf(dose3Details)).child("appointmentLocation").getValue();
                    String manufacturerDose3 = (String) snapshot.child(String.valueOf(dose3Details)).child("vaccineManufacturer").getValue();

                    dose1Card.setVisibility(View.VISIBLE);
                    dose1Date.setText(dateDose1);
                    dose1Location.setText(locationDose1);
                    dose1Manufacturer.setText(manufacturerDose1);

                    dose2Card.setVisibility(View.VISIBLE);
                    dose2Date.setText(dateDose2);
                    dose2Location.setText(locationDose2);
                    dose2Manufacturer.setText(manufacturerDose2);

                    if (currentStatusDose3.equals("completed")) {
                        dose3Card.setVisibility(View.VISIBLE);
                        dose3Date.setText(dateDose3);
                        dose3Location.setText(locationDose3);
                        dose3Manufacturer.setText(manufacturerDose3);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void setDetails(TextView nameText, TextView nricText, TextView phoneText) {
        nameText.setText(user.getName());
        nricText.setText(user.getNric());
        phoneText.setText(user.getPhone());
    }
}