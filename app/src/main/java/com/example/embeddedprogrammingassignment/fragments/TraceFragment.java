package com.example.embeddedprogrammingassignment.fragments;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.User;

import org.parceler.Parcels;

public class TraceFragment extends Fragment {

    public TraceFragment() {
        // Required empty public constructor
    }

    TextView fragmentTitleTv, nricTv, phoneTv, stateTv, nameTv, riskTv, riskTitleTv;
    ImageView fragmentEditProfileBtn, riskIv;
    Button checkInBtn, checkInHistoryBtn;
    User user;
    CardView covidRiskCv;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trace, container, false);

        String userRisk = getArguments().getString("currUserRisk");
        Log.i("currRisk@trace", userRisk);

        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        Bundle bundle = new Bundle();
        bundle.putParcelable("activeUser", Parcels.wrap(user));

        fragmentTitleTv = view.findViewById(R.id.tvCarduserFragmentTitle);
        fragmentTitleTv.setText("Check-in");

        nameTv = view.findViewById(R.id.tvProfileName);
        nricTv = view.findViewById(R.id.tvProfileNRIC);
        phoneTv = view.findViewById(R.id.tvProfilePhone);
        stateTv = view.findViewById(R.id.tvProfileState);

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


        nameTv.setText(user.getName());
        nricTv.setText(user.getNric());
        phoneTv.setText(user.getPhone());
        stateTv.setText(user.getState());

        fragmentEditProfileBtn = view.findViewById(R.id.ivProfileEditBtn);
        fragmentEditProfileBtn.setVisibility(View.GONE);

        checkInBtn = view.findViewById(R.id.btnCheckInQR);
        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_traceFragment_to_checkInScanFragment, bundle);
            }
        });

        checkInHistoryBtn = view.findViewById(R.id.btnCheckInHistory);
        checkInHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_traceFragment_to_qrHistoryFragment, bundle);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}