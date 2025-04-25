package com.example.hotel_booking_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Feature;
import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.ViewHolder> {
    private Context context;
    private List<Feature> features;
    private OnFeatureClickListener listener;

    public interface OnFeatureClickListener {
        void onUpdateClick(Feature feature);
        void onDeleteClick(Feature feature);
    }

    public FeatureAdapter(Context context, List<Feature> features, OnFeatureClickListener listener) {
        this.context = context;
        this.features = features;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_feature, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feature feature = features.get(position);
        holder.tvName.setText(feature.getName());
        holder.btnUpdate.setOnClickListener(v -> listener.onUpdateClick(feature));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(feature));
    }

    @Override
    public int getItemCount() {
        return features.size();
    }

    public void updateFeatures(List<Feature> newFeatures) {
        features.clear();
        features.addAll(newFeatures);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}