package com.example.hotel_booking_app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Review;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> reviews;
    private Context context;

    public ReviewAdapter(List<Review> reviews, Context context) {
        this.reviews = reviews;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.tvUserName.setText(review.getUserName());
        holder.tvRating.setText("Rating: " + review.getRating());
        holder.tvReview.setText(review.getReview());
        holder.tvDate.setText(review.getDate());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserName, tvRating, tvReview, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            tvRating = itemView.findViewById(R.id.tv_rating);
            tvReview = itemView.findViewById(R.id.tv_review);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }
}