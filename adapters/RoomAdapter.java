package com.example.hotel_booking_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.activities.BookNowActivity;
import com.example.hotel_booking_app.activities.RoomDetailsActivity;
import com.example.hotel_booking_app.models.Room;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import java.util.List;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private List<Room> rooms;
    private Context context;
    private boolean isShutdown;

    public RoomAdapter(List<Room> rooms, Context context, boolean isShutdown) {
        this.rooms = rooms;
        this.context = context;
        this.isShutdown = isShutdown;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Room room = rooms.get(position);
        Glide.with(context).load(room.getImages().get(0)).into(holder.ivRoom);
        holder.tvTitle.setText(room.getName());
        holder.tvPrice.setText("Price per Night: $" + room.getPrice());
        holder.tvFeatures.setText("Features: " + String.join(", ", room.getFeatures()));
        holder.tvFacilities.setText("Facilities: " + String.join(", ", room.getFacilities()));
        holder.tvGuests.setText("Guests: " + room.getMaxAdults() + " Adults, " + room.getMaxChildren() + " Children");
        holder.tvRating.setText("Rating: " + room.getRating());

        holder.btnMoreDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, RoomDetailsActivity.class);
            intent.putExtra("room_id", room.getId());
            context.startActivity(intent);
        });

        holder.btnBookNow.setVisibility(isShutdown || room.isBooked() ? View.GONE : View.VISIBLE);
        holder.btnBookNow.setOnClickListener(v -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                new com.example.hotel_booking_app.dialogs.LoginDialog().show(((RoomDetailsActivity) context).getSupportFragmentManager(), "login");
            } else {
                Intent intent = new Intent(context, BookNowActivity.class);
                intent.putExtra("room_id", room.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivRoom;
        TextView tvTitle, tvPrice, tvFeatures, tvFacilities, tvGuests, tvRating;
        Button btnBookNow, btnMoreDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            ivRoom = itemView.findViewById(R.id.iv_room);
            tvTitle = itemView.findViewById(R.id.tv_room_title);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvFeatures = itemView.findViewById(R.id.tv_features);
            tvFacilities = itemView.findViewById(R.id.tv_facilities);
            tvGuests = itemView.findViewById(R.id.tv_guests);
            tvRating = itemView.findViewById(R.id.tv_rating);
            btnBookNow = itemView.findViewById(R.id.btn_book_now);
            btnMoreDetails = itemView.findViewById(R.id.btn_more_details);
        }
    }
}