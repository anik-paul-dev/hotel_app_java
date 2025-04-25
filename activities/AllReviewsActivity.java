package com.example.hotel_booking_app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.ReviewAdapter;
import com.example.hotel_booking_app.models.Review;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class AllReviewsActivity extends AppCompatActivity {

    private RecyclerView reviewsRecycler;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews);

        reviewsRecycler = findViewById(R.id.reviews_recycler);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(this));

        loadAllReviews();
    }

    private void loadAllReviews() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getReviews(null).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    reviewAdapter = new ReviewAdapter(response.body(), AllReviewsActivity.this);
                    reviewsRecycler.setAdapter(reviewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {}
        });
    }
}