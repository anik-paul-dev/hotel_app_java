package com.example.hotel_booking_app.fragments.publicFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.activities.AllReviewsActivity;
import com.example.hotel_booking_app.adapters.CarouselAdapter;
import com.example.hotel_booking_app.adapters.FacilityAdapter;
import com.example.hotel_booking_app.adapters.ReviewAdapter;
import com.example.hotel_booking_app.adapters.RoomAdapter;
import com.example.hotel_booking_app.models.Carousel;
import com.example.hotel_booking_app.models.Facility;
import com.example.hotel_booking_app.models.Review;
import com.example.hotel_booking_app.models.Room;
import com.example.hotel_booking_app.models.Settings;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.textfield.TextInputEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 carouselViewPager;
    private RecyclerView roomsRecycler, facilitiesRecycler, reviewsRecycler;
    private TextInputEditText etCheckIn, etCheckOut, etAdults, etChildren;
    private Button btnCheckAvailability, btnMoreRooms, btnMoreFacilities, btnKnowMore;
    private TextView tvShutdownMessage, tvWebsiteTitle, tvWebsiteDescription;
    private LinearLayout socialLinksLayout;
    private boolean isShutdown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        carouselViewPager = view.findViewById(R.id.carousel_view_pager);
        roomsRecycler = view.findViewById(R.id.rooms_recycler);
        facilitiesRecycler = view.findViewById(R.id.facilities_recycler);
        reviewsRecycler = view.findViewById(R.id.reviews_recycler);
        etCheckIn = view.findViewById(R.id.et_check_in);
        etCheckOut = view.findViewById(R.id.et_check_out);
        etAdults = view.findViewById(R.id.et_adults);
        etChildren = view.findViewById(R.id.et_children);
        btnCheckAvailability = view.findViewById(R.id.btn_check_availability);
        btnMoreRooms = view.findViewById(R.id.btn_more_rooms);
        btnMoreFacilities = view.findViewById(R.id.btn_more_facilities);
        btnKnowMore = view.findViewById(R.id.btn_know_more);
        tvShutdownMessage = view.findViewById(R.id.tv_shutdown_message);
        tvWebsiteTitle = view.findViewById(R.id.tv_website_title);
        tvWebsiteDescription = view.findViewById(R.id.tv_website_description);
        socialLinksLayout = view.findViewById(R.id.social_links);

        roomsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        facilitiesRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        loadSettings();
        loadCarousel();
        loadRooms();
        loadFacilities();
        loadReviews();

        btnCheckAvailability.setOnClickListener(v -> {
            RoomsFragment roomsFragment = new RoomsFragment();
            Bundle args = new Bundle();
            args.putString("check_in", etCheckIn.getText().toString());
            args.putString("check_out", etCheckOut.getText().toString());
            args.putString("adults", etAdults.getText().toString());
            args.putString("children", etChildren.getText().toString());
            roomsFragment.setArguments(args);
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, roomsFragment)
                    .addToBackStack(null)
                    .commit();
        });

        btnMoreRooms.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new RoomsFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnMoreFacilities.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.nav_host_fragment, new FacilitiesFragment())
                    .addToBackStack(null)
                    .commit();
        });

        btnKnowMore.setOnClickListener(v -> startActivity(new Intent(getActivity(), AllReviewsActivity.class)));

        return view;
    }

    private void loadSettings() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (response.isSuccessful()) {
                    Settings settings = response.body();
                    isShutdown = settings.isShutdown();
                    tvShutdownMessage.setVisibility(isShutdown ? View.VISIBLE : View.GONE);
                    tvWebsiteTitle.setText(settings.getWebsiteTitle());
                    tvWebsiteDescription.setText(settings.getWebsiteDescription());
                    for (String link : settings.getSocialLinks()) {
                        TextView tvLink = new TextView(getContext());
                        tvLink.setText(link);
                        tvLink.setClickable(true);
                        socialLinksLayout.addView(tvLink);
                    }
                }
            }

            @Override
            public void onFailure(Call<Settings> call, Throwable t) {}
        });
    }

    private void loadCarousel() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getCarousel().enqueue(new Callback<List<Carousel>>() {
            @Override
            public void onResponse(Call<List<Carousel>> call, Response<List<Carousel>> response) {
                if (response.isSuccessful()) {
                    List<String> imageUrls = new ArrayList<>();
                    for (Carousel carousel : response.body()) {
                        imageUrls.add(carousel.getImageUrl());
                    }
                    CarouselAdapter adapter = new CarouselAdapter(imageUrls, getContext());
                    carouselViewPager.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Carousel>> call, Throwable t) {}
        });
    }

    private void loadRooms() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getRooms(5).enqueue(new Callback<List<Room>>() {
            @Override
            public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                if (response.isSuccessful()) {
                    RoomAdapter adapter = new RoomAdapter(response.body(), getContext(), isShutdown);
                    roomsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Room>> call, Throwable t) {}
        });
    }

    private void loadFacilities() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getFacilities(5).enqueue(new Callback<List<Facility>>() {
            @Override
            public void onResponse(Call<List<Facility>> call, Response<List<Facility>> response) {
                if (response.isSuccessful()) {
                    FacilityAdapter adapter = new FacilityAdapter(response.body(), getContext());
                    facilitiesRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Facility>> call, Throwable t) {}
        });
    }

    private void loadReviews() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getReviews(5).enqueue(new Callback<List<Review>>() {
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