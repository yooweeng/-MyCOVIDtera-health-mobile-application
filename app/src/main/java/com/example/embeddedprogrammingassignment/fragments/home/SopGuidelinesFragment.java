package com.example.embeddedprogrammingassignment.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.adapter.ThingsToDoAdapter;

public class SopGuidelinesFragment extends Fragment {

    SimpleViewPager sopGuidelineSlider;
    ThingsToDoAdapter sopGuidelineAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sopGuidelineAdapter = new ThingsToDoAdapter(getContext(), R.id.action_sopGuidelinesFragment_to_thingsToDoActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_sop_guidelines, container, false);

        sopGuidelineSlider = view.findViewById(R.id.sopGuidelineSlider);
        sopGuidelineSlider.setAdapter(sopGuidelineAdapter);
        sopGuidelineSlider.startAutoScroll(true);
        sopGuidelineSlider.setSliderDuration(7000);
        sopGuidelineSlider.setPageTransformer(new CyclePageTransformer(sopGuidelineSlider));

        return view;
    }
}