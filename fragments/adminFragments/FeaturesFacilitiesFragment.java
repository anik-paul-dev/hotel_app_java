package com.example.hotel_booking_app.fragments.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.FacilityAdapter;
import com.example.hotel_booking_app.adapters.FeatureAdapter;
import com.example.hotel_booking_app.dialogs.AddFacilityDialog;
import com.example.hotel_booking_app.dialogs.AddFeatureDialog;
import com.example.hotel_booking_app.models.Facility;
import com.example.hotel_booking_app.models.Feature;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class FeaturesFacilitiesFragment extends Fragment {

    private RecyclerView featuresRecycler, facilitiesRecycler;
    private FloatingActionButton fabAddFeature, fabAddFacility;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_features_facilities, container, false);

        featuresRecycler = view.findViewById(R.id.features_recycler);
        facilitiesRecycler = view.findViewById(R.id.facilities_recycler);
        fabAddFeature = view.findViewById(R.id.fab_add_feature);
        fabAddFacility = view.findViewById(R.id.fab_add_facility);

        featuresRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        facilitiesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFeatures();
        loadFacilities();

        fabAddFeature.setOnClickListener(v -> {
            AddFeatureDialog dialog = new AddFeatureDialog();
            dialog.show(getParentFragmentManager(), "add_feature");
        });

        fabAddFacility.setOnClickListener(v -> {
            AddFacilityDialog dialog = new AddFacilityDialog();
            dialog.show(getParentFragmentManager(), "add_facility");
        });

        return view;
    }

    private void loadFeatures() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getFeatures().enqueue(new Callback<List<Feature>>() {
            @Override
            public void onResponse(Call<List<Feature>> call, Response<List<Feature>> response) {
                if (response.isSuccessful()) {
                    FeatureAdapter adapter = new FeatureAdapter(getContext(), response.body(), new FeatureAdapter.OnFeatureClickListener() {
                        @Override
                        public void onUpdateClick(Feature feature) {
                            // Handle update click (e.g., show update dialog)
                        }

                        @Override
                        public void onDeleteClick(Feature feature) {
                            // Handle delete click (e.g., call apiService.deleteFeature)
                        }
                    });
                    featuresRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Feature>> call, Throwable t) {}
        });
    }

    private void loadFacilities() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getFacilities(null).enqueue(new Callback<List<Facility>>() {
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
}