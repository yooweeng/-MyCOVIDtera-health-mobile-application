package com.example.embeddedprogrammingassignment.fragments.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.adapter.FaqItemAdapter;
import com.example.embeddedprogrammingassignment.modal.FaqItem;

import java.util.ArrayList;

public class homeFaqFragment extends Fragment {

    RecyclerView general,informationPrivacy;
    ArrayList<FaqItem> generalFaqItems = new ArrayList<>();
    ArrayList<FaqItem> informationPrivacyFaqItems = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_faq, container, false);

        general = view.findViewById(R.id.rvFaqGeneral);
        informationPrivacy = view.findViewById(R.id.rvFaqInformationPrivacy);

        //get data from firebase and add into list
        generalFaqItems.add(new FaqItem("How do I verify my account?",
                "The account is able to be verified by scan a virtual copy of your identification card into the system when the application first started."));
        generalFaqItems.add(new FaqItem("Who can use MyCOVIDtera?",
                "MyCOVIDtera can be used by:\n- Residents of Malaysia\n- System administrator(s)\n- Foreigner with legal passport and entry documents"));
        generalFaqItems.add(new FaqItem("Is MyCOVIDtera free to download?",
                "Yes. MyCOVIDtera is free for the users to use."));
        generalFaqItems.add(new FaqItem("Why should I use MyCOVIDtera app?",
                "- Receiving early notification that you may have been exposed to coronavirus means you can be tested or go into quarantine so your health and others’ is protected.\n\n- The contact app uses technology to make this process faster and more accurate.\n\n- The contact app has been developed to ensure your information and privacy is strictly protected."));
        informationPrivacyFaqItems.add(new FaqItem("Is the information used within this app secured? Will it be used for any third party purposes?",
                "Yes. The information acquired from this application will not be disclosed to any third party. The organization assures that personal information will only be used for the purpos of managing and mitigating COVID-19 outbreak. It will not be shared to any other party."));
        informationPrivacyFaqItems.add(new FaqItem("Is my identity protected?",
                "Medical information of the users is protected under confidentiality of medical records."));
        informationPrivacyFaqItems.add(new FaqItem("Can the app be used to track a user or contact?",
                "No. It does not record an individual’s location or movements. The app only records that a contact occurred to allow health officials to contact those users to enable them to quickly self-quarantine and/or seek medical attention."));
        informationPrivacyFaqItems.add(new FaqItem("Why does the app ask for your contact information?",
                "A mobile number is needed to activate an account and to allow health officials to contact you if they need to."));

        FaqItemAdapter generalAdapter = new FaqItemAdapter(generalFaqItems);
        general.setAdapter(generalAdapter);
        general.setLayoutManager(new LinearLayoutManager(getContext()));

        FaqItemAdapter informationPrivacyAdapter = new FaqItemAdapter(informationPrivacyFaqItems);
        informationPrivacy.setAdapter(informationPrivacyAdapter);
        informationPrivacy.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}