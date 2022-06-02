package com.example.embeddedprogrammingassignment.fragments.trace;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.adapter.QrHistoryAdapter;
import com.example.embeddedprogrammingassignment.modal.HistoryItem;
import com.example.embeddedprogrammingassignment.modal.QrHistory;

import java.util.ArrayList;


public class QrHistoryFragment extends Fragment {

    ArrayList<QrHistory> qrHistories = new ArrayList<>();
    ArrayList<HistoryItem> historyItems = new ArrayList<>();
    RecyclerView qrHistoryRv;

    public QrHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trace_qr_history, container, false);

        qrHistoryRv = view.findViewById(R.id.rvCheckInHistory);

        historyItems.add(new HistoryItem(R.drawable.icon_checkin,"Xiamen University Malaysia", "28-March-2022 07:00:24"));
        historyItems.add(new HistoryItem(R.drawable.icon_checkin,"Xiamen University Malaysia", "28-March-2022 07:00:24"));
        qrHistories.add(new QrHistory("28-March-2022","Xiamen University Malaysia",historyItems));

        QrHistoryAdapter qrHistoryAdapter = new QrHistoryAdapter(qrHistories);
        qrHistoryRv.setAdapter(qrHistoryAdapter);
        qrHistoryRv.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }
}