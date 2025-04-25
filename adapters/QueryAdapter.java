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
import com.example.hotel_booking_app.models.Query;
import java.util.List;

public class QueryAdapter extends RecyclerView.Adapter<QueryAdapter.ViewHolder> {
    private Context context;
    private List<Query> queries;
    private OnQueryClickListener listener;

    public interface OnQueryClickListener {
        void onMarkReadClick(Query query);
        void onDeleteClick(Query query);
    }

    public QueryAdapter(Context context, List<Query> queries, OnQueryClickListener listener) {
        this.context = context;
        this.queries = queries;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_query, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Query query = queries.get(position);
        holder.tvName.setText(query.getName());
        holder.tvEmail.setText(query.getEmail());
        holder.tvSubject.setText(query.getSubject());
        holder.tvMessage.setText(query.getMessage());
        holder.tvDate.setText(query.getDate());
        holder.tvIsRead.setText("Status: " + (query.isRead() ? "Read" : "Unread"));
        holder.btnMarkRead.setOnClickListener(v -> listener.onMarkReadClick(query));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(query));
    }

    @Override
    public int getItemCount() {
        return queries.size();
    }

    public void updateQueries(List<Query> newQueries) {
        queries.clear();
        queries.addAll(newQueries);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvSubject, tvMessage, tvDate, tvIsRead;
        Button btnMarkRead, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);
            tvSubject = itemView.findViewById(R.id.tv_subject);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvIsRead = itemView.findViewById(R.id.tv_is_read);
            btnMarkRead = itemView.findViewById(R.id.btn_mark_read);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}