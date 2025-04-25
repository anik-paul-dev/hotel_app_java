package com.example.hotel_booking_app.fragments.publicFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.FacilityAdapter;
import com.example.hotel_booking_app.models.Facility;
import com.example.hotel_booking_app.models.Settings;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class FacilitiesFragment extends Fragment {

    private RecyclerView facilitiesRecycler;
    private TextView tvWebsiteTitle, tvWebsiteDescription;
    private LinearLayout socialLinksLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facilities, container, false);

        facilitiesRecycler = view.findViewById(R.id.facilities_recycler);
        tvWebsiteTitle = view.findViewById(R.id.tv_website_title);
        tvWebsiteDescription = view.findViewById(R.id.tv_website_description);
        socialLinksLayout = view.findViewById(R.id.social_links);

        facilitiesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadFacilities();
        loadSettings();

        return view;
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

    private void loadSettings() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (response.isSuccessful()) {
                    Settings settings = response.body();
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
}