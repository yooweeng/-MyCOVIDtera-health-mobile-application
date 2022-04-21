package com.example.embeddedprogrammingassignment.fragments.profile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.embeddedprogrammingassignment.R;

import java.util.Objects;

public class EditProfileFragment extends Fragment {

    public EditProfileFragment() {
        // Required empty public constructor
    }

    Toolbar toolbar;



//    setSupportActionBar(toolbar);
//    Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//    getSupportActionBar().setDisplayShowHomeEnabled(true);
//    getSupportActionBar().setTitle("Back");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        toolbar = view.findViewById(R.id.toolbar);

        // Inflate the layout for this fragment
        return view;
    }
}