package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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

    TextView fragmentTitleTv, nricTv, phoneTv, stateTv, nameTv;
    ImageView fragmentEditProfileBtn;
    Button checkInBtn, checkInHistoryBtn;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trace, container, false);



        if(getArguments()!= null)
            user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        else
            user = new User("Ali","123", "Ali", "012", "Male", "KL");

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

        checkInBtn = view.findViewById(R.id.btnCheckInQR);
        checkInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_traceFragment_to_checkInScanFragment);
            }
        });

        checkInHistoryBtn = view.findViewById(R.id.btnCheckInHistory);
        checkInHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_traceFragment_to_qrHistoryFragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}