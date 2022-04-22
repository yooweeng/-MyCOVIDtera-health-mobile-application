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

import com.example.embeddedprogrammingassignment.R;

public class SelfReportCovidFragment extends Fragment {

    AlertDialog.Builder dialogBuilder;
    AlertDialog dialog;
    ImageView infoButton,testkitPositive,testkitNegative;
    Button coughButton,tirednessButton,crampButton,smellLossButton,feverButton;
    Boolean isSelected=true;

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home_self_report_covid, container, false);

        infoButton=view.findViewById(R.id.ivInfo);
        testkitPositive=view.findViewById(R.id.ivTestkitPositive);
        testkitNegative=view.findViewById(R.id.ivTestkitNegative);
        coughButton=view.findViewById(R.id.btnCough);
        tirednessButton=view.findViewById(R.id.btnTiredness);
        crampButton=view.findViewById(R.id.btnCramp);
        smellLossButton=view.findViewById(R.id.btnSmellLoss);
        feverButton=view.findViewById(R.id.btnFever);

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
            }
        });

        tirednessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirednessButton.setSelected(!tirednessButton.isSelected());
            }
        });

        crampButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crampButton.setSelected(!crampButton.isSelected());
            }
        });

        smellLossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smellLossButton.setSelected(!smellLossButton.isSelected());
            }
        });

        feverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feverButton.setSelected(!feverButton.isSelected());
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