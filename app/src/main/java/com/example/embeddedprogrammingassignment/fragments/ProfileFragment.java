package com.example.embeddedprogrammingassignment.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

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

import org.parceler.Parcels;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    TextView nricTv, phoneTv, stateTv, nameTv, riskTv, riskTitleTv;
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

        riskTv.setText(userRisk);
        if(userRisk.equals("No Exposure Detected")) {
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
                                },2000);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
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