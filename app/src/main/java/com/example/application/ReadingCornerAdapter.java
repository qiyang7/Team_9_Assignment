package com.example.application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import com.bumptech.glide.Glide;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReadingCornerAdapter extends RecyclerView.Adapter<ReadingCornerAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private List<ReadingCornerModel> readingCornerModels;

    public void setFilteredList(List<ReadingCornerModel> filteredList){
        this.readingCornerModels = filteredList;
        notifyDataSetChanged();
    }
    public ReadingCornerAdapter(Context context, List<ReadingCornerModel> readingCornerModels, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.readingCornerModels = readingCornerModels;
        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.reading_cornercard, parent, false);
        return new ViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReadingCornerModel readingCornerModel = readingCornerModels.get(holder.getAdapterPosition());
        holder.topicName.setText(readingCornerModel.getTopicName());
        Glide.with(context)
                .load(readingCornerModel.getTopicImage())
                .into(holder.topicImage);
    }

    @Override
    public int getItemCount() {
        return readingCornerModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView topicName;
        ImageView topicImage;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            topicName = itemView.findViewById(R.id.topic_name);
            topicImage = itemView.findViewById(R.id.topic_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (recyclerViewInterface != null) {
                        ReadingCornerModel readingCornerModel = readingCornerModels.get(getAdapterPosition());
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }

                ;
            });
        }
    }
}



//        @Override
//        public void onClick(View v) {
//            if (recyclerViewInterface != null) {
//                ReadingCornerModel readingCornerModel = readingCornerModels.get(getAdapterPosition());
//                int pos = getAdapterPosition();
//                Toast.makeText(context, readingCornerModel.getTopicName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, readingFile.class);
//                intent.putExtra("title", readingCornerModel.getTopicName());
//                context.startActivity(intent);



