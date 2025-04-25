package com.example.hotel_booking_app.fragments.publicFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.hotel_booking_app.R;
import com.example.hotel_booking_app.adapters.RoomAdapter;
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

public class RoomsFragment extends Fragment {

    private TextInputEditText etCheckIn, etCheckOut, etAdults, etChildren, etFacility;
    private RecyclerView roomsRecycler;
    private TextView tvWebsiteTitle, tvWebsiteDescription;
    private LinearLayout socialLinksLayout;
    private boolean isShutdown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rooms, container, false);

        etCheckIn = view.findViewById(R.id.et_check_in);
        etCheckOut = view.findViewById(R.id.et_check_out);
        etAdults = view.findViewById(R.id.et_adults);
        etChildren = view.findViewById(R.id.et_children);
        etFacility = view.findViewById(R.id.et_facility);
        roomsRecycler = view.findViewById(R.id.rooms_recycler);
        tvWebsiteTitle = view.findViewById(R.id.tv_website_title);
        tvWebsiteDescription = view.findViewById(R.id.tv_website_description);
        socialLinksLayout = view.findViewById(R.id.social_links);

        roomsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // Pre-fill filter fields if arguments are passed
        Bundle args = getArguments();
        if (args != null) {
            etCheckIn.setText(args.getString("check_in", ""));
            etCheckOut.setText(args.getString("check_out", ""));
            etAdults.setText(args.getString("adults", ""));
            etChildren.setText(args.getString("children", ""));
        }

        loadSettings();
        filterRooms();

        // Dynamic filter
        etCheckIn.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) { filterRooms(); }
        });

        etCheckOut.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) { filterRooms(); }
        });

        etAdults.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) { filterRooms(); }
        });

        etChildren.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) { filterRooms(); }
        });

        etFacility.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(android.text.Editable s) { filterRooms(); }
        });

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

    private void filterRooms() {
        String checkIn = etCheckIn.getText().toString().trim();
        String checkOut = etCheckOut.getText().toString().trim();
        String adults = etAdults.getText().toString().trim();
        String children = etChildren.getText().toString().trim();
        String facility = etFacility.getText().toString().trim();

        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        apiService.filterRooms(checkIn, checkOut, adults, children, facility).enqueue(new Callback<List<Room>>() {
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
}