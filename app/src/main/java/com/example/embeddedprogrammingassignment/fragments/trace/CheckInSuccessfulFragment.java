package com.example.embeddedprogrammingassignment.fragments.trace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.fragments.TraceFragment;


public class CheckInSuccessfulFragment extends Fragment {

    Button checkoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_checkin_successful, container, false);

        checkoutBtn=view.findViewById(R.id.btnCheckout);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, new TraceFragment()).commit();
            }
        });

        return view;
    }
}