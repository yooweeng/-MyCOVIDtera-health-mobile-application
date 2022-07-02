package com.example.embeddedprogrammingassignment.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpacm.library.SimpleViewPager;
import com.cpacm.library.transformers.CyclePageTransformer;
import com.example.embeddedprogrammingassignment.modal.Announcements;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.adapter.ThingsToDoAdapter;
import com.example.embeddedprogrammingassignment.adapter.AnnouncementPageAdapter;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    CardView riskStatus,selfReport, hotlinePhone, sopViolation, faqs, hotspot, vaccination, sopGuidelines;
    SimpleViewPager thingsToDoSlider;
    ThingsToDoAdapter thingsToDoAdapter;
    User user;
    Bundle passingBundle;

    RecyclerView recyclerView;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("announcements");
    ArrayList<Announcements> list = new ArrayList<>();
    AnnouncementPageAdapter announcementPageAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        }
        passingBundle = new Bundle();
        passingBundle.putParcelable("activeUser", Parcels.wrap(user));
        thingsToDoAdapter = new ThingsToDoAdapter(getContext(), R.id.action_homeFragment_to_thingsToDoActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        riskStatus=view.findViewById(R.id.riskStatusCard);
        selfReport=view.findViewById(R.id.selfReportCard);
        hotlinePhone = view.findViewById(R.id.hotlineCard);
        faqs = view.findViewById(R.id.faqCard);
        recyclerView = view.findViewById(R.id.announcementContent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);
        announcementPageAdapter = new AnnouncementPageAdapter(getContext(), list, user.getNric());
        recyclerView.setAdapter(announcementPageAdapter);
        sopViolation = view.findViewById(R.id.violationCard);
        hotspot = view.findViewById(R.id.hotspotCard);
        vaccination = view.findViewById(R.id.vaccinationCard);
        sopGuidelines = view.findViewById(R.id.guidelineCard);

        riskStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_riskStatusFragment);
            }
        });

        selfReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_selfReportCovidFragment);
            }
        });

        hotlinePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel: 01234567789"));
                startActivity(intent);
            }
        });

        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_homeFaqFragment);
            }
        });

        sopViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_sopViolationFragment, passingBundle);
            }
        });

        hotspot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_hotspotFragment, passingBundle);
            }
        });

        vaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_vaccinationFragment, passingBundle);
            }
        });

        sopGuidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_sopGuidelinesFragment);
            }
        });

        thingsToDoSlider = view.findViewById(R.id.svpHomeThingsToDoSlider);
        thingsToDoSlider.setAdapter(thingsToDoAdapter);
        thingsToDoSlider.startAutoScroll(true);
        thingsToDoSlider.setSliderDuration(7000);
        thingsToDoSlider.setPageTransformer(new CyclePageTransformer(thingsToDoSlider));

        readAnnouncement();

        // Inflate the layout for this fragment
        return view;
    }

    private void readAnnouncement(){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    Announcements announcements = dataSnapshot.getValue(Announcements.class);
                    list.add(announcements);
                }
                announcementPageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
