package com.example.unitconverter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryAdapter extends ListAdapter<SingleHistory, HistoryAdapter.ViewHolder> {

    private SingleHistory curitem;

    protected HistoryAdapter() {
        super(diffCallback);
    }

    private final static DiffUtil.ItemCallback<SingleHistory> diffCallback = new DiffUtil.ItemCallback<SingleHistory>() {
        @Override
        public boolean areItemsTheSame(@NonNull SingleHistory oldItem, @NonNull SingleHistory newItem) {
            return oldItem.getKey().equals(newItem.getKey());
        }

        @Override
        public boolean areContentsTheSame(@NonNull SingleHistory oldItem, @NonNull SingleHistory newItem) {
            return oldItem.isAllEquals(newItem);
        }
    };


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        curitem = getItem(position);

        holder.tvFrom.setText(curitem.getFromData());
        holder.tvTo.setText(curitem.getToData());
        holder.tvTime.setText(curitem.getTime());

    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvTime, tvFrom, tvTo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTime = itemView.findViewById(R.id.tvTime);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTo = itemView.findViewById(R.id.tvTo);
        }

    }

}
