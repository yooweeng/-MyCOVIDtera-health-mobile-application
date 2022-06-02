package com.example.embeddedprogrammingassignment.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.embeddedprogrammingassignment.modal.Announcements;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.webView_announcement;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnnouncementPageAdapter extends RecyclerView.Adapter<AnnouncementPageAdapter.MyViewHolder> {
    Context context;
    ArrayList<Announcements> list;

    public AnnouncementPageAdapter(Context context, ArrayList<Announcements> list) {
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
            announcements.setNumberOfClicks(String.valueOf(Integer.parseInt(announcements.getNumberOfClicks())+1));
            Intent intent = new Intent(context, webView_announcement.class);
            intent.putExtra("url", list.get(position).getUrl());

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("announcements");
            databaseReference.child(announcements.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()) {
                        databaseReference.child(announcements.getId()).child("numberOfClicks").setValue(announcements.getNumberOfClicks());
                        holder.numberOfClicks.setText(announcements.getNumberOfClicks());
                        context.startActivity(intent);
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
