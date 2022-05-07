package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.adapter.ThingsToDoAdapter;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    CardView riskStatus,selfReport;
    SimpleViewPager thingsToDoSlider;
    ThingsToDoAdapter thingsToDoAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thingsToDoAdapter = new ThingsToDoAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        riskStatus=view.findViewById(R.id.riskStatusCard);
        selfReport=view.findViewById(R.id.selfReportCard);

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

        thingsToDoSlider = view.findViewById(R.id.svpHomeThingsToDoSlider);
        thingsToDoSlider.setAdapter(thingsToDoAdapter);
        thingsToDoSlider.startAutoScroll(true);
        thingsToDoSlider.setSliderDuration(7000);
        thingsToDoSlider.setPageTransformer(new CyclePageTransformer(thingsToDoSlider));

        // Inflate the layout for this fragment
        return view;
    }
}