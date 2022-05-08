package com.example.embeddedprogrammingassignment.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.adapter.ThingsToDoAdapter;
import com.example.embeddedprogrammingassignment.modal.User;

import org.parceler.Parcels;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    CardView riskStatus,selfReport, hotlinePhone;
    SimpleViewPager thingsToDoSlider;
    ThingsToDoAdapter thingsToDoAdapter;
    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        }
        thingsToDoAdapter = new ThingsToDoAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        riskStatus=view.findViewById(R.id.riskStatusCard);
        selfReport=view.findViewById(R.id.selfReportCard);
        hotlinePhone = view.findViewById(R.id.hotlineCard);

        riskStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_riskStatusFragment);
            }
        });

        selfReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_selfReportCovidFragment);
            }
        });

        hotlinePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 01234567789"));
                startActivity(intent);
            }
        });

        thingsToDoSlider = view.findViewById(R.id.svpHomeThingsToDoSlider);
        thingsToDoSlider.setAdapter(thingsToDoAdapter);
        thingsToDoSlider.startAutoScroll(true);
        thingsToDoSlider.setSliderDuration(7000);
        thingsToDoSlider.setPageTransformer(new CyclePageTransformer(thingsToDoSlider));

        // Inflate the layout for this fragment
        return view;
    }
}