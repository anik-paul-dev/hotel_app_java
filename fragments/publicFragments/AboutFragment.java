package com.example.hotel_booking_app.fragments.publicFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.ManagementTeamAdapter;
import com.example.hotel_booking_app.models.ManagementTeam;
import com.example.hotel_booking_app.models.Settings;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.bumptech.glide.Glide;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class AboutFragment extends Fragment {

    private ImageView ivHotel;
    private TextView tvDescription, tvRoomsCount, tvCustomersCount, tvReviewsCount, tvStaffsCount;
    private RecyclerView teamRecycler;
    private TextView tvWebsiteTitle, tvWebsiteDescription;
    private LinearLayout socialLinksLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        ivHotel = view.findViewById(R.id.iv_hotel);
        tvDescription = view.findViewById(R.id.tv_description);
        tvRoomsCount = view.findViewById(R.id.tv_rooms_count);
        tvCustomersCount = view.findViewById(R.id.tv_customers_count);
        tvReviewsCount = view.findViewById(R.id.tv_reviews_count);
        tvStaffsCount = view.findViewById(R.id.tv_staffs_count);
        teamRecycler = view.findViewById(R.id.team_recycler);
        tvWebsiteTitle = view.findViewById(R.id.tv_website_title);
        tvWebsiteDescription = view.findViewById(R.id.tv_website_description);
        socialLinksLayout = view.findViewById(R.id.social_links);

        teamRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadSettings();
        loadManagementTeam();

        return view;
    }

    private void loadSettings() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getSettings().enqueue(new Callback<Settings>() {
            @Override
            public void onResponse(Call<Settings> call, Response<Settings> response) {
                if (response.isSuccessful()) {
                    Settings settings = response.body();
                    Glide.with(getContext()).load(settings.getHotelImage()).into(ivHotel);
                    tvDescription.setText(settings.getAboutDescription());
                    tvRoomsCount.setText(String.valueOf(settings.getRoomsCount()));
                    tvCustomersCount.setText(String.valueOf(settings.getCustomersCount()));
                    tvReviewsCount.setText(String.valueOf(settings.getReviewsCount()));
                    tvStaffsCount.setText(String.valueOf(settings.getStaffsCount()));
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

    private void loadManagementTeam() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getManagementTeam().enqueue(new Callback<List<ManagementTeam>>() {
            @Override
            public void onResponse(Call<List<ManagementTeam>> call, Response<List<ManagementTeam>> response) {
                if (response.isSuccessful()) {
                    ManagementTeamAdapter adapter = new ManagementTeamAdapter(response.body(), getContext());
                    teamRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<ManagementTeam>> call, Throwable t) {}
        });
    }
}