package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.fragments.profile.EditProfileFragment;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    TextView nricEt, phoneEt, stateEt, nameEt;
    ImageView editProfileBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameEt = view.findViewById(R.id.tvProfileName);
        nricEt = view.findViewById(R.id.tvProfileNRIC);
        phoneEt = view.findViewById(R.id.tvProfilePhone);
        stateEt = view.findViewById(R.id.tvProfileState);
        editProfileBtn = view.findViewById(R.id.ivProfileEditBtn);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragContainer, new EditProfileFragment()).commit();
            }
        });

        String currActiveAcc = getActivity().getIntent().getStringExtra("nric");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(currActiveAcc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                nameEt.setText(user.getName());
                nricEt.setText(user.getNric());
                phoneEt.setText(user.getPhone());
                stateEt.setText(user.getState());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}