package com.example.embeddedprogrammingassignment.fragments.trace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.embeddedprogrammingassignment.R;


public class CheckInSuccessfulFragment extends Fragment {

    Button checkoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_trace_checkin_successful, container, false);

        checkoutBtn=view.findViewById(R.id.btnCheckout);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_checkInSuccessfulFragment_to_traceFragment);
            }
        });

        return view;
    }
}