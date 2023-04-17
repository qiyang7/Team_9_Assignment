package com.example.application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{
        private final RecyclerViewInterface recyclerViewInterface;
        private Context context;
        private List<ReportModel> reportModels;

        private DatabaseReference myRef;
        private ProgressBar progressBar;

    public ReportAdapter(Context context, List<ReportModel> reportModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.reportModels = reportModels;
        this.recyclerViewInterface = recyclerViewInterface;

    }
        @NonNull
        @Override
        public ReportAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.topic_report, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }


    @Override
        public void onBindViewHolder (@NonNull ViewHolder holder,int position){
        ReportModel reportModel = reportModels.get(holder.getAdapterPosition());
        holder.topicReport.setText(reportModel.getTopicReport());
        holder.progressBar.setMax(100);
        holder.progressBar.setProgress(reportModel.getProgress());
        holder.progress.setText("Progress: " + String.valueOf(reportModel.getProgress()) + "%");




    }

        @Override
        public int getItemCount () {
        return reportModels.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView topicReport;
            TextView progress;


            ProgressBar progressBar;

            public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
                super(itemView);

                topicReport = itemView.findViewById(R.id.reportTopic);
                progress = itemView.findViewById(R.id.readProgress);
                progressBar = itemView.findViewById(R.id.ProgressBarReport);

            }
        }
    }

