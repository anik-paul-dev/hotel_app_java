package com.example.hotel_booking_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Facility;
import com.bumptech.glide.Glide;
import java.util.List;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {

    private List<Facility> facilities;
    private Context context;

    public FacilityAdapter(List<Facility> facilities, Context context) {
        this.facilities = facilities;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facility, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Facility facility = facilities.get(position);
        Glide.with(context).load(facility.getImage()).into(holder.ivFacility);
        holder.tvTitle.setText(facility.getName());
        holder.tvDescription.setText(facility.getDescription());
    }

    @Override
    public int getItemCount() {
        return facilities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFacility;
        TextView tvTitle, tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ivFacility = itemView.findViewById(R.id.iv_facility);
            tvTitle = itemView.findViewById(R.id.tv_name);
            tvDescription = itemView.findViewById(R.id.tv_description);
        }
    }
}