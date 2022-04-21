package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.fragments.profile.EditProfileFragment;
import com.example.embeddedprogrammingassignment.modal.User;

import org.parceler.Parcels;

import java.util.Objects;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {

    }

    TextView nricTv, phoneTv, stateTv, nameTv;
    TextView nricVaxTv, phoneVaxTv, nameVaxTv;
    ImageView editProfileBtn;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

//        assert getArguments() != null;
//        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        user = new User("Alvin","Alvin","Alvin","Alvin","Alvin","Alvin");

        nameTv = view.findViewById(R.id.tvProfileName);
        nricTv = view.findViewById(R.id.tvProfileNRIC);
        phoneTv = view.findViewById(R.id.tvProfilePhone);
        stateTv = view.findViewById(R.id.tvProfileState);
        editProfileBtn = view.findViewById(R.id.ivProfileEditBtn);
        nameVaxTv = view.findViewById(R.id.tvProfileVaxStatusName);
        nricVaxTv = view.findViewById(R.id.tvProfileVaxStatusNRIC);
        phoneVaxTv = view.findViewById(R.id.tvProfileVaxStatusPhone);

        stateTv.setText(user.getState());
        setDetails(nameTv, nricTv, phoneTv);
        setDetails(nameVaxTv, nricVaxTv, phoneVaxTv);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void setDetails(TextView nameText, TextView nricText, TextView phoneText) {
        nameText.setText(user.getName());
        nricText.setText(user.getNric());
        phoneText.setText(user.getPhone());
    }
}