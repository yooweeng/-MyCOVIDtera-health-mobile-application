package com.example.embeddedprogrammingassignment.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.embeddedprogrammingassignment.LoginActivity;
import com.example.embeddedprogrammingassignment.MainActivity;
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
    LottieAnimationView lottieBtn;
    Button logOutBtn;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));

        nameTv = view.findViewById(R.id.tvProfileName);
        nricTv = view.findViewById(R.id.tvProfileNRIC);
        phoneTv = view.findViewById(R.id.tvProfilePhone);
        stateTv = view.findViewById(R.id.tvProfileState);
        editProfileBtn = view.findViewById(R.id.ivProfileEditBtn);
        nameVaxTv = view.findViewById(R.id.tvProfileVaxStatusName);
        nricVaxTv = view.findViewById(R.id.tvProfileVaxStatusNRIC);
        phoneVaxTv = view.findViewById(R.id.tvProfileVaxStatusPhone);
        lottieBtn = view.findViewById(R.id.lottieProfileLogoutBtn);
        logOutBtn = view.findViewById(R.id.btnProfileLogout);

        stateTv.setText(user.getState());
        setDetails(nameTv, nricTv, phoneTv);
        setDetails(nameVaxTv, nricVaxTv, phoneVaxTv);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("activeUser", Parcels.wrap(user));
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editProfileFragment, bundle);
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                requireActivity().finish();
            }
        });

        lottieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = new AlertDialog.Builder(getContext())
                        .setTitle("Delete Account")
                        .setMessage("Are you sure you want to delete the account?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                lottieBtn.playAnimation();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                                        startActivity(intent);
                                        requireActivity().finish();
                                    }
                                },2000);
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
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