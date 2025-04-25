package com.example.hotel_booking_app.fragments.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
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

public class RatingReviewsFragment extends Fragment {

    private RecyclerView reviewsRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_reviews, container, false);

        reviewsRecycler = view.findViewById(R.id.reviews_recycler);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadReviews();

        return view;
    }

    private void loadReviews() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getReviews(null).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    ReviewAdapter adapter = new ReviewAdapter(response.body(), getContext());
                    reviewsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {}
        });
    }
}