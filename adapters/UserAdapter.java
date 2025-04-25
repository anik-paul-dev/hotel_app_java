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
import com.example.hotel_booking_app.models.User;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> users;
    private OnUserClickListener listener;

    public interface OnUserClickListener {
        void onUpdateClick(User user);
        void onDeleteClick(User user);
    }

    public UserAdapter(Context context, List<User> users, OnUserClickListener listener) {
        this.context = context;
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.tvName.setText(user.getName());
        holder.tvEmail.setText(user.getEmail());
        holder.tvPhone.setText(user.getPhone());
        holder.tvStatus.setText("Status: " + (user.isActive() ? "Active" : "Inactive"));
        holder.btnUpdate.setOnClickListener(v -> listener.onUpdateClick(user));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(user));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void updateUsers(List<User> newUsers) {
        users.clear();
        users.addAll(newUsers);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone, tvStatus;
        Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvPhone = itemView.findViewById(R.id.tv_phone);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnUpdate = itemView.findViewById(R.id.btn_update);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}