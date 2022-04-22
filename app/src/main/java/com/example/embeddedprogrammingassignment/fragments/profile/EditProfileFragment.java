package com.example.embeddedprogrammingassignment.fragments.profile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.android.material.textfield.TextInputLayout;

public class EditProfileFragment extends Fragment {

    public EditProfileFragment() {
        // Required empty public constructor
    }

    Toolbar toolbar;
    ArrayAdapter<String> genderAdapterItems, stateAdapterItems;
    TextInputLayout nricEt, passwordEt1, passwordEt2, nameEt, phoneEt, genderEt, stateEt;
    AutoCompleteTextView genderView, stateView;
    User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit_profile, container, false);

        nricEt = view.findViewById(R.id.etEditProfileIC);
        passwordEt1 = view.findViewById(R.id.etEditProfilePwd);
        passwordEt2 = view.findViewById(R.id.etEditProfileConfirmedPwd);
        nameEt = view.findViewById(R.id.etEditProfileName);
        phoneEt = view.findViewById(R.id.etEditProfilePhone);
        genderEt = view.findViewById(R.id.etEditProfileGender);
        stateEt = view.findViewById(R.id.etEditProfileState);
        genderView = view.findViewById(R.id.EditProfileGenderDropDown);
        stateView = view.findViewById(R.id.EditProfileStateDropDown);
        toolbar = view.findViewById(R.id.toolbar_place);


        // user = Parcels.unwrap(getArguments().getParcelable("activeUser"));

        String[] gender_option = getResources().getStringArray(R.array.gender);
        genderAdapterItems = new ArrayAdapter<String>(getActivity(), R.layout.register_gender_dropdown_item, gender_option);
        genderView.setAdapter(genderAdapterItems);

        genderView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String gender = adapterView.getItemAtPosition(i).toString();
            }
        });

        String[] state_option = getResources().getStringArray(R.array.state);
        stateAdapterItems = new ArrayAdapter<String>(getActivity(), R.layout.register_state_dropdown_item, state_option);
        stateView.setAdapter(stateAdapterItems);

        stateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String state = adapterView.getItemAtPosition(i).toString();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}