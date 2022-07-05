package com.example.embeddedprogrammingassignment.fragments.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

    long vaccineNumber, getVaccineDetails;
    TextView tvVaccinationNRIC, tvVaccinationPhone, tvVaccinationState, tvVaccinationName, vaccine1, vaccine2, vaccine3, tvVaccineNo, tvAppointDate, tvAppointLocation;
    Button appointVaccine1Btn, appointVaccine2Btn, appointVaccine3Btn, confirmBtn;
    CardView appointDetailsCard, vaccineDetailsCard;
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

        appointDetailsCard = view.findViewById(R.id.vac_appointmentDetailsCardView);
        vaccineDetailsCard = view.findViewById(R.id.vaccineCard);
        tvVaccineNo = view.findViewById(R.id.tvVaccineNo);
        tvAppointDate = view.findViewById(R.id.tvDate);
        tvAppointLocation = view.findViewById(R.id.tvLocation);
        confirmBtn = view.findViewById(R.id.btnConfirmAppointment);

        Bundle bundle = new Bundle();
        bundle.putParcelable("activeUser", Parcels.wrap(user));

        appointVaccine1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_vaccinationFragment_to_appointmentFragment, bundle);
            }
        });

        appointVaccine2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_vaccinationFragment_to_appointmentFragment, bundle);
            }
        });

        appointVaccine3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_vaccinationFragment_to_appointmentFragment, bundle);
            }
        });

        FirebaseDatabase.getInstance().getReference("appointments").child(user.getNric()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                vaccineNumber=snapshot.getChildrenCount();
                Log.d("Vaccine Number in Vac Page", String.valueOf(vaccineNumber));
                getVaccineDetails = snapshot.getChildrenCount()-1;
                String currentStatus = String.valueOf(snapshot.child(String.valueOf(getVaccineDetails)).child("status").getValue());
                String date = (String) snapshot.child(String.valueOf(getVaccineDetails)).child("appointmentDate").getValue();
                String location = (String) snapshot.child(String.valueOf(getVaccineDetails)).child("appointmentLocation").getValue();

                int vaccine1Done = 1;
                int vaccine2Done = 2;
                int vaccine3Done = 3;

                if (String.valueOf(vaccineNumber).equals(String.valueOf(vaccine1Done))){
                    if (currentStatus.equals("completed")){
                        vaccine1.setVisibility(view.VISIBLE);
                        appointVaccine1Btn.setVisibility(view.GONE);
                        vaccine2.setVisibility(view.GONE);
                        appointVaccine2Btn.setVisibility(view.VISIBLE);
                    }
                    else{
                        appointDetailsCard.setVisibility(View.VISIBLE);
                        vaccineDetailsCard.setVisibility(View.GONE);
                        tvVaccineNo.setText(String.valueOf(vaccine1Done));
                        tvAppointDate.setText(date);
                        tvAppointLocation.setText(location);

                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                updateStatus(String.valueOf(getVaccineDetails), "completed");
                                vaccineDetailsCard.setVisibility(View.VISIBLE);
                                appointDetailsCard.setVisibility(View.GONE);
                                vaccine1.setVisibility(view.VISIBLE);
                                appointVaccine1Btn.setVisibility(view.GONE);
                                vaccine1.setText("completed");
                                vaccine2.setVisibility(view.GONE);
                                appointVaccine2Btn.setVisibility(view.VISIBLE);
                            }
                        });
                    }
                }
                else if (String.valueOf(vaccineNumber).equals(String.valueOf(vaccine2Done))){
                    if (currentStatus.equals("completed")){
                        vaccine1.setVisibility(view.VISIBLE);
                        appointVaccine1Btn.setVisibility(view.GONE);
                        vaccine2.setVisibility(view.VISIBLE);
                        vaccine2.setText("Completed");
                        appointVaccine2Btn.setVisibility(view.GONE);
                        vaccine3.setVisibility(view.GONE);
                        appointVaccine3Btn.setVisibility(view.VISIBLE);
                    }
                    else{
                        appointDetailsCard.setVisibility(View.VISIBLE);
                        vaccineDetailsCard.setVisibility(View.GONE);
                        tvVaccineNo.setText(String.valueOf(vaccine2Done));
                        tvAppointDate.setText(date);
                        tvAppointLocation.setText(location);

                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                updateStatus(String.valueOf(getVaccineDetails), "completed");
                                vaccineDetailsCard.setVisibility(View.VISIBLE);
                                appointDetailsCard.setVisibility(View.GONE);
                                vaccine1.setVisibility(view.VISIBLE);
                                appointVaccine1Btn.setVisibility(view.GONE);
                                vaccine2.setVisibility(view.VISIBLE);
                                vaccine2.setText("Completed");
                                appointVaccine2Btn.setVisibility(view.GONE);
                                vaccine3.setVisibility(view.GONE);
                                appointVaccine3Btn.setVisibility(view.VISIBLE);
                            }
                        });
                    }
                }
                else if (String.valueOf(vaccineNumber).equals(String.valueOf(vaccine3Done))){
                    if (currentStatus.equals("completed")){
                        vaccine1.setVisibility(view.VISIBLE);
                        appointVaccine1Btn.setVisibility(view.GONE);
                        vaccine2.setVisibility(view.VISIBLE);
                        vaccine2.setText("Completed");
                        appointVaccine2Btn.setVisibility(view.GONE);
                        vaccine3.setVisibility(view.VISIBLE);
                        vaccine3.setText("Completed");
                        appointVaccine3Btn.setVisibility(view.GONE);
                    }
                    else{
                        appointDetailsCard.setVisibility(View.VISIBLE);
                        vaccineDetailsCard.setVisibility(View.GONE);
                        tvVaccineNo.setText(String.valueOf(vaccine3Done));
                        tvAppointDate.setText(date);
                        tvAppointLocation.setText(location);

                        confirmBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                updateStatus(String.valueOf(getVaccineDetails), "completed");
                                vaccineDetailsCard.setVisibility(View.VISIBLE);
                                appointDetailsCard.setVisibility(View.GONE);
                                vaccine1.setVisibility(view.VISIBLE);
                                appointVaccine1Btn.setVisibility(view.GONE);
                                vaccine2.setVisibility(view.VISIBLE);
                                vaccine2.setText("Completed");
                                appointVaccine2Btn.setVisibility(view.GONE);
                                vaccine3.setVisibility(view.VISIBLE);
                                vaccine3.setText("Completed");
                                appointVaccine3Btn.setVisibility(view.GONE);
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void updateStatus(String vaccineNo, String currentStatus){
        FirebaseDatabase.getInstance().getReference("appointments").child(user.getNric()).child(vaccineNo).child("status").setValue(currentStatus);
    }
}