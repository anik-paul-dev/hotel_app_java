package com.example.hotel_booking_app.fragments.adminFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.CarouselAdapter;
import com.example.hotel_booking_app.models.Carousel;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class CarouselFragment extends Fragment {

    private RecyclerView carouselRecycler;
    private FloatingActionButton fabAddImage;
    private ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carousel, container, false);

        carouselRecycler = view.findViewById(R.id.carousel_recycler);
        fabAddImage = view.findViewById(R.id.fab_add_image);
        carouselRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                uploadCarouselImage(imageUri);
            }
        });

        fabAddImage.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            imagePickerLauncher.launch(intent);
        });

        loadCarousel();

        return view;
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
                    carouselRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Carousel>> call, Throwable t) {}
        });
    }

    private void uploadCarouselImage(Uri imageUri) {
        // Implement image upload to backend
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        // Assuming backend handles file upload
        apiService.addCarousel(imageUri.toString()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadCarousel();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {}
        });
    }
}