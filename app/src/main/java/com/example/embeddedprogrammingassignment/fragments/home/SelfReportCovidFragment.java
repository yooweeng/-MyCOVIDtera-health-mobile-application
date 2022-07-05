package com.example.embeddedprogrammingassignment.fragments.home;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.embeddedprogrammingassignment.MainActivity;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

public class SelfReportCovidFragment extends Fragment {

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    ImageView infoButton,testkitPositive,testkitNegative;
    Button coughButton,tirednessButton,crampButton,smellLossButton,feverButton, submitButton;
    Boolean isSelected=true;
    int symScore=0;
    User user;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_self_report_covid, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        }

        infoButton=view.findViewById(R.id.ivInfo);
        testkitPositive=view.findViewById(R.id.ivTestkitPositive);
        testkitNegative=view.findViewById(R.id.ivTestkitNegative);
        coughButton=view.findViewById(R.id.btnCough);
        tirednessButton=view.findViewById(R.id.btnTiredness);
        crampButton=view.findViewById(R.id.btnCramp);
        smellLossButton=view.findViewById(R.id.btnSmellLoss);
        feverButton=view.findViewById(R.id.btnFever);
        submitButton = view.findViewById(R.id.btnSelfReportSubmit);

        testkitPositive.setForegroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.testkit_selected));
        testkitPositive.setSelected(!isSelected);
        testkitNegative.setForegroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.testkit_selected));
        testkitNegative.setSelected(!isSelected);
        coughButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.testkit_selected));
        tirednessButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.testkit_selected));
        crampButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.testkit_selected));
        smellLossButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.testkit_selected));
        feverButton.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.testkit_selected));

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTestkitInfoPopup();
            }
        });

        testkitPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testkitPositive.setSelected(!testkitPositive.isSelected());

                if(testkitPositive.isSelected() && testkitNegative.isSelected()){
                    testkitNegative.setSelected(!testkitNegative.isSelected());
                }
            }
        });

        testkitNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testkitNegative.setSelected(!testkitNegative.isSelected());

                if(testkitPositive.isSelected() && testkitNegative.isSelected()){
                    testkitPositive.setSelected(!testkitPositive.isSelected());
                }
            }
        });

        coughButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coughButton.setSelected(!coughButton.isSelected());
                if(coughButton.isSelected())
                    symScore += 1;
            }
        });

        tirednessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirednessButton.setSelected(!tirednessButton.isSelected());
                if(tirednessButton.isSelected())
                    symScore+=1;
            }
        });

        crampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crampButton.setSelected(!crampButton.isSelected());
                if(crampButton.isSelected())
                    symScore+=1;
            }
        });

        smellLossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smellLossButton.setSelected(!smellLossButton.isSelected());
                if(smellLossButton.isSelected())
                    symScore+=1;
            }
        });

        feverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feverButton.setSelected(!feverButton.isSelected());
                if(feverButton.isSelected())
                    symScore+=1;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String riskStatus = "No Exposure Detected";
                if(symScore>=3 && testkitNegative.isSelected()) {
                    riskStatus = "You are at High Risk";
                } else if (testkitPositive.isSelected()) {
                    riskStatus = "You are positive for COVID-19";
                }

                FirebaseDatabase.getInstance().getReference("risks").child(user.getNric()).child("risk").setValue(riskStatus);
                ((MainActivity) requireActivity()).getUser();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void createTestkitInfoPopup(){
        dialogBuilder=new AlertDialog.Builder(getActivity());
        final View popUpView=getLayoutInflater().inflate(R.layout.popup_testkitinfo,null);
        dialogBuilder.setView(popUpView);
        dialog=dialogBuilder.create();
        dialog.show();
    }
}