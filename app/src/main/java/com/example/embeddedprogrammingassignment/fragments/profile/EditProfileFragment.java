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

import com.example.embeddedprogrammingassignment.MainActivity;
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

import java.util.Objects;

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
        String userRisk = getArguments().getString("currUserRisk");
        Log.i("currRisk@profile", userRisk);

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

        String[] state_option = getResources().getStringArray(R.array.state);
        stateAdapterItems = new ArrayAdapter<String>(getActivity(), R.layout.register_state_dropdown_item, state_option);
        stateView.setAdapter(stateAdapterItems);
        
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validField(phoneEt.getEditText().getText().toString(),passwordEt1.getEditText().getText().toString(),passwordEt2.getEditText().getText().toString()))
                    return;

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("users");
                User updateUser = new User();
                updateUser.setNric(user.getNric());
                updateUser.setGender(genderEt.getEditText().getText().toString());
                updateUser.setName(user.getName());
                updateUser.setPhone(phoneEt.getEditText().getText().toString());
                updateUser.setPassword(passwordEt1.getEditText().getText().toString());
                updateUser.setState(stateEt.getEditText().getText().toString());

                databaseReference.child(user.getNric()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            databaseReference.child(user.getNric()).setValue(updateUser);
                            Log.d("EditActivity user @ after update dr ", updateUser.toString());
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("activeUser", Parcels.wrap(updateUser));
                            bundle.putString("currUserRisk", userRisk);
                            Log.d("EditActivity user @ update ", updateUser.toString());

                            ((MainActivity) requireActivity()).getUser();
                            Navigation.findNavController(view).navigate(R.id.action_editProfileFragment_to_profileFragment, bundle);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private boolean validField(String phone, String pwd1, String pwd2) {
        if(phone.length() < 9 ) {
            Toast.makeText(getContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!pwd1.equals(pwd2) || pwd1.length()<6) {
            Toast.makeText(getContext(), "Passwords does not match or less than 5 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}