package com.example.hotel_booking_app.fragments.adminFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.BookingAdapter;
import com.example.hotel_booking_app.models.Booking;
import com.example.hotel_booking_app.network.ApiService;
import com.example.hotel_booking_app.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class BookingsRecordsFragment extends Fragment {

    private RecyclerView bookingsRecycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookings_records, container, false);

        bookingsRecycler = view.findViewById(R.id.bookings_recycler);
        bookingsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        loadBookings();

        return view;
    }

    private void loadBookings() {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.getNewBookings().enqueue(new Callback<List<Booking>>() {
            @Override
            public void onResponse(Call<List<Booking>> call, Response<List<Booking>> response) {
                if (response.isSuccessful()) {
                    BookingAdapter adapter = new BookingAdapter(getContext(), response.body(), new BookingAdapter.OnBookingClickListener() {
                        @Override
                        public void onCancelClick(Booking booking) {
                            // Handle cancel click (e.g., call apiService.cancelBooking)
                        }

                        @Override
                        public void onAssignRoomClick(Booking booking) {
                            // Handle assign room click (e.g., call apiService.assignRoom)
                        }

                        @Override
                        public void onDownloadClick(Booking booking) {
                            // Handle download click (e.g., generate PDF)
                        }
                    });
                    bookingsRecycler.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Booking>> call, Throwable t) {}
        });
    }
}