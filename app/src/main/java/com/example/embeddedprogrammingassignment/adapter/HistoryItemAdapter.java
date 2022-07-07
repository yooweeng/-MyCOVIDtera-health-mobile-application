package com.example.embeddedprogrammingassignment.adapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.HistoryItem;
import com.example.embeddedprogrammingassignment.modal.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.parceler.Parcels;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemViewHolder> {
    ArrayList<HistoryItem> historyItems;
    int parentPosition;
    User user;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public HistoryItemAdapter(ArrayList<HistoryItem> historyItems, int parentPosition, User user) {
        this.historyItems = historyItems;
        this.parentPosition = parentPosition;
        this.user = user;
    }

    @NonNull
    @Override
    public HistoryItemAdapter.HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cardview_historyitem, parent, false);
        return new HistoryItemAdapter.HistoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemAdapter.HistoryItemViewHolder holder, @SuppressLint("RecyclerView") int position) {

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("history").child(user.getNric()).child("history"+parentPosition).child("details");

        if(historyItems.size() > 1){
            holder.btnCheckout.setVisibility(View.GONE);
        }
        if(historyItems.get(position).getIsCheckIn().equals("true")){
            holder.status.setImageResource(R.drawable.icon_checkin);
        }
        else if(historyItems.get(position).getIsCheckIn().equals("false")){
            holder.status.setImageResource(R.drawable.icon_checkout);
        }
        holder.location.setText(historyItems.get(position).getLocation());
        holder.date.setText(historyItems.get(position).getTime());

        holder.btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter format= DateTimeFormatter.ofPattern("dd-MMMM-yyyy HH:mm:ss");
                String formatDateTime  = now.format(format);

                HistoryItem historyItem = new HistoryItem("false", historyItems.get(position).getLocation(),formatDateTime);
                reference.child("1").setValue(historyItem);

                Bundle bundle = new Bundle();
                bundle.putParcelable("activeUser", Parcels.wrap(user));

                historyItems.add(historyItem);
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    public static class HistoryItemViewHolder extends RecyclerView.ViewHolder{

        TextView location,date;
        ImageView status;
        Button btnCheckout;

        public HistoryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.tvHistoryItemLocation);
            date = itemView.findViewById(R.id.tvHistoryItemDate);
            status = itemView.findViewById(R.id.ivHistoryItemStatus);
            btnCheckout = itemView.findViewById(R.id.btnCheckoutHistoryItem);
        }
    }
}
