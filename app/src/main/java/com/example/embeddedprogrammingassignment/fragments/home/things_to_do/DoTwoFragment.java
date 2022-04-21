package com.example.embeddedprogrammingassignment.fragments.home.things_to_do;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;

public class DoTwoFragment extends Fragment {

    TextView quit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.fragment_do_two, container, false);
        quit = view.findViewById(R.id.tvThingsToDo2);
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