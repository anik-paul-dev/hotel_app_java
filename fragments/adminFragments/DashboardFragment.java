package com.example.hotel_booking_app.fragments.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private TextView tvTotalBookings, tvNewBookings, tvTotalUsers, tvTotalRevenue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        tvTotalBookings = view.findViewById(R.id.tv_total_bookings);
        tvNewBookings = view.findViewById(R.id.tv_new_bookings);
        tvTotalUsers = view.findViewById(R.id.tv_total_users);
        tvTotalRevenue = view.findViewById(R.id.tv_total_revenue);

        loadAnalytics();

        return view;
    }

    private void loadAnalytics() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.getBookingAnalytics().enqueue(new Callback<Map<String, Integer>>() {
            @Override
            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
                if (response.isSuccessful()) {
                    Map<String, Integer> analytics = response.body();
                    tvTotalBookings.setText(String.valueOf(analytics.get("total_bookings")));
                    tvNewBookings.setText(String.valueOf(analytics.get("new_bookings")));
                    tvTotalRevenue.setText(String.format("$%d", analytics.get("total_revenue")));
                }
            }

            @Override
            public void onFailure(Call<Map<String, Integer>> call, Throwable t) {}
        });

        apiService.getUserAnalytics().enqueue(new Callback<Map<String, Integer>>() {
            @Override
            public void onResponse(Call<Map<String, Integer>> call, Response<Map<String, Integer>> response) {
                if (response.isSuccessful()) {
                    tvTotalUsers.setText(String.valueOf(response.body().get("total_users")));
                }
            }

            @Override
            public void onFailure(Call<Map<String, Integer>> call, Throwable t) {}
        });
    }
}