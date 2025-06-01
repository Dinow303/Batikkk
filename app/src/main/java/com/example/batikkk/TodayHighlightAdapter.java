package com.example.batikkk;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TodayHighlightAdapter extends RecyclerView.Adapter<TodayHighlightAdapter.HighlightViewHolder> {

    private List<TodayHighlight> highlightList;

    public TodayHighlightAdapter(List<TodayHighlight> highlightList) {
        this.highlightList = highlightList;
    }

    @NonNull
    @Override
    public HighlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_today_highlight, parent, false);
        return new HighlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HighlightViewHolder holder, int position) {
        TodayHighlight highlight = highlightList.get(position);
        holder.titleTextView.setText(highlight.getTitle());
        holder.descriptionTextView.setText(highlight.getDescription());
        holder.imageView.setImageResource(highlight.getImageResId());
    }

    @Override
    public int getItemCount() {
        return highlightList.size();
    }

    public static class HighlightViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView descriptionTextView;
        ImageView imageView;

        public HighlightViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.text_title);
            descriptionTextView = itemView.findViewById(R.id.text_description);
            imageView = itemView.findViewById(R.id.image_highlight);
        }
    }
}
