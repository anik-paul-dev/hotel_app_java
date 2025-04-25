package com.example.hotel_booking_app.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.CarouselAdapter;
import com.example.hotel_booking_app.adapters.ReviewAdapter;
import com.example.hotel_booking_app.models.Room;
import com.example.hotel_booking_app.models.Review;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

public class RoomDetailsActivity extends AppCompatActivity {

    private ViewPager2 imageSlider;
    private RecyclerView reviewsRecycler;
    private TextView tvTitle, tvDescription, tvPrice, tvFeatures, tvFacilities, tvGuests, tvRating, tvArea;
    private Button btnBookNow;
    private CarouselAdapter carouselAdapter;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        int roomId = getIntent().getIntExtra("room_id", -1);
        imageSlider = findViewById(R.id.image_slider);
        reviewsRecycler = findViewById(R.id.reviews_recycler);
        tvTitle = findViewById(R.id.tv_room_title);
        tvDescription = findViewById(R.id.tv_description);
        tvPrice = findViewById(R.id.tv_price);
        tvFeatures = findViewById(R.id.tv_features);
        tvFacilities = findViewById(R.id.tv_facilities);
        tvGuests = findViewById(R.id.tv_guests);
        tvRating = findViewById(R.id.tv_rating);
        tvArea = findViewById(R.id.tv_area);
        btnBookNow = findViewById(R.id.btn_book_now);

        reviewsRecycler.setLayoutManager(new LinearLayoutManager(this));

        loadRoomDetails(roomId);
        loadReviews(roomId);

        btnBookNow.setOnClickListener(v -> {
            if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                new com.example.hotel_booking_app.dialogs.LoginDialog().show(getSupportFragmentManager(), "login");
            } else {
                Intent intent = new Intent(this, BookNowActivity.class);
                intent.putExtra("room_id", roomId);
                startActivity(intent);
            }
        });
    }

    private void loadRoomDetails(int roomId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getRoom(roomId).enqueue(new Callback<Room>() {
            @Override
            public void onResponse(Call<Room> call, Response<Room> response) {
                if (response.isSuccessful()) {
                    Room room = response.body();
                    tvTitle.setText(room.getName());
                    tvDescription.setText(room.getDescription());
                    tvPrice.setText("Price per Night: $" + room.getPrice());
                    tvFeatures.setText("Features: " + String.join(", ", room.getFeatures()));
                    tvFacilities.setText("Facilities: " + String.join(", ", room.getFacilities()));
                    tvGuests.setText("Guests: " + room.getMaxAdults() + " Adults, " + room.getMaxChildren() + " Children");
                    tvRating.setText("Rating: " + room.getRating());
                    tvArea.setText("Area: " + room.getArea() + " sq. ft.");
                    carouselAdapter = new CarouselAdapter(room.getImages(), RoomDetailsActivity.this);
                    imageSlider.setAdapter(carouselAdapter);
                    btnBookNow.setVisibility(room.isBooked() ? View.GONE : View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Room> call, Throwable t) {}
        });
    }

    private void loadReviews(int roomId) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getReviewsByRoom(roomId, 5).enqueue(new Callback<List<Review>>() {
            @Override
            public void onResponse(Call<List<Review>> call, Response<List<Review>> response) {
                if (response.isSuccessful()) {
                    reviewAdapter = new ReviewAdapter(response.body(), RoomDetailsActivity.this);
                    reviewsRecycler.setAdapter(reviewAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Review>> call, Throwable t) {}
        });
    }
}