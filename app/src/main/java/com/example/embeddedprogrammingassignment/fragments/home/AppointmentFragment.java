package com.example.embeddedprogrammingassignment.fragments.home;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.embeddedprogrammingassignment.R;
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

import java.util.Calendar;

public class AppointmentFragment extends Fragment {
    long vaccineNo, getVaccineDetails;
    Button q1Yes, q1No, q2Yes, q2No, submit, btnConfirm, btnVaccine1, btnVaccine2, btnVaccine3;
    MaterialButtonToggleGroup q1Toggle, q2Toggle;
    TextView tvNRIC, tvPhone, tvState, tvName, tvVaccine1, tvVaccine2, tvVaccine3;
    TextView tvVaccineNo, tvAppointDate, tvAppointLocation;
    CardView appointDetails, vaccineBooking;
    AutoCompleteTextView selectDate, selectLocation;
    ArrayAdapter<String> ppvAdapterItems;
    User user;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String getVaccine, comorbidities;
    int flag = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home_appointment, container, false);

        tvName = view.findViewById(R.id.tvVaccinationName);
        tvNRIC = view.findViewById(R.id.tvVaccinationNRIC);
        tvPhone = view.findViewById(R.id.tvVaccinationPhone);
        tvState = view.findViewById(R.id.tvVaccinationState);
        tvVaccine1 = view.findViewById(R.id.tvVaccine1Status);
        tvVaccine2 = view.findViewById(R.id.tvVaccine2Status);
        tvVaccine3 = view.findViewById(R.id.tvVaccine3Status);
        appointDetails = view.findViewById(R.id.appointmentDetailsCardView);
        vaccineBooking = view.findViewById(R.id.bookVaccinationCardview);
        btnConfirm = view.findViewById(R.id.btnConfirmAppointment);
        btnVaccine1 = view.findViewById(R.id.btnVaccine1);
        btnVaccine2 = view.findViewById(R.id.btnVaccine2);
        btnVaccine3 = view.findViewById(R.id.btnVaccine3);

        q1Yes = view.findViewById(R.id.btnQ1Yes);
        q1No = view.findViewById(R.id.btnQ1No);
        q2Yes = view.findViewById(R.id.btnQ2Yes);
        q2No = view.findViewById(R.id.btnQ2No);
        q1Toggle = view.findViewById(R.id.toggleQ1);
        q2Toggle = view.findViewById(R.id.toggleQ2);
        selectDate = view.findViewById(R.id.vaccinationDate);
        selectLocation = view.findViewById(R.id.vaccinationLocation);
        submit = view.findViewById(R.id.btnAppointment);

        q1Yes.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.vaccination_question_option_selected));
        q1No.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.vaccination_question_option_selected));
        q2Yes.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.vaccination_question_option_selected));
        q2No.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.vaccination_question_option_selected));

        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        tvState.setText(user.getState());
        tvName.setText(user.getName());
        tvPhone.setText(user.getPhone());
        tvNRIC.setText(user.getNric());
        tvVaccineNo = view.findViewById(R.id.tvVaccineNo);
        tvAppointDate = view.findViewById(R.id.tvDate);
        tvAppointLocation = view.findViewById(R.id.tvLocation);

        Bundle bundle = new Bundle();
        bundle.putParcelable("activeUser", Parcels.wrap(user));

        q1Toggle.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                q1Yes.setSelected(false);
                q1No.setSelected(false);

                if(isChecked){
                    if(checkedId==R.id.btnQ1Yes){
                        q1Yes.setSelected(true);
                        q1No.setSelected(false);
                        getVaccine = q1Yes.getText().toString();
                        flag = 1;
                    }
                    if(checkedId==R.id.btnQ1No){
                        q1No.setSelected(true);
                        q1Yes.setSelected(false);
                        getVaccine = q1No.getText().toString();
                    }
                }
            }
        });

        q2Toggle.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                q2Yes.setSelected(false);
                q2No.setSelected(false);

                if(isChecked){
                    if(checkedId==R.id.btnQ2Yes){
                        q2Yes.setSelected(true);
                        comorbidities = q2Yes.getText().toString();
                    }
                    if(checkedId==R.id.btnQ2No){
                        q2No.setSelected(true);
                        comorbidities = q2No.getText().toString();
                    }
                }
            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        selectDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        String[] ppv_option = getResources().getStringArray(R.array.PPV);
        ppvAdapterItems = new ArrayAdapter<String>(getContext(), R.layout.vaccination_ppv_dropdown_item, ppv_option);
        selectLocation.setAdapter(ppvAdapterItems);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (q1Yes.isSelected()){
                    vaccineBooking.setVisibility(view.GONE);

                    FirebaseDatabase.getInstance().getReference("appointments").child(user.getNric()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dsp : snapshot.getChildren()) {
//                                vaccineNo=String.valueOf(dsp.getValue()); //add result into array list
//                            }
                            vaccineNo=snapshot.getChildrenCount();
                            Log.d("Vaccine No: ", String.valueOf(vaccineNo));
                            addAppointment(String.valueOf(vaccineNo));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateAppointment(String.valueOf(vaccineNo));
                        }
                    },2000);
                    appointDetails.setVisibility(view.VISIBLE);
                }
                else {
                    Navigation.findNavController(view).navigate(R.id.action_appointmentFragment_to_vaccinationFragment, bundle);
                }
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(String.valueOf(vaccineNo), "completed");
                Navigation.findNavController(view).navigate(R.id.action_appointmentFragment_to_vaccinationFragment, bundle);
            }
        });

        return view;
    }

    private void addAppointment(String vaccineNumber){
        String nric = user.getNric();
        String vaccine = getVaccine;
        String comm = comorbidities;
        String date = selectDate.getText().toString();
        String location = selectLocation.getText().toString();
        String status = "booked";
        Appointments appointments = new Appointments(nric, vaccine, comm, date, location, status);
        Log.d("VaccineNo: ", vaccineNumber);

        if(validAppointment(appointments)){
            rootNode = FirebaseDatabase.getInstance();
            reference = rootNode.getReference("appointments");

            Query newAppointment = reference.orderByChild("nric").equalTo(nric);

            newAppointment.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    reference.child(nric).child(vaccineNumber).setValue(appointments);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    private void updateStatus(String vaccineNo, String currentStatus){
        FirebaseDatabase.getInstance().getReference("appointments").child(user.getNric()).child(vaccineNo).child("status").setValue(currentStatus);
    }

    private boolean validAppointment(Appointments appointments) {
        if(appointments.getNric().length() != 12) {
            Toast.makeText(getContext(), "Invalid NRIC.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(appointments.getGetVaccine().equals("")) {
            Toast.makeText(getContext(), "Please select a choice.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(appointments.getComorbidities().equals("") ) {
            Toast.makeText(getContext(), "Please select a choice.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(appointments.getAppointmentDate().equals("") ) {
            Toast.makeText(getContext(), "Please select a date.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(appointments.getAppointmentLocation().equals("")) {
            Toast.makeText(getContext(), "Please select a location.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void updateAppointment(String vaccineNumber){
        FirebaseDatabase.getInstance().getReference("appointments").child(user.getNric()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getVaccineDetails = snapshot.getChildrenCount()-1;
                String date = (String) snapshot.child(String.valueOf(getVaccineDetails)).child("appointmentDate").getValue();
                String location = (String) snapshot.child(String.valueOf(getVaccineDetails)).child("appointmentLocation").getValue();

                tvVaccineNo.setText(vaccineNumber);
                tvAppointDate.setText(date);
                tvAppointLocation.setText(location);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}