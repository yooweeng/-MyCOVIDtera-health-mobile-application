package com.example.embeddedprogrammingassignment.fragments.home;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.embeddedprogrammingassignment.MainActivity;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.HealthRiskAssessment;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.util.Arrays;
import java.util.List;

public class RiskStatusFragment extends Fragment {

    MaterialButtonToggleGroup toggleGroupQ2,toggleGroupQ3,toggleGroupQ4;
    MaterialCheckBox sym1, sym2, sym3, sym4, sym5, sym6, sym7, sym8, sym9, sym10,sym11,sym12;

    Button q2YesBtn,q2NoBtn,q3YesBtn,q3NoBtn,q4YesBtn,q4NoBtn, submitRiskBtn;
    ImageView riskLogoIv;
    TextView statusTv, riskTv;
    CardView riskCv;
    User user;
    HealthRiskAssessment assessment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home_risk_status, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user = Parcels.unwrap(getArguments().getParcelable("activeUser"));
        }

        toggleGroupQ2=view.findViewById(R.id.tgq2);
        toggleGroupQ3=view.findViewById(R.id.tgq3);
        toggleGroupQ4=view.findViewById(R.id.tgq4);
        q2YesBtn=view.findViewById(R.id.btnq2Yes);
        q2NoBtn=view.findViewById(R.id.btnq2No);
        q3YesBtn=view.findViewById(R.id.btnq3Yes);
        q3NoBtn=view.findViewById(R.id.btnq3No);
        q4YesBtn=view.findViewById(R.id.btnq4Yes);
        q4NoBtn=view.findViewById(R.id.btnq4No);
        submitRiskBtn = view.findViewById(R.id.btnSubmitRiskStatus);

        sym1 = view.findViewById(R.id.tgRisk1);
        sym2 = view.findViewById(R.id.tgRisk2);
        sym3 = view.findViewById(R.id.tgRisk3);
        sym4 = view.findViewById(R.id.tgRisk4);
        sym5 = view.findViewById(R.id.tgRisk5);
        sym6 = view.findViewById(R.id.tgRisk6);
        sym7 = view.findViewById(R.id.tgRisk7);
        sym8 = view.findViewById(R.id.tgRisk8);
        sym9 = view.findViewById(R.id.tgRisk9);
        sym10 = view.findViewById(R.id.tgRisk10);
        sym11 = view.findViewById(R.id.tgRisk11);
        sym12 = view.findViewById(R.id.tgRisk12);

        List<MaterialCheckBox> q1 = Arrays.asList(sym1, sym2, sym3, sym4, sym5, sym6, sym7, sym8, sym9, sym10,sym11,sym12);

        riskLogoIv = view.findViewById(R.id.ivCardviewCovidRisk);
        statusTv = view.findViewById(R.id.tvCardviewCovidTitle);
        riskTv = view.findViewById(R.id.tvCardviewCovidRisk);
        riskCv = view.findViewById(R.id.cvCovidExposureRisk);

        q2YesBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.riskstatus_question_option_selected));
        q2NoBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.riskstatus_question_option_selected));
        q3YesBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.riskstatus_question_option_selected));
        q3NoBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.riskstatus_question_option_selected));
        q4YesBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.riskstatus_question_option_selected));
        q4NoBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(),R.color.riskstatus_question_option_selected));

        toggleGroupQ2.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                q2YesBtn.setSelected(false);
                q2NoBtn.setSelected(false);

                if(isChecked){
                    if(checkedId==R.id.btnq2Yes){
                        q2YesBtn.setSelected(true);
                    }
                    if(checkedId==R.id.btnq2No){
                        q2NoBtn.setSelected(true);
                    }
                }
            }
        });

        toggleGroupQ3.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                q3YesBtn.setSelected(false);
                q3NoBtn.setSelected(false);

                if(isChecked){
                    if(checkedId==R.id.btnq3Yes){
                        q3YesBtn.setSelected(true);
                    }
                    if(checkedId==R.id.btnq3No){
                        q3NoBtn.setSelected(true);
                    }
                }
            }
        });

        toggleGroupQ4.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {

                q4YesBtn.setSelected(false);
                q4NoBtn.setSelected(false);

                if(isChecked){
                    if(checkedId==R.id.btnq4Yes){
                        q4YesBtn.setSelected(true);
                    }
                    if(checkedId==R.id.btnq4No){
                        q4NoBtn.setSelected(true);
                    }
                }
            }
        });

        submitRiskBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {

                assessment = new HealthRiskAssessment(sym1.isChecked(), sym2.isChecked(), sym3.isChecked(), sym4.isChecked(),
                                                        sym5.isChecked(), sym6.isChecked(), sym7.isChecked(), sym8.isChecked(),
                                                        sym9.isChecked(), sym10.isChecked(), sym11.isChecked(), sym12.isChecked(),
                                                        q2YesBtn.isSelected(), q3YesBtn.isSelected(), q4YesBtn.isSelected());

                FirebaseDatabase.getInstance().getReference("healthAssessment").child(user.getNric()).setValue(assessment);

                int score = 0;
                boolean isSelected = false;
                isSelected = (q2YesBtn.isSelected() || q2NoBtn.isSelected()) && (q3YesBtn.isSelected() || q3NoBtn.isSelected()) && (q4YesBtn.isSelected() || q4NoBtn.isSelected());
                if(!isSelected) {
                    Toast.makeText(requireContext(), "Please answer all the questions above.", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if(q2YesBtn.isSelected())
                    score += 1;
                if(q3YesBtn.isSelected())
                    score += 1;
                if(q4YesBtn.isSelected())
                    score += 1;

                Log.i("Risk score for q2, q3, q4", "="+ score);

                int q1Sym=0;
                for (int i = 0; i < q1.size(); i++) {
                    if(q1.get(i).isChecked())
                        q1Sym+=1;
                }
                Log.i("Risk score for q1", "="+ q1Sym);

                String riskStatus = "No Exposure Detected";
                if(score==0 || q1Sym<3) {
                    riskStatus = "No Exposure Detected";
                } else if (score==1 || q1Sym<=4) {
                    riskStatus = "You are at High Risk";
                } else if (score>=2 && q1Sym>4) {
                    riskStatus = "You are positive for COVID-19";
                }

                FirebaseDatabase.getInstance().getReference("risks").child(user.getNric()).child("risk").setValue(riskStatus);
                ((MainActivity) requireActivity()).getUser();

                Toast.makeText(requireContext(), "Health assessment updated.", Toast.LENGTH_SHORT).show();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void setStatus(int imageRes, int bgRes,int colorRes, String riskState) {
        riskLogoIv.setImageResource(imageRes);
        riskCv.setCardBackgroundColor(bgRes);
        riskLogoIv.setImageTintList(ColorStateList.valueOf(colorRes));
        statusTv.setTextColor(colorRes);
        riskTv.setTextColor(colorRes);
        riskTv.setText(riskState);
    }
}