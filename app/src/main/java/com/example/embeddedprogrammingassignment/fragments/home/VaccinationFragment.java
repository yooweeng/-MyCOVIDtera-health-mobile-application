package com.example.embeddedprogrammingassignment.fragments.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.embeddedprogrammingassignment.MainActivity;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.RegisterActivity;
import com.example.embeddedprogrammingassignment.modal.Appointments;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.Calendar;

public class VaccinationFragment extends Fragment {

    TextView tvVaccinationNRIC, tvVaccinationPhone, tvVaccinationState, tvVaccinationName, vaccine1, vaccine2, vaccine3;
    Button appointVaccine1Btn, appointVaccine2Btn, appointVaccine3Btn;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_vaccination, container, false);

        tvVaccinationNRIC = view.findViewById(R.id.tvVaccinationNRIC);
        tvVaccinationPhone = view.findViewById(R.id.tvVaccinationPhone);
        tvVaccinationState = view.findViewById(R.id.tvVaccinationState);
        tvVaccinationName = view.findViewById(R.id.tvVaccinationName);
        vaccine1 = view.findViewById(R.id.tvVaccine1Status);
        vaccine2 = view.findViewById(R.id.tvVaccine2Status);
        vaccine3 = view.findViewById(R.id.tvVaccine3Status);
        appointVaccine1Btn = view.findViewById(R.id.btnVaccine1);
        appointVaccine2Btn = view.findViewById(R.id.btnVaccine2);
        appointVaccine3Btn = view.findViewById(R.id.btnVaccine3);

        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        tvVaccinationState.setText(user.getState());
        tvVaccinationName.setText(user.getName());
        tvVaccinationPhone.setText(user.getPhone());
        tvVaccinationNRIC.setText(user.getNric());

        Bundle bundle = new Bundle();
        bundle.putParcelable("activeUser", Parcels.wrap(user));

        appointVaccine1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_vaccinationFragment_to_appointmentFragment, bundle);
            }
        });

        getParentFragmentManager().setFragmentResultListener("vaccineStatus", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String vaccine1Completion = result.getString("vaccine1Status");
                if (vaccine1Completion == "completed"){
                    vaccine1.setVisibility(view.VISIBLE);
                    appointVaccine1Btn.setVisibility(view.GONE);
                    vaccine2.setVisibility(view.GONE);
                    appointVaccine2Btn.setVisibility(view.VISIBLE);
                }
            }
        });
        return view;
    }
}