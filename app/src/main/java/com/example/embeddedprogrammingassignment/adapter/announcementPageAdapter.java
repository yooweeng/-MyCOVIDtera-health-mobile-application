package com.example.embeddedprogrammingassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.embeddedprogrammingassignment.MainActivity;
import com.example.embeddedprogrammingassignment.fragments.HomeFragment;
import com.example.embeddedprogrammingassignment.modal.Announcements;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.webView_announcement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;

public class announcementPageAdapter extends RecyclerView.Adapter<announcementPageAdapter.MyViewHolder> {
    Context context;
    ArrayList<Announcements> list;
    int clickCount;

    public announcementPageAdapter(Context context, ArrayList<Announcements> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardview_announcement, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Announcements announcements = list.get(position);
        holder.title.setText(announcements.getTitle());
        holder.caption.setText(announcements.getCaption());
        holder.numberOfClicks.setText(announcements.getNumberOfClicks());

        holder.readMore.setOnClickListener(v -> {
            clickCount = Integer.parseInt(list.get(position).getNumberOfClicks());
            Log.d("click1", String.valueOf(clickCount));
            Intent intent = new Intent(context, webView_announcement.class);
            intent.putExtra("url", list.get(position).getUrl());
            clickCount++;
            Log.d("click2", String.valueOf(clickCount) + announcements.getId());

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("announcements");
            databaseReference.child(announcements.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        databaseReference.child(announcements.getId()).child("numberOfClicks").setValue(String.valueOf(clickCount));
                        holder.numberOfClicks.setText(String.valueOf(clickCount));
                        Log.d("click3", String.valueOf(clickCount) + announcements.getId());
//                        context.startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        // set image on card
        Glide.with(context).load(list.get(position).getUrlToImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, caption, numberOfClicks;
        CardView cardView;
        ImageView imageView;
        Button readMore;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tvTitle);
            caption = itemView.findViewById(R.id.tvCaption);
            imageView = itemView.findViewById(R.id.ivImage);
            cardView = itemView.findViewById(R.id.cvReads);
            readMore = itemView.findViewById(R.id.btnReadMore);
            numberOfClicks = itemView.findViewById(R.id.tvNoOfClicks);
        }
    }
}
