package com.example.embeddedprogrammingassignment.fragments.home.things_to_do;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;

import java.util.Objects;


public class DoOneFragment extends Fragment {

    TextView quit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_do_one, container, false);
        quit = view.findViewById(R.id.tvThingsToDo1);
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().finish();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
}