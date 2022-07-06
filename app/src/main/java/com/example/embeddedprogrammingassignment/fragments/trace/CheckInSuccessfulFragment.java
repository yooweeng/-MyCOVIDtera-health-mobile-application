package com.example.embeddedprogrammingassignment.fragments.trace;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.Objects;


public class CheckInSuccessfulFragment extends Fragment {

    Button checkoutBtn;
    TextView riskTv, riskTitleTv, tvNric, tvPhone, tvDate, tvTime, tvLocation;
    User user;
    CardView covidRiskCv;
    ImageView riskIv;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_trace_checkin_successful, container, false);

        assert getArguments() != null;
        user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        String userRisk = getArguments().getString("currUserRisk");
        String dateTime = getArguments().getString("dateTime");
        String location = getArguments().getString("location");

        Bundle bundle = new Bundle();
        bundle.putParcelable("activeUser", Parcels.wrap(user));
        bundle.putString("currUserRisk", userRisk);

        covidRiskCv = view.findViewById(R.id.cvCovidExposureRisk);
        riskTv = view.findViewById(R.id.tvCardviewCovidRisk);
        riskTitleTv = view.findViewById(R.id.tvCardviewCovidTitle);
        riskIv = view.findViewById(R.id.ivCardviewCovidRisk);
        tvNric = view.findViewById(R.id.tvCheckInSuccessNric);
        tvPhone = view.findViewById(R.id.tvCheckInSuccessPhone);
        tvDate = view.findViewById(R.id.tvCheckInSuccessDate);
        tvTime = view.findViewById(R.id.tvCheckInSuccessTime);
        tvLocation = view.findViewById(R.id.tvCheckInSuccessLocation);

        tvNric.setText(user.getNric());
        tvPhone.setText(user.getPhone());
        tvDate.setText(dateTime.split(" ")[0]);
        tvTime.setText(dateTime.split(" ")[1]);
        tvLocation.setText(location);

        riskTv.setText(userRisk);
        if(userRisk.equals("No Exposure Detected")) {
            riskIv.setImageResource(R.drawable.risk_green);
            covidRiskCv.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green_warning));
        } else if (userRisk.equals("You are at High Risk")) {
            riskTitleTv.setTextColor(R.color.black);
            riskTv.setTextColor(R.color.black);
            riskIv.setImageResource(R.drawable.risk_warning);
            riskIv.setImageTintList(ColorStateList.valueOf(R.color.black_txt));
            covidRiskCv.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow_warning));
        } else {
            riskIv.setImageResource(R.drawable.risk_red);
            riskIv.setImageTintList(ColorStateList.valueOf(Color.TRANSPARENT));
            covidRiskCv.setCardBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red_warning));
            addCaseToHotspotZone(location);
        }

        checkoutBtn=view.findViewById(R.id.btnCheckout);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_checkInSuccessfulFragment_to_traceFragment, bundle);
            }
        });

        return view;
    }

    private void addCaseToHotspotZone(String location) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("hotspots").child(location).child("cases");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    int cases = Integer.parseInt(Objects.requireNonNull(snapshot.getValue()).toString());
                    cases += 1;
                    ref.setValue(cases);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}