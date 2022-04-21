package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.User;

import org.parceler.Parcels;

public class TraceFragment extends Fragment {

    public TraceFragment() {
        // Required empty public constructor
    }

    TextView fragmentTitleTv, nricTv, phoneTv, stateTv, nameTv;
    ImageView fragmentEditProfileBtn;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trace, container, false);

        assert getArguments() != null;
        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));

        fragmentTitleTv = view.findViewById(R.id.tvCarduserFragmentTitle);
        fragmentTitleTv.setText("Check-in");

        nameTv = view.findViewById(R.id.tvProfileName);
        nricTv = view.findViewById(R.id.tvProfileNRIC);
        phoneTv = view.findViewById(R.id.tvProfilePhone);
        stateTv = view.findViewById(R.id.tvProfileState);

        nameTv.setText(user.getName());
        nricTv.setText(user.getNric());
        phoneTv.setText(user.getPhone());
        stateTv.setText(user.getState());

        fragmentEditProfileBtn = view.findViewById(R.id.ivProfileEditBtn);
        fragmentEditProfileBtn.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }
}