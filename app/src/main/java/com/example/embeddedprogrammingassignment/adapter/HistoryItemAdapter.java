package com.example.embeddedprogrammingassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.HistoryItem;

import java.util.ArrayList;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemViewHolder> {
    ArrayList<HistoryItem> historyItems;

    public HistoryItemAdapter(ArrayList<HistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public HistoryItemAdapter.HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cardview_historyitem, parent, false);
        return new HistoryItemAdapter.HistoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemAdapter.HistoryItemViewHolder holder, int position) {
        if(historyItems.get(position).getIsCheckIn().equals("true")){
            holder.status.setImageResource(R.drawable.icon_checkin);
        }
        else if(historyItems.get(position).getIsCheckIn().equals("false")){
            holder.status.setImageResource(R.drawable.icon_checkout);
        }
        holder.location.setText(historyItems.get(position).getLocation());
        holder.date.setText(historyItems.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public static class HistoryItemViewHolder extends RecyclerView.ViewHolder{

        TextView location,date;
        ImageView status;

        public HistoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.tvHistoryItemLocation);
            date = itemView.findViewById(R.id.tvHistoryItemDate);
            status = itemView.findViewById(R.id.ivHistoryItemStatus);
        }
    }
}
