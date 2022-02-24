package com.example.capstone.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.Entities.Term;
import com.example.capstone.R;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {
    private List<Term> terms = new ArrayList<>();


    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_item, parent, false);
        return new ReportAdapter.ReportHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder reportHolder, int position) {
        Term currentTerm = terms.get(position);
        reportHolder.textViewTitle.setText(currentTerm.getTitle());
        reportHolder.textViewStart.setText(currentTerm.getStart());
        reportHolder.textViewTimestamp.setText(currentTerm.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return terms.size();
    }
    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }
    public Term getTermAt(int position) {
        return terms.get(position);
    }

    class ReportHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewStart;
        private TextView textViewTimestamp;

        public ReportHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.term_title_report_text_view_report);
            textViewStart = itemView.findViewById(R.id.term_start_date_text_view_report);
            textViewTimestamp = itemView.findViewById(R.id.report_time_stamp_text_view);

        }
    }
}
