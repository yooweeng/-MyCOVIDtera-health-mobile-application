package com.example.embeddedprogrammingassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.FaqItem;

import java.util.ArrayList;

public class FaqItemAdapter extends RecyclerView.Adapter<FaqItemAdapter.FaqViewHolder> {
    ArrayList<FaqItem> faqItems;

    public FaqItemAdapter(ArrayList<FaqItem> faqItems) {
        this.faqItems = faqItems;
    }

    @NonNull
    @Override
    public FaqItemAdapter.FaqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.cardview_faqs_item, parent, false);
        return new FaqItemAdapter.FaqViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FaqItemAdapter.FaqViewHolder holder, int position) {
        holder.questionNo.setText((position + 1) +". ");
        holder.question.setText(faqItems.get(position).getQuestion());
        holder.answer.setText(faqItems.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return faqItems.size();
    }

    public static class FaqViewHolder extends RecyclerView.ViewHolder{

        TextView question, answer, questionNo;

        public FaqViewHolder(@NonNull View itemView) {
            super(itemView);

            questionNo = itemView.findViewById(R.id.faqQuestionNo);
            question = itemView.findViewById(R.id.faqQuestion);
            answer = itemView.findViewById(R.id.faqAnswer);
        }
    }
}
