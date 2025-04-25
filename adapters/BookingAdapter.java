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
import com.example.hotel_booking_app.models.Booking;
import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    private Context context;
    private List<Booking> bookings;
    private OnBookingClickListener listener;

    public interface OnBookingClickListener {
        void onCancelClick(Booking booking);
        void onAssignRoomClick(Booking booking);
        void onDownloadClick(Booking booking);
    }

    public BookingAdapter(Context context, List<Booking> bookings, OnBookingClickListener listener) {
        this.context = context;
        this.bookings = bookings;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookings.get(position);
        holder.tvBookingId.setText("Booking ID: " + booking.getBookingId());
        holder.tvRoomId.setText("Room ID: " + booking.getRoomId());
        holder.tvCheckIn.setText("Check-In: " + booking.getCheckIn());
        holder.tvCheckOut.setText("Check-Out: " + booking.getCheckOut());
        holder.tvTotalPrice.setText("Total Price: $" + booking.getTotalPrice());
        holder.tvStatus.setText("Status: " + booking.getStatus());
        holder.tvPaymentStatus.setText("Payment: " + booking.getPaymentStatus());

        holder.btnCancel.setOnClickListener(v -> listener.onCancelClick(booking));
        holder.btnAssignRoom.setOnClickListener(v -> listener.onAssignRoomClick(booking));
        holder.btnDownload.setOnClickListener(v -> listener.onDownloadClick(booking));
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public void updateBookings(List<Booking> newBookings) {
        bookings.clear();
        bookings.addAll(newBookings);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBookingId, tvRoomId, tvCheckIn, tvCheckOut, tvTotalPrice, tvStatus, tvPaymentStatus;
        Button btnCancel, btnAssignRoom, btnDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookingId = itemView.findViewById(R.id.tv_booking_id);
            tvRoomId = itemView.findViewById(R.id.tv_room_id);
            tvCheckIn = itemView.findViewById(R.id.tv_check_in);
            tvCheckOut = itemView.findViewById(R.id.tv_check_out);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_price);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvPaymentStatus = itemView.findViewById(R.id.tv_payment_status);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
            btnAssignRoom = itemView.findViewById(R.id.btn_assign_room);
            btnDownload = itemView.findViewById(R.id.btn_download);
        }
    }
}