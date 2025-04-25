package com.example.hotel_booking_app.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.models.Review;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingReviewDialog extends DialogFragment {

    private TextInputEditText etReview;
    private RatingBar ratingBar;
    private Button btnSubmit;
    private int roomId;

    public static RatingReviewDialog newInstance(int roomId) {
        RatingReviewDialog dialog = new RatingReviewDialog();
        Bundle args = new Bundle();
        args.putInt("room_id", roomId);
        dialog.setArguments(args);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_rating_review, container, false);

        roomId = getArguments().getInt("room_id", -1);
        etReview = view.findViewById(R.id.et_review);
        ratingBar = view.findViewById(R.id.rating_bar);
        btnSubmit = view.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(v -> submitReview());

        return view;
    }

    private void submitReview() {
        String reviewText = etReview.getText().toString().trim();
        float rating = ratingBar.getRating();

        Review review = new Review();
        review.setRoomId(roomId);
        review.setUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        review.setUserName("User"); // Fetch from backend
        review.setRating(rating);
        review.setReview(reviewText);
        review.setDate(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.addReview(review).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Review Submitted", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Toast.makeText(getActivity(), "Failed to submit review", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}