package com.example.embeddedprogrammingassignment.fragments.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

public class EditProfileFragment extends Fragment {

    public EditProfileFragment() {
        // Required empty public constructor
    }

    Toolbar toolbar;
    ArrayAdapter<String> genderAdapterItems, stateAdapterItems;
    TextInputLayout passwordEt1, passwordEt2, phoneEt, genderEt, stateEt;
    TextView nricEt, nameEt;
    AutoCompleteTextView genderView, stateView;
    User user;
    Button updateBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_edit_profile, container, false);

        nricEt = view.findViewById(R.id.tvEditProfileNRIC);
        passwordEt1 = view.findViewById(R.id.etEditProfilePwd);
        passwordEt2 = view.findViewById(R.id.etEditProfileConfirmedPwd);
        nameEt = view.findViewById(R.id.tvEditProfileName);
        phoneEt = view.findViewById(R.id.etEditProfilePhone);
        genderEt = view.findViewById(R.id.etEditProfileGender);
        stateEt = view.findViewById(R.id.etEditProfileState);
        genderView = view.findViewById(R.id.EditProfileGenderDropDown);
        stateView = view.findViewById(R.id.EditProfileStateDropDown);
        toolbar = view.findViewById(R.id.toolbar_place);
        updateBtn = view.findViewById(R.id.btnEditProfileUpdate);


        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        nricEt.setText(user.getNric());
        nameEt.setText(user.getName());
        phoneEt.getEditText().setText(user.getPhone());
        genderEt.getEditText().setText(user.getGender());
        stateEt.getEditText().setText(user.getState());
        passwordEt1.getEditText().setText(user.getPassword());
        passwordEt2.getEditText().setText(user.getPassword());


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

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passwordEt1.getEditText().getText().toString().equals(passwordEt2.getEditText().getText().toString())) {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference("users");
                    User updateDetails = new User();
                    updateDetails.setNric(user.getNric());
                    updateDetails.setGender(genderEt.getEditText().getText().toString());
                    updateDetails.setName(user.getName());
                    updateDetails.setPhone(phoneEt.getEditText().getText().toString());
                    updateDetails.setPassword(passwordEt1.getEditText().getText().toString());
                    updateDetails.setState(stateEt.getEditText().getText().toString());

                    databaseReference.child(user.getNric()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                databaseReference.child(user.getNric()).setValue(updateDetails);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("activeUser", Parcels.wrap(updateDetails));
                    Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_profileFragment, bundle);
                } else {
                    Toast.makeText(getContext(), "Password reset failed! You have entered incorrect passwords.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}