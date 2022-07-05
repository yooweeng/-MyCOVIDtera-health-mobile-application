package com.example.embeddedprogrammingassignment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.QrHistory;

import java.util.ArrayList;

public class QrHistoryAdapter extends RecyclerView.Adapter<QrHistoryAdapter.qrHistoryViewHolder> {

    ArrayList<QrHistory> qrHistories;
    Context context;

    public QrHistoryAdapter(ArrayList<QrHistory> qrHistories) {
        this.qrHistories = qrHistories;
    }

    @NonNull
    @Override
    public QrHistoryAdapter.qrHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.cardview_qr_history, parent, false);
        return new QrHistoryAdapter.qrHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QrHistoryAdapter.qrHistoryViewHolder holder, int position) {
        QrHistory previousQrHistory = new QrHistory();
        if(position >= 1){
            previousQrHistory = qrHistories.get(position - 1);
        }
        if(qrHistories.get(position).getDate().equals(previousQrHistory.getDate())){
            holder.tvQrHistoryDate.setVisibility(View.GONE);
        }
        else{
            holder.tvQrHistoryDate.setText(qrHistories.get(position).getDate());
        }
        if(qrHistories.get(position).getLocation().equals(previousQrHistory.getLocation())){
            holder.tvQrHistoryLocation.setVisibility(View.GONE);
        }
        else{
            holder.tvQrHistoryLocation.setText(qrHistories.get(position).getLocation());
        }
        HistoryItemAdapter historyItemAdapter = new HistoryItemAdapter(qrHistories.get(position).getDetails(),qrHistories.size()-1-position);
        holder.rvQrHistoryItem.setAdapter(historyItemAdapter);
        holder.rvQrHistoryItem.setLayoutManager(new LinearLayoutManager(context));
        holder.rvQrHistoryItem.addItemDecoration(new DividerItemDecoration(context,LinearLayoutManager.VERTICAL));
    }

    @Override
    public int getItemCount() {
        return qrHistories.size();
    }

    public static class qrHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView tvQrHistoryDate, tvQrHistoryLocation;
        RecyclerView rvQrHistoryItem;

        public qrHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQrHistoryDate = itemView.findViewById(R.id.tvQrHistoryDate);
            tvQrHistoryLocation = itemView.findViewById(R.id.tvQrHistoryLocation);
            rvQrHistoryItem = itemView.findViewById(R.id.rvQrHistoryItem);
        }
    }
}
