package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;

public class TraceFragment extends Fragment {

    public TraceFragment() {
        // Required empty public constructor
    }

    TextView fragmentTitleTv;
    ImageView fragmentEditProfileBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trace, container, false);

        fragmentTitleTv = view.findViewById(R.id.tvCarduserFragmentTitle);
        fragmentTitleTv.setText("Check-in");

        fragmentEditProfileBtn = view.findViewById(R.id.ivProfileEditBtn);
        fragmentEditProfileBtn.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }
}