package com.example.hotel_booking_app.fragments.userFragments;

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
import com.example.hotel_booking_app.adapters.BookingAdapter;
import com.example.hotel_booking_app.models.Booking;
import com.example.hotel_booking_app.models.Settings;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import com.google.firebase.auth.FirebaseAuth;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MyBookingsFragment extends Fragment {

    private RecyclerView bookingsRecycler;
    private TextView tvWebsiteTitle, tvWebsiteDescription;
    private LinearLayout socialLinksLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        bookingsRecycler = view.findViewById(R.id.bookings_recycler);
        tvWebsiteTitle = view.findViewById(R.id.tv_website_title);
        tvWebsiteDescription = view.findViewById(R.id.tv_website_description);
        socialLinksLayout = view.findViewById(R.id.social_links);

        bookingsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadBookings();
        loadSettings();

        return view;
    }

    private void loadBookings() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        apiService.getBookings(userId).enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful()) {
                    BookingAdapter adapter = new BookingAdapter(getContext(), response.body(), new BookingAdapter.OnBookingClickListener() {
                        @Override
                        public void onCancelClick(Booking booking) {
                            // Handle cancel booking (e.g., call apiService.cancelBooking)
                        }

                        @Override
                        public void onAssignRoomClick(Booking booking) {
                            // Handle assign room (not applicable for user, may be hidden)
                        }

                        @Override
                        public void onDownloadClick(Booking booking) {
                            // Handle download PDF (e.g., use PdfGenerator)
                        }
                    });
                    bookingsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {}
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